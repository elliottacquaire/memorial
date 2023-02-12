package com.exae.memorialapp.bean

import com.google.gson.annotations.SerializedName

class LoginResultModel(
//    @SerializedName("code")
//    val code: Int,
//    @SerializedName("msg")
//    val msg: String?,
    @SerializedName("token")
    val token: String = ""
)

class LoginResultModel1(
    @SerializedName("user_id")
    val user_id: Int,
    @SerializedName("user_name")
    val user_name: String?,
    @SerializedName("city")
    val city: String = "",
    @SerializedName("district")
    var district: String = "",
    @SerializedName("vehicle_model")
    val vehicle_model: String = ""
)
class BannerResponse : ProtocolResponse<ArrayList<BannerModel>>()
class BannerModel(
    @SerializedName("id")
    val ids: Int,
    @SerializedName("picUrl")
    val picUrl: String?,
    @SerializedName("linkUrl")
    val linkUrl: String = ""
)
