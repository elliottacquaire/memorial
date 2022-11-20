package com.exae.memorialapp.bean

import com.google.gson.annotations.SerializedName

open class ProtocolResponse<T> {
    @SerializedName("message")
    val message: String? = null

    @SerializedName("code")
    val code: String? = null

    @SerializedName("data")
    val data: T? = null
}