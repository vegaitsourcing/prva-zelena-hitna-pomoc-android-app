package org.zelenikljuc.domain.home.wastedisposal

import org.zelenikljuc.common.models.home.wastedisposal.WasteDisposal
import org.zelenikljuc.common.utils.DataState
import kotlinx.coroutines.flow.Flow

interface WasteDisposalRepository {
    suspend fun getWasteDisposalDetails() : Flow<DataState<WasteDisposal>>
}