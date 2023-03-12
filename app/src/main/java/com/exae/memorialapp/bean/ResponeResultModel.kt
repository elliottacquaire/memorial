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
    @SerializedName("ememorialNo")
    val ememorialNo: Int,
    @SerializedName("type")
    val type: String?,
    @SerializedName("name")
    val name: String = "",
    @SerializedName("picUrlPrefix")
    val picUrlPrefix: String = "",
    @SerializedName("thumbPicUrl")
    val thumbPicUrl: String = "",
    @SerializedName("createTime")
    val createTime: String = "",
    @SerializedName("ownerUser")
    val ownerUser: Int? = null,
    @SerializedName("worshipValue")
    val worshipValue: Int? = null,
    @SerializedName("ownerNickName")
    val ownerNickName: String = ""
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

class UploadImageResponse : ProtocolResponse<UploadImageModel>()
class UploadImageModel(
    @SerializedName("id")
    val ids: Int,
    @SerializedName("fileName")
    val fileName: String?,
    @SerializedName("url")
    val url: String = ""
)

class SingleMemorialResponse : ProtocolResponse<SingleMemorialModel>()
class SingleMemorialModel(
    @SerializedName("name")
    var name: String = "",
    @SerializedName("birthDate")
    var birthDate: String? = "",
    @SerializedName("leaveDate")
    var leaveDate: String? = "",
    @SerializedName("avatarPicUrl")
    var avatarPicUrl: String? = "",
    @SerializedName("address")
    var address: String = "",
    @SerializedName("ememorialId")
    var ememorialId: Int = -1,
    @SerializedName("ememorialName")
    var ememorialName: String = "",
    @SerializedName("ememorialPicUrl")
    var ememorialPicUrl: String = "",
    @SerializedName("hallId")
    var hallId: Int = -1,
    @SerializedName("hallName")
    var hallName: String = "",
    @SerializedName("hallPicUrl")
    var hallPicUrl: String = "",
    @SerializedName("tabletId")
    var tabletId: Int = -1,
    @SerializedName("tabletName")
    var tabletName: String = "",
    @SerializedName("tabletPicUrl")
    var tabletPicUrl: String = "",
    @SerializedName("ememorialNo")
    var ememorialNo: Int = -1,
    @SerializedName("nation")
    var nation: String = "",
    @SerializedName("sex")
    var sex: String = "",
    @SerializedName("relationship")
    var relationship: String = "",
    @SerializedName("epitaph")
    var epitaph: String = "",
    @SerializedName("picUrlPrefix")
    var picUrlPrefix: String = "",
    @SerializedName("createTime")
    var createTime: String = "",
    @SerializedName("worshipValue")
    var worshipValue: Int = -1,
    @SerializedName("ownerUser")
    var ownerUser: Int = -1,
)