package com.example.weldy.data.remote.api


import com.example.weldy.extensions.fromJson
import okhttp3.ResponseBody
import retrofit2.HttpException
import retrofit2.Response
import java.nio.charset.Charset
import com.google.gson.annotations.SerializedName as SN


enum class Status {
    SUCCESS,
    ERROR,
    LOADING
}

data class NetworkError(
        @SN("code")
        var code: String? = "",
        @SN("message")
        var message: String? = "",
)

val ResponseBody?.safeString : String? get() {
    try {
        if (this != null) {
            val source = source()
            source.request(Long.MAX_VALUE)
            return source.readString(Charset.forName("UTF-8"))
        }
    } catch (ignored: Exception) {}
    return null
}
val Response<*>.errorText : String? get() {
    try {
        val responseBody = errorBody()
        if (responseBody != null) {
            val source = responseBody.source()
            source.request(Long.MAX_VALUE)
            return source.readString(Charset.forName("UTF-8"))
        }
    } catch (ignored: Exception) {}
    return null
}


data class Resource<T>(var status: Status, var data: T?, var message: String?, var errorObject: NetworkError? = null) {
    companion object {
        fun <T> success(data: T?): Resource<T> {
            return Resource(
                Status.SUCCESS,
                data,
                null
            )
        }

        fun <T> error(msg: String?, data: T? = null, err: NetworkError? = null): Resource<T> {
            return Resource(
                Status.ERROR,
                data,
                msg,
                err,
            )
        }

        fun <T> error(e: HttpException): Resource<T> {
            val json = e.response()?.errorText
            val err = json?.fromJson<ServerErrorResponse>()
            return Resource(
                Status.ERROR,
                null,
                err?.data?.message ?: json,
                NetworkError(err?.status?.toString(),err?.data?.message)
            )
        }

        fun <T> loading(data: T? = null): Resource<T> {
            return Resource(
                Status.LOADING,
                data,
                null
            )
        }
    }
    fun isSuccess() : Boolean = status == Status.SUCCESS
    fun isError() : Boolean = status == Status.ERROR
    fun isLoading() : Boolean = status == Status.LOADING

    fun toSuccess(data: T? = null) {
        this.data = data ?: this.data
        this.status = Status.SUCCESS
    }
    fun toLoading(data: T? = null) {
        this.data = data ?: this.data
        this.status = Status.LOADING
    }
    fun toError(msg: String? = null, data: T? = null) {
        this.data = data ?: this.data
        this.status = Status.ERROR
        this.message = msg
    }

    fun<U> map( mapper: (T?)->(U?) ) = Resource(
        status,
        mapper(data),
        message,
        errorObject
    )
}
