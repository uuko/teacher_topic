package com.example.linteacher.util

import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody

open class BaseRepository {

    fun createPartFromString(string: String?): RequestBody {
        return string!!.toRequestBody(MultipartBody.FORM)
    }
}