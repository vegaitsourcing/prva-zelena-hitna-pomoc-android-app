package org.zelenikljuc.common.models.home.wastedisposal

import java.io.Serializable

data class WasteDisposal(
    val title: String,
    val description: String,
    val locations: ArrayList<WasteDisposalLocation>? = ArrayList()
) : Serializable {
    constructor() :this(title= "", description= "", null)
}