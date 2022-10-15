package com.example.domain.donate

import com.example.common.models.Donation
import com.example.common.utils.DataState
import kotlinx.coroutines.flow.Flow

interface DonateRepository {
    suspend fun getDonateDetails() : Flow<DataState<List<Donation>>>
}