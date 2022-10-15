package com.example.firebase

import android.net.Uri
import android.util.Log
import com.example.common.models.Category
import com.example.common.models.Problem
import com.example.common.utils.*
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.GenericTypeIndicator
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirebaseManager @Inject constructor() {

    private val database: DatabaseReference = Firebase.database.reference
    private val storageRef = Firebase.storage.reference

    fun getCategories(): Flow<DataState<List<Category>>> = flow {
        emit(DataState.Loading)
        try {
            val myQuery = database.child(FIREBASE_DATABASE_CATEGORIES).get().await()
            val value = myQuery.getValue(object :
                GenericTypeIndicator<List<Category>>() {})
            value?.let { categories ->
                emit(DataState.Success(categories))
            }
        } catch (e: Exception) {
            emit(DataState.Error(e))
        }
    }

    fun addProblem(problem: Problem): Flow<DataState<String>> = flow {
        emit(DataState.Loading)
        try {
            database.child(FIREBASE_DATABASE_PROBLEMS).child(problem.id).setValue(problem).await()
            emit(DataState.Success(problem.id))
        } catch (e: Exception) {
            emit(DataState.Error(e))
        }
    }

    fun uploadImagesToStorage(uri: Uri, problemId: String) {
        //emit(DataState.Loading)
        val problemRef = storageRef.child("$STORAGE_IMAGES/$STORAGE_PROBLEM_REPORT_IMAGES/${problemId}/${uri.lastPathSegment}")
        val uploadTask = problemRef.putFile(uri)
        uploadTask.addOnSuccessListener {
            val downloadUrl = problemRef.downloadUrl
            downloadUrl.addOnSuccessListener {
                Log.d(TAG, "uploadImagesToStorage - Success")
                //emit(DataState.Success(it))
                updateImagesInDatabase(it, problemId)
            }
        }
        uploadTask.addOnFailureListener {
            Log.d(TAG, "uploadImagesToStorage - Error")
            //emit(DataState.Error(it))
        }
    }

    private fun updateImagesInDatabase(uri: Uri?, problemId: String) {
        database.child(FIREBASE_DATABASE_PROBLEMS).child(problemId).child("media").setValue(uri.toString())
            .addOnSuccessListener {
                Log.d(TAG, "updateImagesInDatabase - Success")
            }.addOnFailureListener {
                Log.d(TAG, "updateImagesInDatabase - Error")
            }
    }

    suspend fun getNews() = flow {
        emit(DataState.Loading)
        //emit(DataState.Success())
        //emit(DataState.Error(it))
    }

    suspend fun getPartners() = flow {
        emit(DataState.Loading)
        //emit(DataState.Success())
        //emit(DataState.Error(it))
    }

    suspend fun getContactDetails() = flow {
        emit(DataState.Loading)
        //emit(DataState.Success())
        //emit(DataState.Error(it))
    }

    suspend fun getDonateDetails() = flow {
        emit(DataState.Loading)
        //emit(DataState.Success())
        //emit(DataState.Error(it))
    }
}
