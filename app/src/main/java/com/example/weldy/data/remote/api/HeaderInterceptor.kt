package com.example.weldy.data.remote.api

import okhttp3.Interceptor
import okhttp3.RequestBody
import okhttp3.Response
import okhttp3.ResponseBody
import okio.Buffer
import java.io.IOException
import java.nio.charset.Charset


class HeaderInterceptor() : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {

        var request = chain.request()
        val requestBuilder = request.newBuilder()

        requestBuilder.addHeader("Connection", "keep-alive")
        requestBuilder.addHeader("application-id", "1")
        requestBuilder.addHeader("Pragma", "no-cache")
        requestBuilder.addHeader("Cache-Control", "no-cache")
        requestBuilder.addHeader("Accept","application/json; version=1.0")
        requestBuilder.addHeader("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_14_5) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/84.0.4147.135 Safari/537.36")
        requestBuilder.addHeader("x-api-key", "WELDY")

        request = requestBuilder.build()

        return chain.proceed(request)
    }

}
