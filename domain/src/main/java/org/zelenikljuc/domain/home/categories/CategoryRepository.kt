package org.zelenikljuc.domain.home.categories

import org.zelenikljuc.common.models.home.categories.Category
import org.zelenikljuc.common.utils.DataState
import kotlinx.coroutines.flow.Flow

interface CategoryRepository {
    fun getCategories() : Flow<DataState<List<Category>>>
}