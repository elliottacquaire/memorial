package com.exae.memorialapp.requestData

import com.google.gson.annotations.SerializedName

const val CLIENT_GROUP = "DRS33043"
const val DEVICE_TYPE = "ANDROID"
const val GATEWAY = "pdc-api-gateway"
const val API_VERSION = "v1"
const val DRS_MOBILE_BUSINESS_NAME = "drs-mobile-bff"
const val DRS_MOBILE_TEST_DRIVE_NAME = "drs-customer-service"
const val DRS_FLOW_SERVICE_NAME = "drs-flow-service"
const val POS_SALES_BUSINESS_NAME = "drs-sales-service"
const val BUSINESS_PATH = "/$GATEWAY/$DRS_MOBILE_BUSINESS_NAME/$API_VERSION"
const val REQUESTCARPATH = "/api/v1/vehicle"

open class BaseRequest(@Transient open var path: String)

//登陆
data class LoginRequest(var code: String = "") :
    BaseRequest("$BUSINESS_PATH/permission/token/$code")

//手机验证码获取
data class VerificationCodeRequest(
    @SerializedName("phone")
    var phone: String = ""
) : BaseRequest("$BUSINESS_PATH/mbff/sms/code")

data class VerificationCodeLoginRequest(
    @SerializedName("phone")
    var phone: String = "",
    @SerializedName("verifyCode")
    var verify_code: String = ""
) : BaseRequest("$BUSINESS_PATH/mbff/sms/token")

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

//查询可用车辆列表
data class SearchCarRequest(
    @SerializedName("province")
    var province: String = "",
    @SerializedName("city")
    var city: String = "",
    @SerializedName("carType")
    var carType: String = "",
    @SerializedName("startTime")
    var startTime: Long = 0,
    @SerializedName("endTime")
    var endTime: Long = 0
) : BaseRequest("$REQUESTCARPATH/available")

data class SendForCarRequest(
    @SerializedName("message")
    var message: String = "",
    @SerializedName("owner_id")
    var owner_id: Int = 0,
    @SerializedName("city")
    var city: String = "",
    @SerializedName("request_vehicle_model")
    var request_vehicle_model: String = "",
    @SerializedName("start_time")
    var startTime: Long = 0,
    @SerializedName("end_time")
    var endTime: Long = 0
) : BaseRequest("$REQUESTCARPATH/requests")

data class FindCarToUse(
    @SerializedName("phone")
    var phone: String = "",
    @SerializedName("verifyCode")
    var verify_code: String = ""
) : BaseRequest("$REQUESTCARPATH/requests")

data class AcceptCarRequest(
    @SerializedName("id")
    var id: String

) : BaseRequest("$REQUESTCARPATH/requests/$id/accept")

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