package org.zelenikljuc.domain.donate

import org.zelenikljuc.common.models.donation.Donation
import org.zelenikljuc.common.utils.DataState
import kotlinx.coroutines.flow.Flow

interface DonateRepository {
    suspend fun getDonateDetails() : Flow<DataState<Donation>>
}