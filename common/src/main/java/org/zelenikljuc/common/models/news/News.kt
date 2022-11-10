package org.zelenikljuc.common.models.news

import java.io.Serializable

data class News(
    val id: String,
    val title: String,
    val date: String,
    val description: String,
    val image: String,
): Serializable {
    constructor() :this(id= "", title= "", date= "", description= "", image="")
}