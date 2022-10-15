package com.example.domain.home.categories

import com.example.common.models.Category
import com.example.common.utils.DataState
import kotlinx.coroutines.flow.Flow

interface CategoryRepository {
    fun getCategories() : Flow<DataState<List<Category>>>
}