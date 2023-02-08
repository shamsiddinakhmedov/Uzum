package com.example.uzum.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Card(
    var id: Int = 0,
    var sum: String = "",
    var cardNumber: String = "",
    var ownerName: String = "",
    var validThru: String = ""
) : Parcelable