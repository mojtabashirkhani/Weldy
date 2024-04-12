package com.example.weldy.extensions

fun <T, R> T.transform(transformer: (T) -> R) = transformer(this)
