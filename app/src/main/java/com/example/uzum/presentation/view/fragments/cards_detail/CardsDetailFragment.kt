package com.example.uzum.presentation.view.fragments.cards_detail

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.example.uzum.databinding.FragmentCardsDetailBinding
import com.example.uzum.domain.model.Card
import com.example.uzum.presentation.arcitecture.BaseFragment
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CardsDetailFragment :
    BaseFragment<FragmentCardsDetailBinding>(FragmentCardsDetailBinding::inflate) {
    private lateinit var fireStore: FirebaseFirestore
    private lateinit var cardReference: CollectionReference
    private val adapter = CardsDetailAdapter(this::onClick)
    lateinit var list: ArrayList<Card>
    private val viewModel by viewModels<CardsDetailViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi()
    }

    private fun initUi() = with(binding) {
        list = ArrayList()

        fireStore = FirebaseFirestore.getInstance()
        cardReference = fireStore.collection("card")
        cardReference.get().addOnSuccessListener { cardList ->
            cardList.forEach {
                list.add(it.toObject(Card::class.java))
            }
            adapter.submitList(list)
        }

        cardsList.adapter = adapter


    }

    private fun onClick(card: Card) {

    }
}