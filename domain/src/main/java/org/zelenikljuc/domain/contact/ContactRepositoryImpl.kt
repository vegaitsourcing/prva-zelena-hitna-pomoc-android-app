package org.zelenikljuc.domain.contact

import org.zelenikljuc.common.models.contact.Contact
import org.zelenikljuc.common.utils.DataState
import org.zelenikljuc.firebase.FirebaseManager
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ContactRepositoryImpl
@Inject constructor(
    private val firebaseManager: FirebaseManager
) : ContactRepository {
    override suspend fun getContacts(): Flow<DataState<Contact>> = firebaseManager.getContactDetails()
}