package com.example.weldy.data.remote.api

import android.util.Log
import retrofit2.HttpException
import java.net.SocketTimeoutException
import javax.inject.Inject

enum class ErrorCodes(val code: Int) {
    SocketTimeOut(-1)
}

class ResponseHandler  {
    fun <T : Any> handleSuccess(data: T): Resource<T> {

        return Resource.success(data)
    }

    fun <T : Any> handleException(e: Exception): Resource<T> {
        return when (e) {
            is HttpException -> Resource.error(e)
            is SocketTimeoutException -> Resource.error(
                getErrorMessage(ErrorCodes.SocketTimeOut.code),
                null
            )

            else -> Resource.error(getErrorMessage(Int.MAX_VALUE), null)
        }
    }

    private fun getErrorMessage(code: Int): String {
        return when (code) {
            ErrorCodes.SocketTimeOut.code -> "پاسخی یافت نشد (408)"
            401 -> "Unauthorised"
            404 -> "چیزی یافت نشد"
            else -> "خطا در اتصال"
        }
    }

   /* suspend fun <T : Any> apiCall (apiCal: suspend (WeldyApi.() -> T)): Resource<T> {
        return try {
            val response = weldyApi.apiCal()
            handleSuccess(response)
        } catch (e: Exception) {
            Log.d("api", "failed: $e" )
            e.printStackTrace()
            return handleException(e)
        }
    }*/
}




