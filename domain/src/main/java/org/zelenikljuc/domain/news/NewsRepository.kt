package org.zelenikljuc.domain.news

import org.zelenikljuc.common.models.news.News
import org.zelenikljuc.common.utils.DataState
import kotlinx.coroutines.flow.Flow

interface NewsRepository {
    suspend fun getNews() : Flow<DataState<List<News>>>
}