package com.example.uzum.presentation.view.fragments.cards_detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.uzum.databinding.CardItemBinding
import com.example.uzum.domain.model.Card
import java.text.DecimalFormat

class CardsDetailAdapter(val onClick: (Card) -> Unit) :
    ListAdapter<Card, CardsDetailAdapter.ViewHolder>(DIFF_UTIL) {

    inner class ViewHolder(private val binding: CardItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(card: Card) = with(binding) {
            root.setOnClickListener {
                onClick(card)
            }

            val formattedSum =
                DecimalFormat("#,###.##").format(card.sum.toDouble()).toString().replace(",", " ")
                    .plus(" UZS")
            sum.text = formattedSum

            cardNumber.text = card.cardNumber
                .replaceRange(6, 12, "******")
                .cardNumberFormat

            name.text = card.ownerName
            validThru.text = card.validThru
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            CardItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(getItem(position))
    }

    companion object {
        val DIFF_UTIL = object : DiffUtil.ItemCallback<Card>() {
            override fun areItemsTheSame(oldItem: Card, newItem: Card): Boolean = oldItem == newItem

            override fun areContentsTheSame(oldItem: Card, newItem: Card): Boolean =
                oldItem == newItem

        }
    }

    val String.cardNumberFormat
        get() = run {
            var result = ""
            forEachIndexed { index, c ->
                if (index in listOf(4, 8, 12)) {
                    result += (" $c")
                } else {
                    result += c
                }
            }
            result
        }

}