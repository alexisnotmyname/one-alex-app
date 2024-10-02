package com.alexc.ph.data.util

sealed class OAResults<out T> {
    data class Success<out T>(val data: T) : OAResults<T>()
    data class Error(val exception: Throwable? = null) : OAResults<Nothing>()
    object Loading : OAResults<Nothing>()
}