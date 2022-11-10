package org.zelenikljuc.domain.home.reportproblem

import android.net.Uri
import org.zelenikljuc.common.models.home.reportproblem.Problem
import org.zelenikljuc.common.utils.DataState
import kotlinx.coroutines.flow.Flow

interface ProblemRepository {

    suspend fun reportProblem(problem: Problem): Flow<DataState<String>>

    fun uploadImagesToStorage(mediaFiles: ArrayList<Uri>, problemId: String)
}