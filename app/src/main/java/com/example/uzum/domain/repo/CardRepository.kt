package com.example.uzum.domain.repo

import com.example.uzum.domain.model.Card

interface CardRepository {

    fun writeToDataBase(card: Card)

    fun readToDB(): List<Card>
}