package com.example.uzum.presentation.view.fragments.cards_detail

import com.example.uzum.domain.model.Card
import com.example.uzum.domain.repo.CardRepository
import com.example.uzum.presentation.arcitecture.BaseViewModel
import com.example.uzum.presentation.view.fragments.cards_detail.CardsDetailViewModel.*
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CardsDetailViewModel @Inject constructor(
    private val cardRepository: CardRepository
) : BaseViewModel<State, Input, Effect>() {

    init {
        getCards()
    }

    private fun getCards() = cardRepository.readToDB()

    data class State(
        val listCards: List<Card>? = null,
    )

    class Input

    class Effect

    override fun getInitialState(): State = State()

    override fun processInput(input: Input) {
    }
}