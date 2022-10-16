package com.example.domain.home.wastedisposal

import com.example.common.models.home.wastedisposal.WasteDisposal
import com.example.common.utils.DataState
import com.example.firebase.FirebaseManager
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class WasteDisposalRepositoryImpl
@Inject constructor(
    private val firebaseManager: FirebaseManager
) : WasteDisposalRepository {
    override suspend fun getWasteDisposalDetails() : Flow<DataState<WasteDisposal>> = firebaseManager.getWasteDisposalLocations()
}