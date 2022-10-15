package com.example.domain.donate

import com.example.common.models.donation.Donation
import com.example.common.utils.DataState
import com.example.firebase.FirebaseManager
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DonateRepositoryImpl
@Inject constructor(
    private val firebaseManager: FirebaseManager
) : DonateRepository {
    override suspend fun getDonateDetails(): Flow<DataState<Donation>> = firebaseManager.getDonateDetails()
}