package org.zelenikljuc.prvazelenahitnapomoc.ui.home.reportproblem

import android.location.Location
import android.net.Uri
import android.util.Log
import androidx.lifecycle.*
import org.zelenikljuc.common.models.home.reportproblem.Problem
import org.zelenikljuc.common.utils.DataState
import org.zelenikljuc.common.utils.TAG
import org.zelenikljuc.domain.home.reportproblem.ProblemRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReportProblemViewModel
@Inject constructor(
    private val repository: ProblemRepository
) : ViewModel() {

    private val _problemReported = MutableLiveData<String?>()
    val problemReported: MutableLiveData<String?> get() = _problemReported

    var problem: Problem = Problem()

    var currentLocation: Location? = null

    var mediaFiles: ArrayList<Uri> = ArrayList()

    fun reportProblem(problem: Problem) {
        viewModelScope.launch {
            repository.reportProblem(problem).collect { problemID ->
                when (problemID) {
                    is DataState.Loading -> {
                        Log.d(TAG, "reportProblem - Loading...")
                    }
                    is DataState.Success -> {
                        Log.d(TAG, "reportProblem - Success: ${problemID.data}")
                        uploadProblemMedia(problemID.data)
                        _problemReported.postValue(problemID.data)
                    }
                    is DataState.Error -> {
                        Log.d(TAG, "reportProblem - Error: ${problemID.exception}")
                    }
                    else -> {
                    }
                }
            }
        }
    }

    private fun uploadProblemMedia(problemId: String) {
        repository.uploadImagesToStorage(mediaFiles, problemId = problemId)
    }
}