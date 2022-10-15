package com.example.domain.home.categories

import com.example.common.models.home.categories.Category
import com.example.common.utils.DataState
import com.example.firebase.FirebaseManager
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CategoryRepositoryImpl
@Inject constructor(
    private val firebaseManager: FirebaseManager
) : CategoryRepository {
    override fun getCategories() : Flow<DataState<List<Category>>> = firebaseManager.getCategories()

}