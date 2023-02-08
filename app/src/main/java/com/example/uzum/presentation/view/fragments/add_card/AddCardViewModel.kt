package com.example.uzum.presentation.view.fragments.add_card

import com.example.uzum.domain.model.Card
import com.example.uzum.domain.repo.CardRepository
import com.example.uzum.presentation.arcitecture.BaseViewModel
import com.example.uzum.presentation.view.fragments.add_card.AddCardViewModel.*
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AddCardViewModel @Inject constructor(
    private val cardRepository: CardRepository
    ) :
    BaseViewModel<State, Input, Effect>() {


    data class State(
        val cardNumber: String? = null,
        val cardExpireDate: String? = null,
        val loading: Boolean = false

    ) {
        val isEnabled: Boolean get() = loading.not() && cardNumber != null && cardExpireDate != null
    }

    sealed class Input {
        data class SetCardNumber(val cardNumber: String) : Input()
        data class SetCardEDate(val eDate: String) : Input()
        object AddCard : Input()
    }

    class Effect

    override fun getInitialState(): State = State()

    override fun processInput(input: Input) {
        when (input) {
            is Input.SetCardEDate -> updateState { it.copy(cardExpireDate = input.eDate) }
            is Input.SetCardNumber -> updateState { it.copy(cardNumber = input.cardNumber) }
            Input.AddCard -> addCard()
        }
    }

    private fun addCard() = cardRepository.writeToDataBase(
        card = Card(
            id = 0,
            sum = "0.00",
            cardNumber = state.value.cardNumber!!,
            ownerName = "Akhmedov Shamsiddin",
            validThru = state.value.cardExpireDate!!
        )
    )
}