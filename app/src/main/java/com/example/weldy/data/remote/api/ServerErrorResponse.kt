package com.example.weldy.data.remote.api

data class ServerErrorResponse(
    val status:Int = -1,
    val data: Payload?
)

data class Payload(
    val message:String?
)
