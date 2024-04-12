package com.example.weldy.api

data class ServerErrorResponse(
    val status:Int = -1,
    val data:Payload?
)

data class Payload(
    val message:String?
)
