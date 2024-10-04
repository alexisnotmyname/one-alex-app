package com.alexc.ph.data.util

import retrofit2.Response

sealed interface Result<out T> {
    data class Success<T>(val data: T) : Result<T>
    data class Error(val exception: Throwable) : Result<Nothing>
    data object Loading : Result<Nothing>
}

inline fun<T> result(call: () -> Response<T>) : Result<T> {
    return try {
        val response = call()
        if(response.isSuccessful) {
            response.body()?.let {
                Result.Success(it)
            } ?: Result.Error(Exception("Response body is null"))
        } else {
            Result.Error(Exception("Error ${response.code()}: ${response.message()}"))
        }
    } catch (e: Exception) {
        Result.Error(e)
    }
}
