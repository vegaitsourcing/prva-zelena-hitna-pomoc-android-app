package com.example.domain.partners

import com.example.common.models.Partner
import com.example.common.utils.DataState
import com.example.firebase.FirebaseManager
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PartnersRepositoryImpl
@Inject constructor(
    private val firebaseManager: FirebaseManager
) : PartnersRepository {
    override suspend fun getPartners(): Flow<DataState<List<Partner>>> = firebaseManager.getPartners()
}