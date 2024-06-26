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

data class AttentionRequest(var memorialNo: Int) :
    BaseRequest("/prod-api/user/memorial/attention/$memorialNo")

data class AttentionCancelRequest(var memorialNo: Int) :
    BaseRequest("/prod-api/user/memorial/attention/$memorialNo")

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
    var applyCode: String,
    @SerializedName("notes")
    var notes: String,
) : BaseRequest("/prod-api/user/memorial/application")

data class HandleApplyMemorialRequest(
    @SerializedName("id")
    var applyId: Int,
    @SerializedName("status")
    var status: Int,
) : BaseRequest("/prod-api/user/memorial/application/$applyId/$status")

data class ApplyMemorialListAllRequest(var statusType: Int = -1) :
    BaseRequest("/prod-api/user/memorial/application/listAll")

data class HandleApplyMemorialListAllRequest(var status: Int = -1) :
    BaseRequest("/prod-api/user/memorial/audit/listAll")

data class CommentLisRequest(
    @SerializedName("memorialNo")
    var memorialNo: Int,
    @SerializedName("pageNum")
    var pageNum: Int,
    @SerializedName("pageSize")
    var pageSize: Int,
) : BaseRequest("/prod-api/user/memorial/comment/list")

data class AddCommentRequest(
    @SerializedName("memorialNo")
    var memorialNo: Int,
    @SerializedName("comment")
    var comment: String,
) : BaseRequest("/prod-api/user/memorial/comment")

data class DeleteCommentRequest(
//    @SerializedName("memorialNo")
//    var memorialNo: Int,
    @SerializedName("id")
    var commentId: Int,
) : BaseRequest("/prod-api/user/memorial/comment/$commentId")

data class IntroduceRequest(
    @SerializedName("memorialNo")
    var memorialNo: Int
) : BaseRequest("/prod-api/user/memorial/introduction/$memorialNo")

data class CreateIntroduceRequest(
    @SerializedName("memorialNo")
    var memorialNo: Int,
    @SerializedName("introduction")
    var introduction: String,
) : BaseRequest("/prod-api/user/memorial/introduction")

data class ModifyIntroduceRequest(
    @SerializedName("memorialNo")
    var memorialNo: Int,
    @SerializedName("id")
    var introId: Int,
    @SerializedName("introduction")
    var introduction: String,
) : BaseRequest("/prod-api/user/memorial/introduction")

data class DeleteIntroduceRequest(
    @SerializedName("id")
    var ids: Int,
//    @SerializedName("introduction")
//    var introduction: String,
) : BaseRequest("/prod-api/user/memorial/introduction/$ids")

data class ArticleLisRequest(
    @SerializedName("memorialNo")
    var memorialNo: Int,
    @SerializedName("pageNum")
    var pageNum: Int,
    @SerializedName("pageSize")
    var pageSize: Int,
) : BaseRequest("/prod-api/user/memorial/article/list")

data class ArticleDetailRequest(
    @SerializedName("id")
    var articleId: Int,
) : BaseRequest("/prod-api/user/memorial/article/$articleId")

data class CreateArticleRequest(
    @SerializedName("memorialNo")
    var memorialNo: Int,
    @SerializedName("content")
    var content: String,
    @SerializedName("title")
    var title: String,
) : BaseRequest("/prod-api/user/memorial/article")

data class ModifyArticleRequest(
    @SerializedName("memorialNo")
    var memorialNo: Int,
    @SerializedName("id")
    var articleId: Int,
    @SerializedName("content")
    var content: String,
    @SerializedName("title")
    var title: String,
) : BaseRequest("/prod-api/user/memorial/article")

data class DeleteArticleRequest(
    @SerializedName("id")
    var articleId: Int,
) : BaseRequest("/prod-api/user/memorial/article/$articleId")

data class AlbumLisRequest(
    @SerializedName("memorialNo")
    var memorialNo: Int,
    @SerializedName("pageNum")
    var pageNum: Int,
    @SerializedName("pageSize")
    var pageSize: Int,
) : BaseRequest("/prod-api/user/memorial/album/list")

data class CreateAlbumRequest(
    @SerializedName("memorialNo")
    var memorialNo: Int,
    @SerializedName("name")
    var name: String,
) : BaseRequest("/prod-api/user/memorial/album")

data class DeleteAlbumRequest(
    @SerializedName("id")
    var albumId: Int,
) : BaseRequest("/prod-api/user/memorial/album/$albumId")

data class ModifyAlbumRequest(
    @SerializedName("id")
    var albumId: Int,
    @SerializedName("name")
    var name: String,
) : BaseRequest("/prod-api/user/memorial/album")

data class AllMaterialOfferRequest(
    @SerializedName("type")
    var type: String,
) : BaseRequest("/prod-api/material/listAll/$type")

data class AlbumPicLisRequest(
    @SerializedName("albumId")
    var albumId: Int,
    @SerializedName("pageNum")
    var pageNum: Int,
    @SerializedName("pageSize")
    var pageSize: Int,
) : BaseRequest("/prod-api/user/memorial/photo/list")

data class UploadAlbumPicRequest(
    @SerializedName("albumId")
    var albumId: Int,
    @SerializedName("picDesc")
    var picDesc: String,
    @SerializedName("picUrl")
    var picUrl: String,
) : BaseRequest("/prod-api/user/memorial/photo")

data class ModifyAlbumPicRequest(
    @SerializedName("albumId")
    var albumId: Int,
    @SerializedName("id")
    var id: Int,
    @SerializedName("picDesc")
    var picDesc: String,
    @SerializedName("picUrl")
    var picUrl: String,
) : BaseRequest("/prod-api/user/memorial/photo")

data class DeletePicRequest(
    @SerializedName("id")
    var id: Int,
) : BaseRequest("/prod-api/user/memorial/photo/$id")