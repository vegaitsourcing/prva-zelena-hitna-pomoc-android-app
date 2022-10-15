package com.example.common.models.home.reportproblem

data class Problem(
    val id: String,
    val userName: String,
    val location: String,
    val category: String,
    val description: String,
    val media: String,
    val timestamp: String,
)