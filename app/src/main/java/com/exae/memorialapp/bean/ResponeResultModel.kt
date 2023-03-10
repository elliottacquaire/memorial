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

class ManageMemorialResponse : ProtocolResponse<ArrayList<ManageMemorialModel>>()
class ManageMemorialModel(
    @SerializedName("id")
    val ids: Int,
    @SerializedName("picUrl")
    val picUrl: String?,
    @SerializedName("hallNum")
    val hallNum: String = "",
    @SerializedName("hallName")
    val hallName: String = "",
    @SerializedName("hallCreateTime")
    val hallCreateTime: String = "",
    @SerializedName("hallLevel")
    val hallLevel: String = "",
    @SerializedName("linkUrl")
    val linkUrl: String = ""
)

class StyleMemorialResponse : ProtocolResponse<ArrayList<StyleMemorialModel>>()
class StyleMemorialModel(
    @SerializedName("id")
    val ids: Int,
    @SerializedName("picUrl")
    val picUrl: String?,
    @SerializedName("name")
    val name: String = "",
    @SerializedName("repeatUse")
    val repeatUse: Int = 0
)

class ChargeMoneyModel(
    @SerializedName("id")
    val ids: Int,
    @SerializedName("name")
    val name: String?,
    @SerializedName("num")
    val num: String = "",
    @SerializedName("coinNum")
    val coinNum: String = "",
    @SerializedName("tips")
    val tips: String = "",
    @SerializedName("price")
    val price: String = "",
)