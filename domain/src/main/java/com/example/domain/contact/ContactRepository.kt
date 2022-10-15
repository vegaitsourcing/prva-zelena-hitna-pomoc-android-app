package com.example.domain.contact

import com.example.common.models.contact.Contact
import com.example.common.utils.DataState
import kotlinx.coroutines.flow.Flow

interface ContactRepository {
    suspend fun getContacts() : Flow<DataState<Contact>>
}