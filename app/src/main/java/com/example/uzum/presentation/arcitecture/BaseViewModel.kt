package com.example.uzum.presentation.arcitecture

import android.os.Looper
import androidx.annotation.MainThread
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.trySendBlocking
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import timber.log.Timber

abstract class BaseViewModel<State : Any, Input : Any, Effect : Any> :
    Store<State, Input, Effect>, ViewModel() {

    private val effectsChannel = Channel<Effect>(Channel.BUFFERED)

    private val stateFlow by lazy(LazyThreadSafetyMode.NONE) {
        MutableStateFlow(getInitialState())
    }

    protected open val tag: String = "MVVM:{${javaClass.simpleName}"

    @Suppress("MemberVisibilityCanBePrivate")
    private val log: Timber.Tree
        get() = Timber.tag(tag)

    override val state: StateFlow<State>
        get() = stateFlow

    override val effect: Flow<Effect>
        get() = effectsChannel.receiveAsFlow()

    init {
        log.v("View Model created")
    }

    protected fun getState(): State = stateFlow.value

    abstract fun getInitialState(): State

    override fun onCleared() {
        log.v("View model destroyed")
        super.onCleared()
    }

    fun emitEffect(effect: Effect) {
        val result = effectsChannel.trySendBlocking(effect)
        log.v("Dispatching effect %s", effect)
        if (!result.isSuccess) {
            log.w("Failed to dispatch effect %s", result)
        }
    }

    @MainThread
    protected fun emitState(state: State): State {
        require(Looper.getMainLooper() == Looper.myLooper()) {
            "Must be main thread"
        }
        if (stateFlow.compareAndSet(stateFlow.value, state)) {
            log.v("New state: %s", state)
        } else {
            log.w("New state REJECTED: %s", state)
        }
        return stateFlow.value
    }

    @MainThread
    fun updateState(update: (state: State) -> State): State {
        val newState = update(getState())
        return emitState(newState)
    }

    protected fun dispatchAction(action: suspend (State) -> Unit): Job {
        return viewModelScope.launch {
            action(getState())
        }
    }

    protected fun dispatchCancelableAction(
        cancelCondition: (State) -> Boolean,
        action: suspend (State) -> Unit
    ): Job {
        return viewModelScope.launch {
            val actionScope = this
            launch(start = CoroutineStart.UNDISPATCHED) {
                stateFlow.dropWhile { !cancelCondition(it) }.first()
                actionScope.cancel()
            }

            launch {
                val currentState = getState()
                if (!cancelCondition(currentState)) {
                    action(currentState)
                }
                actionScope.cancel()
            }
        }
    }

    protected inline fun <reified T : State> requireState() = getState() as T

    protected fun <T> Flow<T>.stateOnStart(action: (state: State) -> State) = onStart {
        updateState { action(it) }
    }

    protected fun <T> Flow<T>.stateOnComplete(action: (state: State) -> State) = onCompletion {
        updateState { action(it) }
    }

    protected fun <T> Flow<T>.stateOnEach(action: (state: State, value: T) -> State) =
        onEach { value ->
            updateState { action(it, value) }
        }

    protected fun <T> Flow<T>.effectOnCatch(action: (e: Throwable) -> Effect) = catch {
        emitEffect(action(it))
    }

    protected fun <T> Flow<T>.stateOnCatch(action: (state: State, e: Throwable) -> State) =
        catch { e ->
            updateState { action(it, e) }
        }

    protected fun <T> Flow<T>.effectOnEach(action: (value: T) -> Effect) = onEach {
        emitEffect(action(it))
    }
}
