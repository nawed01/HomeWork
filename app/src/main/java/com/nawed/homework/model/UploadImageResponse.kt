package com.nawed.homework.model

data class UploadImageResponse(
    var cid: String?,
    var description: String?,
    var error: Boolean?,
    var message: String?,
    var result: Result?,
    var statusCode: Int?,
    var success: Boolean?
)