package org.zelenikljuc.domain.home.categories

import org.zelenikljuc.common.models.home.categories.Category
import org.zelenikljuc.common.utils.DataState
import org.zelenikljuc.firebase.FirebaseManager
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CategoryRepositoryImpl
@Inject constructor(
    private val firebaseManager: FirebaseManager
) : CategoryRepository {
    override fun getCategories() : Flow<DataState<List<Category>>> = firebaseManager.getCategories()

}