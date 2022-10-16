package com.example.domain.home.reportproblem

import android.net.Uri
import com.example.common.models.home.reportproblem.Problem
import com.example.common.utils.DataState
import kotlinx.coroutines.flow.Flow

interface ProblemRepository {

    suspend fun reportProblem(problem: Problem): Flow<DataState<String>>

    fun uploadImagesToStorage(mediaFiles: ArrayList<Uri>, problemId: String)
}