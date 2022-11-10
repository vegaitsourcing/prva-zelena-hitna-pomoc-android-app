package org.zelenikljuc.domain.home.wastedisposal

import org.zelenikljuc.common.models.home.wastedisposal.WasteDisposal
import org.zelenikljuc.common.utils.DataState
import org.zelenikljuc.firebase.FirebaseManager
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class WasteDisposalRepositoryImpl
@Inject constructor(
    private val firebaseManager: FirebaseManager
) : WasteDisposalRepository {
    override suspend fun getWasteDisposalDetails() : Flow<DataState<WasteDisposal>> = firebaseManager.getWasteDisposalLocations()
}