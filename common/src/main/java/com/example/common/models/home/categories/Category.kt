package com.example.common.models.home.categories

import java.io.Serializable

data class Category(
    val id: String,
    val name: String,
    val description: String,
    val image: String,
    val subcategories: ArrayList<Subcategory>? = ArrayList()
) : Serializable {
    constructor(id: String, name: String, description: String, image: String) :this(id, name, description, image, null)
    constructor() :this(id= "", name= "", description= "", image= "", null)
}