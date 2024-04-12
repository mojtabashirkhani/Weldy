package com.example.weldy.extensions

import com.google.gson.Gson
import java.lang.reflect.Type


fun <T> String.fromJson(cls: Type): T? {

    try {
        return Gson().fromJson<T>(this, cls)
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return null
}

@Suppress("unused")
inline fun <reified T> String.fromJson(): T? {
    return fromJson(T::class.java)
}


