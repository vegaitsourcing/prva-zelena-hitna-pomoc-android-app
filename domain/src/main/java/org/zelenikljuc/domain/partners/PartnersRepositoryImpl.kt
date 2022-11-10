package org.zelenikljuc.domain.partners

import org.zelenikljuc.common.models.partners.PartnerDetails
import org.zelenikljuc.common.utils.DataState
import org.zelenikljuc.firebase.FirebaseManager
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PartnersRepositoryImpl
@Inject constructor(
    private val firebaseManager: FirebaseManager
) : PartnersRepository {
    override suspend fun getPartners(): Flow<DataState<PartnerDetails>> = firebaseManager.getPartners()
}