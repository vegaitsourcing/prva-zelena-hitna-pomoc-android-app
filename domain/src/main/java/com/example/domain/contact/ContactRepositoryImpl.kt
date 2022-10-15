package com.example.domain.contact

import com.example.common.models.contact.Contact
import com.example.common.utils.DataState
import com.example.firebase.FirebaseManager
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ContactRepositoryImpl
@Inject constructor(
    private val firebaseManager: FirebaseManager
) : ContactRepository {
    override suspend fun getContacts(): Flow<DataState<Contact>> = firebaseManager.getContactDetails()
}