package org.zelenikljuc.domain.donate

import org.zelenikljuc.common.models.donation.Donation
import org.zelenikljuc.common.utils.DataState
import org.zelenikljuc.firebase.FirebaseManager
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DonateRepositoryImpl
@Inject constructor(
    private val firebaseManager: FirebaseManager
) : DonateRepository {
    override suspend fun getDonateDetails(): Flow<DataState<Donation>> = firebaseManager.getDonateDetails()
}