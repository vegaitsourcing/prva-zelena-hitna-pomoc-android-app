package com.example.common.utils

import java.lang.Exception

sealed class DataState<out T> {
    data class Success<T>(val data: T) : DataState<T>()
    data class Error(val exception: Exception) : DataState<Nothing>()
    data class ErrorMessage(val message: String) : DataState<Nothing>()
    object Loading : DataState<Nothing>()
}