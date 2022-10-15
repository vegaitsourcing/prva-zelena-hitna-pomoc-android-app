package com.example.common.models.partners

import java.io.Serializable

data class PartnerDetails(
    val description: String,
    val listOfPartners: ArrayList<Partner>? = ArrayList(),
) : Serializable {
    constructor(): this(description="", null)
}