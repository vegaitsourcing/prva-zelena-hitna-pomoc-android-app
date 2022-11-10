package org.zelenikljuc.common.models.home.wastedisposal

import java.io.Serializable

data class WasteDisposalLocation(
    val id: String,
    val name: String,
    val url: String,
) : Serializable {
    constructor(): this(id="", name="", url="")
}