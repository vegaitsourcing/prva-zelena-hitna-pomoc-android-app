package com.example.common.models.home.reportproblem

data class Problem(
    var id: String,
    val userName: String,
    val location: String,
    var category: String,
    val description: String,
    val media: String,
    val timestamp: String,
) : java.io.Serializable {
    constructor() : this(id = "", userName = "", location = "", category = "", description = "", media = "", timestamp = "")
}