package org.zelenikljuc.prvazelenahitnapomoc.ui.contact

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import org.zelenikljuc.common.models.contact.Contact
import org.zelenikljuc.common.utils.DataState
import org.zelenikljuc.common.utils.TAG
import org.zelenikljuc.domain.contact.ContactRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ContactViewModel
@Inject constructor(
    private val repository: ContactRepository
) : ViewModel() {

    private val _contactDetails = MutableLiveData<Contact?>()
    val contactDetails: LiveData<Contact?> get() = _contactDetails

    private fun getContacts() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getContacts().collect{ contactDetails->
                when(contactDetails){
                    is  DataState.Loading -> {
                        Log.d(TAG, "getContacts: Loading..")
                    }
                    is  DataState.Success -> {
                        Log.d(TAG, "getContacts - Success: ${contactDetails.data}")
                        _contactDetails.postValue(contactDetails.data)
                    }
                    is  DataState.Error -> {
                        Log.d(TAG, "getContacts - Error: ${contactDetails.exception}")
                    }
                    else -> {}
                }
            }
        }
    }

    init {
        getContacts()
    }
}