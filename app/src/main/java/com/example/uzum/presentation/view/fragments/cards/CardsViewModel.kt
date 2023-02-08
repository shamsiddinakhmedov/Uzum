package com.example.uzum.presentation.view.fragments.cards

import com.example.uzum.presentation.arcitecture.BaseViewModel
import com.example.uzum.presentation.view.fragments.cards.CardsViewModel.*
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CardsViewModel @Inject constructor() : BaseViewModel<State, Input, Effect>() {

    class State
    class Input
    class Effect

    override fun getInitialState(): State = State()

    override fun processInput(input: Input) {
    }
}