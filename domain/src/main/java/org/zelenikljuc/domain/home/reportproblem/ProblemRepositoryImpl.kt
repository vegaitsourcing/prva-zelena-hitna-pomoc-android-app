package org.zelenikljuc.domain.home.reportproblem

import android.net.Uri
import org.zelenikljuc.common.models.home.reportproblem.Problem
import org.zelenikljuc.common.utils.DataState
import org.zelenikljuc.firebase.FirebaseManager
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ProblemRepositoryImpl
@Inject constructor(
    private val firebaseManager: FirebaseManager
) : ProblemRepository {

    override suspend fun reportProblem(problem: Problem): Flow<DataState<String>> = firebaseManager.addProblem(problem)

    override fun uploadImagesToStorage(mediaFiles: ArrayList<Uri>, problemId: String) {
        firebaseManager.uploadImagesToStorage(mediaFiles, problemId)
    }
}