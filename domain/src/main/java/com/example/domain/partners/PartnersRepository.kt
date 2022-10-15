package com.example.domain.partners

import com.example.common.models.partners.PartnerDetails
import com.example.common.utils.DataState
import kotlinx.coroutines.flow.Flow

interface PartnersRepository {
    suspend fun getPartners() : Flow<DataState<PartnerDetails>>
}