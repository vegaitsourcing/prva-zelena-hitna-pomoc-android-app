package org.zelenikljuc.prvazelenahitnapomoc.ui.home.wastedisposal

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import org.zelenikljuc.common.models.home.wastedisposal.WasteDisposal
import org.zelenikljuc.common.utils.DataState
import org.zelenikljuc.common.utils.TAG
import org.zelenikljuc.domain.home.wastedisposal.WasteDisposalRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WasteDisposalViewModel
@Inject constructor(
    private val repository: WasteDisposalRepository
    ) : ViewModel() {

    private val _wasteDisposalDetails = MutableLiveData<WasteDisposal?>()
    val wasteDisposalDetails: LiveData<WasteDisposal?> get() = _wasteDisposalDetails

    private fun getWasteDisposalDetails() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getWasteDisposalDetails().collect{ wasteDisposalDetails->
                when(wasteDisposalDetails){
                    is  DataState.Loading -> {
                        Log.d(TAG, "getWasteDisposalDetails: Loading..")
                    }
                    is  DataState.Success -> {
                        Log.d(TAG, "getWasteDisposalDetails - Success: ${wasteDisposalDetails.data}")
                        _wasteDisposalDetails.postValue(wasteDisposalDetails.data)
                    }
                    is  DataState.Error -> {
                        Log.d(TAG, "getWasteDisposalDetails - Error: ${wasteDisposalDetails.exception}")
                    }
                    else -> {}
                }
            }
        }
    }

    init {
        getWasteDisposalDetails()
    }
}

