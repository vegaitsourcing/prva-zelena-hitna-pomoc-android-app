package org.zelenikljuc.common.models.partners

import java.io.Serializable

data class Partner(
    val id: String,
    val name: String,
    val city: String,
    val url: String,
    val logo: String,
) : Serializable {
    constructor() :this(id= "", name= "", city= "", url= "", logo = "")
}