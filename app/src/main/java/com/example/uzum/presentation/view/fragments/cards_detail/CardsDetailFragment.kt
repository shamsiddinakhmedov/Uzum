package com.example.uzum.presentation.view.fragments.cards_detail

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.example.uzum.databinding.FragmentCardsDetailBinding
import com.example.uzum.domain.model.Card
import com.example.uzum.presentation.arcitecture.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CardsDetailFragment :
    BaseFragment<FragmentCardsDetailBinding>(FragmentCardsDetailBinding::inflate) {
    private val adapter = CardsDetailAdapter(this::onClick)
    private val viewModel by viewModels<CardsDetailViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi()
        viewModel.state.collect(this::renderCardsList) { it.listCards }
    }

    private fun initUi() = with(binding) {
        cardsList.adapter = adapter
    }

    private fun renderCardsList(cards: List<Card>?) {
        adapter.submitList(cards)
    }

    private fun onClick(card: Card) {

    }
}