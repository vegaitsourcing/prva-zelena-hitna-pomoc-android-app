package com.example.common.models

import java.io.Serializable

data class Subcategory(
    val id: String,
    val name: String,
    val url: String,
) : Serializable {
    constructor(): this(id="", name="", url="")
}