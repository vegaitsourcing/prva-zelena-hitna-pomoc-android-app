package org.zelenikljuc.common.models.home.reportproblem

data class Problem(
    var id: String,
    val userName: String,
    val location: String,
    var category: String,
    val description: String,
    val imagesURL: ArrayList<String> =  ArrayList(),
    val timestamp: String,
) : java.io.Serializable {
    constructor() : this(id = "", userName = "", location = "", category = "", description = "", timestamp = "")
}