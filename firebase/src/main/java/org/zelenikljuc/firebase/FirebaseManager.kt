package org.zelenikljuc.firebase

import android.net.Uri
import android.util.Log
import org.zelenikljuc.common.models.contact.Contact
import org.zelenikljuc.common.models.donation.Donation
import org.zelenikljuc.common.models.home.categories.Category
import org.zelenikljuc.common.models.home.reportproblem.Problem
import org.zelenikljuc.common.models.home.wastedisposal.WasteDisposal
import org.zelenikljuc.common.models.news.News
import org.zelenikljuc.common.models.partners.PartnerDetails
import org.zelenikljuc.common.utils.*
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

    fun getWasteDisposalLocations(): Flow<DataState<WasteDisposal>> = flow {
        emit(DataState.Loading)
        try {
            val myQuery = database.child(FIREBASE_DATABASE_WASTE_DISPOSAL).get().await()
            val value = myQuery.getValue(object :
                GenericTypeIndicator<WasteDisposal>() {})
            value?.let { wasteDisposalDetails ->
                emit(DataState.Success(wasteDisposalDetails))
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

    fun uploadImagesToStorage(mediaFiles: ArrayList<Uri>, problemId: String) {
        var imagesURL: ArrayList<String> = ArrayList()
        for (file in mediaFiles) {
            val problemRef =
                storageRef.child("$STORAGE_IMAGES/$STORAGE_PROBLEM_REPORT_IMAGES/${problemId}/${file.lastPathSegment}")
            val uploadTask = problemRef.putFile(file)
            uploadTask.addOnSuccessListener {
                val downloadUrl = problemRef.downloadUrl
                downloadUrl.addOnSuccessListener {
                    Log.d(TAG, "uploadImagesToStorage - Success")
                    imagesURL.add(it.toString())
                    updateImagesInDatabase(imagesURL, problemId)
                }
            }
            uploadTask.addOnFailureListener {
                Log.d(TAG, "uploadImagesToStorage - Error: $it")
            }
        }
    }

    private fun updateImagesInDatabase(imagesURL: ArrayList<String>, problemId: String) {
        database.child(FIREBASE_DATABASE_PROBLEMS).child(problemId).child("imagesURL").setValue(imagesURL)
            .addOnSuccessListener {
                Log.d(TAG, "updateImagesInDatabase - Success")
            }.addOnFailureListener {
                Log.d(TAG, "updateImagesInDatabase - Error: $it")
            }
    }

    suspend fun getNews() = flow {
        emit(DataState.Loading)
        try {
            val myQuery = database.child(FIREBASE_DATABASE_NEWS).get().await()
            val value = myQuery.getValue(object :
                GenericTypeIndicator<List<News>>() {})
            value?.let { news ->
                emit(DataState.Success(news))
            }
        } catch (e: Exception) {
            emit(DataState.Error(e))
        }
    }

    suspend fun getPartners() = flow {
        emit(DataState.Loading)
        try {
            val myQuery = database.child(FIREBASE_DATABASE_PARTNERS).get().await()
            val value = myQuery.getValue(object :
                GenericTypeIndicator<PartnerDetails>() {})
            value?.let { partner ->
                emit(DataState.Success(partner))
            }
        } catch (e: Exception) {
            emit(DataState.Error(e))
        }
    }

    suspend fun getDonateDetails() = flow {
        emit(DataState.Loading)
        try {
            val myQuery = database.child(FIREBASE_DATABASE_DONATION).get().await()
            val value = myQuery.getValue(object :
                GenericTypeIndicator<Donation>() {})
            value?.let { donation ->
                emit(DataState.Success(donation))
            }
        } catch (e: Exception) {
            emit(DataState.Error(e))
        }
    }

    suspend fun getContactDetails() = flow {
        emit(DataState.Loading)
        try {
            val myQuery = database.child(FIREBASE_DATABASE_CONTACTS).get().await()
            val value = myQuery.getValue(object :
                GenericTypeIndicator<Contact>() {})
            value?.let { contact ->
                emit(DataState.Success(contact))
            }
        } catch (e: Exception) {
            emit(DataState.Error(e))
        }
    }
}
