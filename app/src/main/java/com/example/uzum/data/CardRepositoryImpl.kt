package com.example.uzum.data

import com.example.uzum.domain.model.Card
import com.example.uzum.domain.repo.CardRepository
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import javax.inject.Inject

class CardRepositoryImpl @Inject constructor(
    private val fireStore: FirebaseFirestore
) : CardRepository {
    private lateinit var list: ArrayList<Card>
    var cardReference: CollectionReference = fireStore.collection("card")

    override fun writeToDataBase(card: Card) {
        val cardData = mapOf(
            "type" to
                    if (card.cardNumber.startsWith("86")) 1
                    else if (card.cardNumber.startsWith("98")) 2
                    else if (card.cardNumber.startsWith("99")) 3
                    else 4,
            "cardName" to
                    if (card.cardNumber.startsWith("86")) "TBC"
                    else if (card.cardNumber.startsWith("98")) "SQB"
                    else if (card.cardNumber.startsWith("99")) "Xalq banki"
                    else "Milliy bank",
            "cardNumber" to card.cardNumber,
            "ownerName" to card.ownerName,
            "sum" to "0.00",
            "validThru" to card.validThru
        )
        cardReference.add(cardData).addOnSuccessListener { }
    }

    override fun readToDB(): List<Card> {
        list = ArrayList()
        cardReference.get().addOnSuccessListener { cardList ->
            cardList.forEach {
                list.add(it.toObject(Card::class.java))
            }
        }
        return list
    }
}