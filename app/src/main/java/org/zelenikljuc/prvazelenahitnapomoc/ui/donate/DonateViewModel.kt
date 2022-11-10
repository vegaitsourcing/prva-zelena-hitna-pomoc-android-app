package org.zelenikljuc.prvazelenahitnapomoc.ui.donate

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import org.zelenikljuc.common.models.donation.Donation
import org.zelenikljuc.common.utils.DataState
import org.zelenikljuc.common.utils.TAG
import org.zelenikljuc.domain.donate.DonateRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DonateViewModel
@Inject constructor(
    private val repository: DonateRepository
) : ViewModel() {

    private val _donateDetails = MutableLiveData<Donation?>()
    val donateDetails: LiveData<Donation?> get() = _donateDetails

    private fun getDonateDetails() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getDonateDetails().collect{ donateDetails->
                when(donateDetails){
                    is  DataState.Loading -> {
                        Log.d(TAG, "getDonateDetails: Loading..")
                    }
                    is  DataState.Success -> {
                        Log.d(TAG, "getDonateDetails - Success: ${donateDetails.data}")
                        _donateDetails.postValue(donateDetails.data)
                    }
                    is  DataState.Error -> {
                        Log.d(TAG, "getDonateDetails - Error: ${donateDetails.exception}")
                    }
                    else -> {}
                }
            }
        }
    }

    init {
        getDonateDetails()
    }
}