package com.example.hakaton.ui.home.reportproblem

import android.net.Uri
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.common.models.home.reportproblem.Problem
import com.example.common.utils.DataState
import com.example.common.utils.TAG
import com.example.domain.home.reportproblem.ProblemRepository
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

    var images: ArrayList<Uri>? = ArrayList()

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
        repository.uploadImagesToStorage(images!![0], problemId = problemId) //optimizuj za listu!
    }
}