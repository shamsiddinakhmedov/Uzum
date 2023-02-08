package com.example.uzum.presentation.arcitecture

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface Store<State, Input, Effect> {

    val state: StateFlow<State>

    val effect: Flow<Effect>

    fun processInput(input: Input)
}