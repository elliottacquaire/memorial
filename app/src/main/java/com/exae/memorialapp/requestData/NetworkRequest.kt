package com.exae.memorialapp.requestData

import com.google.gson.annotations.SerializedName

const val GATEWAY = "pdc-api-gateway"
const val API_VERSION = "v1"
const val DRS_MOBILE_BUSINESS_NAME = "drs-mobile-bff"
const val DRS_MOBILE_TEST_DRIVE_NAME = "drs-customer-service"
const val DRS_FLOW_SERVICE_NAME = "drs-flow-service"
const val POS_SALES_BUSINESS_NAME = "drs-sales-service"
const val BUSINESS_PATH = "/$GATEWAY/$DRS_MOBILE_BUSINESS_NAME/$API_VERSION"
const val REQUESTCARPATH = "/api/v1/vehicle"
const val REQUESLOGINPATH = "/prod-api/login"

open class BaseRequest(@Transient open var path: String)

//登陆
data class LoginRequest(var code: String = "") :
    BaseRequest("$BUSINESS_PATH/permission/token/$code")

data class BannerRequest(var code: String = "") :
    BaseRequest("/prod-api/banner/listAll")

data class AttentionListRequest(var code: String = "") :
    BaseRequest("/prod-api/user/memorial/attention/listAll")

data class MemorialListAllRequest(var code: String = "") :
    BaseRequest("/prod-api/user/memorial/listAll")

//手机验证码获取
data class VerificationCodeRequest(
    @SerializedName("phone")
    var phone: String = ""
) : BaseRequest("$BUSINESS_PATH/mbff/sms/code")

data class PasswordCodeLoginRequest(
    @SerializedName("phone")
    var phone: String = "",
    @SerializedName("verifyCode")
    var verify_code: String = ""
) : BaseRequest("$BUSINESS_PATH/mbff/sms/token")

data class VerificationCodeLoginRequest(
    @SerializedName("username")
    var phone: String = "",
    @SerializedName("password")
    var verify_code: String = ""
) : BaseRequest("/prod-api/loginApp")

data class UserRequest(
    @SerializedName("id")
    var id: Int = 0
) : BaseRequest("/api/v1/users/1")

data class UserRequest3(
    @SerializedName("id")
    var id: Int = 0
) : BaseRequest("/api/v1/users/3")

data class UserRequest4(
    @SerializedName("id")
    var id: Int = 0
) : BaseRequest("/api/v1/users/4")

data class FindCarToUse(
    @SerializedName("id")
    var id: Int = 0
) : BaseRequest("/api/v1/users/4")

//发布车辆
data class PublicCarRequest(
//    @SerializedName("province")
//    var province: String = "",
//    @SerializedName("city")
//    var city: String = "",
//    @SerializedName("carType")
//    var carType: String = "",
    @SerializedName("start_time")
    var startTime: Long = 0,
    @SerializedName("end_time")
    var endTime: Long = 0

) : BaseRequest("$REQUESTCARPATH/publish")

data class SingleMemorialRequest(
    @SerializedName("name")
    var name: String = "",
    @SerializedName("birthDate")
    var birthDate: String = "",
    @SerializedName("leaveDate")
    var leaveDate: String = "",
    @SerializedName("avatarPicUrl")
    var avatarPicUrl: String = "",
    @SerializedName("address")
    var address: String = "",
    @SerializedName("memorialId")
    var memorialId: Int = -1,
    @SerializedName("hallId")
    var hallId: Int = -1,
    @SerializedName("tabletId")
    var tabletId: Int = -1,
    @SerializedName("memorialNo")
    var memorialNo: Int? = null,
    @SerializedName("nation")
    var nation: String = "",
    @SerializedName("sex")
    var sex: String = "",
    @SerializedName("relationship")
    var relationship: String = "",
    @SerializedName("epitaph")
    var epitaph: String = ""
) : BaseRequest("/prod-api/user/memorial/single-memorial")

data class MoreMemorialRequest(
    @SerializedName("name")
    var name: String = "",
    @SerializedName("memorialId")
    var memorialId: Int = -1,
    @SerializedName("hallId")
    var hallId: Int = -1,
    @SerializedName("tabletId")
    var tabletId: Int = -1,
    @SerializedName("memorialNo")
    var memorialNo: Int? = null,
    @SerializedName("theme")
    var theme: String = "",
    @SerializedName("monumentMaker")
    var monumentMaker: String = "",
    @SerializedName("ancestralHome")
    var ancestralHome: String = "",
) : BaseRequest("/prod-api/user/memorial/family-memorial")

data class DoubleMemorialRequest(
    @SerializedName("name")
    var name: String = "",
    @SerializedName("memorialId")
    var memorialId: Int = -1,
    @SerializedName("memorialNo")
    var memorialNo: Int? = null,
    @SerializedName("hallId")
    var hallId: Int = -1,
    @SerializedName("relationship")
    var relationship: String = "",
    @SerializedName("description")
    var description: String = "",

    @SerializedName("name1")
    var name1: String = "",
    @SerializedName("avatarPicUrl1")
    var avatarPicUrl1: String = "",
    @SerializedName("sex1")
    var sex1: String = "",
    @SerializedName("birthDate1")
    var birthDate1: String = "",
    @SerializedName("leaveDate1")
    var leaveDate1: String = "",
    @SerializedName("tabletId1")
    var tabletId1: Int = -1,

    @SerializedName("name2")
    var name2: String = "",
    @SerializedName("avatarPicUrl2")
    var avatarPicUrl2: String = "",
    @SerializedName("sex2")
    var sex2: String = "",
    @SerializedName("birthDate2")
    var birthDate2: String = "",
    @SerializedName("leaveDate2")
    var leaveDate2: String = "",
    @SerializedName("tabletId2")
    var tabletId2: Int = -1,

    ) : BaseRequest("/prod-api/user/memorial/double-memorial")

data class ChooseHallRequest(
    @SerializedName("type")
    var type: Int
) : BaseRequest("/prod-api/hall/listAll/$type")

data class ChooseTableRequest(
    @SerializedName("type")
    var type: Int
) : BaseRequest("/prod-api/tablet/listAll/")

data class ChooseMemorialRequest(
    @SerializedName("type")
    var type: Int
) : BaseRequest("/prod-api/memorial/listAll/$type")

data class UploadImageRequest(
    @SerializedName("username")
    var phone: String = "",
    @SerializedName("password")
    var verify_code: String = ""
) : BaseRequest("/prod-api/common/uploadApp")

data class FindOrder(
    @SerializedName("phone")
    var phone: String = "",
    @SerializedName("verifyCode")
    var verify_code: String = ""
) : BaseRequest("$REQUESTCARPATH/orders")

data class MyOredrCarToUse(
    @SerializedName("phone")
    var phone: String = "",
    @SerializedName("verifyCode")
    var verify_code: String = ""
) : BaseRequest("$REQUESTCARPATH/myorders")

data class SingleDetailRequest(
    @SerializedName("memorialNo")
    var memorialNo: Int
) : BaseRequest("/prod-api/user/memorial/single-memorial/$memorialNo")

data class MoreDetailRequest(
    @SerializedName("memorialNo")
    var memorialNo: Int
) : BaseRequest("/prod-api/user/memorial/family-memorial/$memorialNo")

data class TwoDetailRequest(
    @SerializedName("memorialNo")
    var memorialNo: Int
) : BaseRequest("/prod-api/user/memorial/double-memorial/$memorialNo")

data class DeleteMemorialRequest(
    @SerializedName("memorialNo")
    var memorialNo: Int
) : BaseRequest("/prod-api/user/memorial/$memorialNo")

data class ApplyMemorialRequest(
    @SerializedName("invitationCode")
    var applyCode: String
) : BaseRequest("/prod-api/user/memorial/application/$applyCode")

data class HandleApplyMemorialRequest(
    @SerializedName("id")
    var applyId: Int,
    @SerializedName("status")
    var status: Int,
) : BaseRequest("/prod-api/user/memorial/application/$applyId/$status")

data class ApplyMemorialListAllRequest(var code: String = "") :
    BaseRequest("/prod-api/user/memorial/application/listAll")

data class HandleApplyMemorialListAllRequest(var code: String = "") :
    BaseRequest("/prod-api/user/memorial/audit/listAll")

