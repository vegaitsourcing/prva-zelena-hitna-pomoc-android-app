package com.example.domain.news

import com.example.common.models.news.News
import com.example.common.utils.DataState
import com.example.firebase.FirebaseManager
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class NewsRepositoryImpl
@Inject constructor(
    private val firebaseManager: FirebaseManager
) : NewsRepository {
    override suspend fun getNews(): Flow<DataState<List<News>>> = firebaseManager.getNews()
}