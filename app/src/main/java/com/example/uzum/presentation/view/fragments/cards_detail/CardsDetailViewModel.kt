package com.example.uzum.presentation.view.fragments.cards_detail

import com.example.uzum.presentation.arcitecture.BaseViewModel
import com.example.uzum.presentation.view.fragments.cards_detail.CardsDetailViewModel.*
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CardsDetailViewModel @Inject constructor() : BaseViewModel<State, Input, Effect>() {

    class State

    class Input

    class Effect

    override fun getInitialState(): State = State()

    override fun processInput(input: Input) {
    }
}