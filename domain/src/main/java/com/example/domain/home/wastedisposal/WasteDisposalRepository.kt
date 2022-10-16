package com.example.domain.home.wastedisposal

import com.example.common.models.home.wastedisposal.WasteDisposal
import com.example.common.utils.DataState
import kotlinx.coroutines.flow.Flow

interface WasteDisposalRepository {
    suspend fun getWasteDisposalDetails() : Flow<DataState<WasteDisposal>>
}