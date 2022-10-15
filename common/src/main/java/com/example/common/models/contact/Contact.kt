package com.example.common.models.contact

import java.io.Serializable

data class Contact(
    val description: String,
    val phoneNumber: String,
    val email: String,
    val webAddress: String,
    val instagramProfile: String,
    val facebookProfile: String,
) : Serializable {
    constructor() :this(description = "", phoneNumber = "", email = "", webAddress = "", instagramProfile = "", facebookProfile = "")
}