package org.zelenikljuc.domain.news

import org.zelenikljuc.common.models.news.News
import org.zelenikljuc.common.utils.DataState
import org.zelenikljuc.firebase.FirebaseManager
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class NewsRepositoryImpl
@Inject constructor(
    private val firebaseManager: FirebaseManager
) : NewsRepository {
    override suspend fun getNews(): Flow<DataState<List<News>>> = firebaseManager.getNews()
}