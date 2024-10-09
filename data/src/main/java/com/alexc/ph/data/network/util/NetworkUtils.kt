package com.alexc.ph.data.network.util

import retrofit2.Response

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