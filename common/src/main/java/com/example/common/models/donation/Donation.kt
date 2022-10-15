package com.example.common.models.donation

import java.io.Serializable

data class Donation(
    val cardNumber: String,
    val description: String,
) : Serializable {
    constructor() :this(cardNumber="", description="")
}