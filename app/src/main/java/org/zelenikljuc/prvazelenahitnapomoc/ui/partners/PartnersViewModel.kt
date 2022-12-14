package org.zelenikljuc.prvazelenahitnapomoc.ui.partners

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import org.zelenikljuc.common.models.partners.PartnerDetails
import org.zelenikljuc.common.utils.DataState
import org.zelenikljuc.common.utils.TAG
import org.zelenikljuc.domain.partners.PartnersRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PartnersViewModel
@Inject constructor(
    private val repository: PartnersRepository
) : ViewModel() {

    private val _partnerDetails = MutableLiveData<PartnerDetails?>()
    val partnerDetails: LiveData<PartnerDetails?> get() = _partnerDetails

    private fun getPartners() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getPartners().collect{ partnerDetails->
                when(partnerDetails){
                    is  DataState.Loading -> {
                        Log.d(TAG, "getPartners: Loading..")
                    }
                    is  DataState.Success -> {
                        Log.d(TAG, "getPartners - Success: ${partnerDetails.data}")
                        _partnerDetails.postValue(partnerDetails.data)
                    }
                    is  DataState.Error -> {
                        Log.d(TAG, "getPartners - Error: ${partnerDetails.exception}")
                    }
                    else -> {}
                }
            }
        }
    }

    init {
        getPartners()
    }
}