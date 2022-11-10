package org.zelenikljuc.domain.contact

import org.zelenikljuc.common.models.contact.Contact
import org.zelenikljuc.common.utils.DataState
import kotlinx.coroutines.flow.Flow

interface ContactRepository {
    suspend fun getContacts() : Flow<DataState<Contact>>
}