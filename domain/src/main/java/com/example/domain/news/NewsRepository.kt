package com.example.domain.news

import com.example.common.models.news.News
import com.example.common.utils.DataState
import kotlinx.coroutines.flow.Flow

interface NewsRepository {
    suspend fun getNews() : Flow<DataState<List<News>>>
}