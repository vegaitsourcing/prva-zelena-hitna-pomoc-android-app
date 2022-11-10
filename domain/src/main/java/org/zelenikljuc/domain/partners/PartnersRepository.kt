package org.zelenikljuc.domain.partners

import org.zelenikljuc.common.models.partners.PartnerDetails
import org.zelenikljuc.common.utils.DataState
import kotlinx.coroutines.flow.Flow

interface PartnersRepository {
    suspend fun getPartners() : Flow<DataState<PartnerDetails>>
}