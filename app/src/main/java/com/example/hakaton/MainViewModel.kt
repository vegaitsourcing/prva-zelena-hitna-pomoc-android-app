package com.example.hakaton

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.common.models.home.categories.Category
import com.example.common.utils.DataState
import com.example.common.utils.TAG
import com.example.domain.home.categories.CategoryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel
@Inject constructor(
    private val repository: CategoryRepository
) : ViewModel() {

    private val _categories = MutableLiveData<List<Category>?>()
    val categories: LiveData<List<Category>?> get() = _categories

    val showSplash = MutableLiveData(true)

    private fun getCategories() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getCategories().collect{ category->
                when(category){
                    is  DataState.Loading -> {
                        Log.d(TAG, "getCategories: Loading..")
                    }
                    is  DataState.Success -> {
                        Log.d(TAG, "getCategories - Success: ${category.data}")
                        _categories.postValue(category.data)
                    }
                    is  DataState.Error -> {
                        Log.d(TAG, "getCategories - Error: ${category.exception}")
                    }
                    else -> {}
                }
            }
        }
    }

    init {
        getCategories()
        viewModelScope.launch {
            delay(1000)
            showSplash.value = false
        }
    }
}