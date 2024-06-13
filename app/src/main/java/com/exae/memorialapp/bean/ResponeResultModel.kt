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

class AttentionListResponse : ProtocolResponse<ArrayList<AttentionListModel>>()
class AttentionListModel(
    @SerializedName("memorialNo")
    val memorialNo: Int,
    @SerializedName("type")
    val type: String? = "",
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


class ManageMemorialResponse : ProtocolResponse<ArrayList<ManageMemorialModel>>()
class ManageMemorialModel(
    @SerializedName("memorialNo")
    val memorialNo: Int,
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
    @SerializedName("memorialId")
    var memorialId: Int = -1,
    @SerializedName("memorialName")
    var memorialName: String = "",
    @SerializedName("memorialPicUrl")
    var memorialPicUrl: String = "",
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
    @SerializedName("memorialNo")
    var memorialNo: Int = -1,
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
    @SerializedName("editable")
    var editable: Boolean = false,
    @SerializedName("invitationCode")
    var invitationCode: String = "",
    @SerializedName("attentionStatus")
    var attentionStatus: Boolean = false,
)

class MoreMemorialResponse : ProtocolResponse<MoreMemorialModel>()
class MoreMemorialModel(
    @SerializedName("name")
    var name: String = "",
    @SerializedName("theme")
    var theme: String? = "",
    @SerializedName("monumentMaker")
    var monumentMaker: String? = "",
    @SerializedName("ancestralHome")
    var ancestralHome: String? = "",
    @SerializedName("avatarPicUrl")
    var avatarPicUrl: String? = "",
    @SerializedName("address")
    var address: String = "",
    @SerializedName("memorialId")
    var memorialId: Int = -1,
    @SerializedName("memorialName")
    var memorialName: String = "",
    @SerializedName("memorialPicUrl")
    var memorialPicUrl: String = "",
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
    @SerializedName("memorialNo")
    var memorialNo: Int = -1,
    @SerializedName("picUrlPrefix")
    var picUrlPrefix: String = "",
    @SerializedName("thumbPicUrl")
    val thumbPicUrl: String = "",
    @SerializedName("createTime")
    var createTime: String = "",
    @SerializedName("worshipValue")
    var worshipValue: Int = -1,
    @SerializedName("ownerUser")
    var ownerUser: Int = -1,
    @SerializedName("editable")
    var editable: Boolean = false,
    @SerializedName("invitationCode")
    var invitationCode: String = "",
    @SerializedName("attentionStatus")
    var attentionStatus: Boolean = false,
)

class DoubleMemorialResponse : ProtocolResponse<DoubleMemorialModel>()
class DoubleMemorialModel(
    @SerializedName("name1")
    var name1: String = "",
    @SerializedName("sex1")
    var sex1: String? = "",
    @SerializedName("birthDate1")
    var birthDate1: String? = "",
    @SerializedName("leaveDate1")
    var leaveDate1: String? = "",
    @SerializedName("tabletId1")
    var tabletId1: Int? = -1,
    @SerializedName("tabletName1")
    var tabletName1: String? = "",
    @SerializedName("tabletPicUrl1")
    var tabletPicUrl1: String? = "",
    @SerializedName("avatarPicUrl1")
    var avatarPicUrl1: String? = "",

    @SerializedName("name2")
    var name2: String = "",
    @SerializedName("sex2")
    var sex2: String? = "",
    @SerializedName("birthDate2")
    var birthDate2: String? = "",
    @SerializedName("leaveDate2")
    var leaveDate2: String? = "",
    @SerializedName("tabletId2")
    var tabletId2: Int? = -1,
    @SerializedName("tabletName2")
    var tabletName2: String? = "",
    @SerializedName("tabletPicUrl2")
    var tabletPicUrl2: String? = "",
    @SerializedName("avatarPicUrl2")
    var avatarPicUrl2: String? = "",

    @SerializedName("name")
    var name: String = "",
    @SerializedName("relationship")
    var relationship: String = "",
    @SerializedName("description")
    var description: String = "",
    @SerializedName("memorialId")
    var memorialId: Int = -1,
    @SerializedName("memorialName")
    var memorialName: String = "",
    @SerializedName("memorialPicUrl")
    var memorialPicUrl: String = "",
    @SerializedName("hallId")
    var hallId: Int = -1,
    @SerializedName("hallName")
    var hallName: String = "",
    @SerializedName("hallPicUrl")
    var hallPicUrl: String = "",
    @SerializedName("memorialNo")
    var memorialNo: Int = -1,
    @SerializedName("picUrlPrefix")
    var picUrlPrefix: String = "",
    @SerializedName("thumbPicUrl")
    val thumbPicUrl: String = "",
    @SerializedName("createTime")
    var createTime: String = "",
    @SerializedName("worshipValue")
    var worshipValue: Int = -1,
    @SerializedName("ownerUser")
    var ownerUser: Int = -1,
    @SerializedName("editable")
    var editable: Boolean = false,
    @SerializedName("invitationCode")
    var invitationCode: String = "",
    @SerializedName("attentionStatus")
    var attentionStatus: Boolean = false,
)

class ApplyMemorialResponse : ProtocolResponse<ApplyMemorialModel>()
class ApplyMemorialModel(
    @SerializedName("memorialNo")
    val memorialNo: Int?,
    @SerializedName("memorialType")
    val memorialType: String?,
    @SerializedName("approved")
    val approved: Boolean = false
)

class DeleteMemorialResponse : ProtocolResponse<DeleteMemorialModel>()
class DeleteMemorialModel(
    @SerializedName("id")
    val ids: Int,
    @SerializedName("fileName")
    val fileName: String?,
    @SerializedName("url")
    val url: String = ""
)

class HandleApplyMemorialResponse : ProtocolResponse<HandleApplyMemorialModel>()
class HandleApplyMemorialModel(
    @SerializedName("id")
    val ids: Int,
    @SerializedName("fileName")
    val fileName: String?,
    @SerializedName("url")
    val url: String = ""
)

class ApplyHistoryListResponse : ProtocolResponse<ArrayList<ApplyListModel>>()
class HandleApplyListResponse : ProtocolResponse<ArrayList<ApplyListModel>>()
class ApplyListModel(
    @SerializedName("memorialNo")
    val memorialNo: Int,
    @SerializedName("updateTime")
    val updateTime: String = "",
    @SerializedName("createTime")
    val createTime: String = "",
    @SerializedName("id")
    val id: Int? = -1,
    @SerializedName("applyUserId")
    val applyUserId: Int? = -1,
    @SerializedName("notes")
    val notes: String = "",
    @SerializedName("auditUserId")
    val auditUserId: Int? = -1,
    @SerializedName("status")
    val status: Int? = -1
)

class CommentListResponse : ProtocolResponse<ArrayList<CommentListModel>>()
class CommentListModel(
    @SerializedName("id")
    val ids: Int,
    @SerializedName("picUrl")
    val picUrl: String?,
    @SerializedName("name")
    val name: String = "",
    @SerializedName("comment")
    val comment: String = "",
    @SerializedName("memorialNo")
    val memorialNo: Int?,
    @SerializedName("createTime")
    val createTime: String = "",
    @SerializedName("repeatUse")
    val repeatUse: Int = 0
)

class AddCommentResponse : ProtocolResponse<AddCommentModel>()
class AddCommentModel(
    @SerializedName("id")
    val ids: Int,
    @SerializedName("picUrl")
    val picUrl: String?,
    @SerializedName("memorialNo")
    val memorialNo: Int?,
    @SerializedName("name")
    val name: String = "",
    @SerializedName("comment")
    val comment: String = "",
    @SerializedName("createTime")
    val createTime: String = "",
    @SerializedName("repeatUse")
    val repeatUse: Int = 0
)

class DeleteCommentResponse : ProtocolResponse<DeleteCommentModel>()
class DeleteCommentModel(
    @SerializedName("id")
    val ids: Int,
    @SerializedName("picUrl")
    val picUrl: String?,
    @SerializedName("name")
    val name: String = "",
    @SerializedName("content")
    val content: String = "",
    @SerializedName("createTime")
    val createTime: String = "",
    @SerializedName("repeatUse")
    val repeatUse: Int = 0
)

class IntroduceResponse : ProtocolResponse<IntroduceModel>()
class IntroduceModel(
    @SerializedName("id")
    val ids: Int,
    @SerializedName("picUrl")
    val picUrl: String?,
    @SerializedName("name")
    val name: String = "",
    @SerializedName("introduction")
    val introduction: String = "",
    @SerializedName("memorialNo")
    val memorialNo: Int = -1,
    @SerializedName("userId")
    val userId: Int = -1
)

class CreateIntroduceResponse : ProtocolResponse<IntroduceModel>()
class CreateIntroduceModel(
    @SerializedName("id")
    val ids: Int,
    @SerializedName("picUrl")
    val picUrl: String?,
    @SerializedName("name")
    val name: String = "",
    @SerializedName("introduction")
    val introduction: String = "",
    @SerializedName("memorialNo")
    val memorialNo: Int = -1,
    @SerializedName("userId")
    val userId: Int = -1
)

class DeleteIntroduceResponse : ProtocolResponse<Boolean>()
class DeleteIntroduceModel(
    @SerializedName("id")
    val ids: Int,
    @SerializedName("picUrl")
    val picUrl: String?,
    @SerializedName("name")
    val name: String = "",
    @SerializedName("introduction")
    val introduction: String = "",
    @SerializedName("memorialNo")
    val memorialNo: Int = -1,
    @SerializedName("userId")
    val userId: Int = -1
)

class ModifyIntroduceResponse : ProtocolResponse<IntroduceModel>()
class ModifyIntroduceModel(
    @SerializedName("id")
    val ids: Int,
    @SerializedName("picUrl")
    val picUrl: String?,
    @SerializedName("name")
    val name: String = "",
    @SerializedName("introduction")
    val introduction: String = "",
    @SerializedName("memorialNo")
    val memorialNo: Int = -1,
    @SerializedName("userId")
    val userId: Int = -1
)

class ArticleListResponse : ProtocolResponse<ArrayList<ArticleListModel>>()
class ArticleListModel(
    @SerializedName("id")
    val ids: Int,
    @SerializedName("picUrl")
    val picUrl: String?,
    @SerializedName("title")
    val title: String = "",
    @SerializedName("content")
    val content: String = "",
    @SerializedName("memorialNo")
    val memorialNo: Int?,
    @SerializedName("createTime")
    val createTime: String = "",
    @SerializedName("repeatUse")
    val repeatUse: Int = 0
)

class ArticleDetailResponse : ProtocolResponse<ArticleDetailModel>()
class ArticleDetailModel(
    @SerializedName("id")
    val ids: Int,
    @SerializedName("picUrl")
    val picUrl: String?,
    @SerializedName("title")
    val title: String = "",
    @SerializedName("content")
    val content: String = "",
    @SerializedName("memorialNo")
    val memorialNo: Int?,
    @SerializedName("createTime")
    val createTime: String = "",
    @SerializedName("repeatUse")
    val repeatUse: Int = 0
)

class CreateArticleResponse : ProtocolResponse<CreateArticleModel>()
class CreateArticleModel(
    @SerializedName("id")
    val ids: Int,
    @SerializedName("picUrl")
    val picUrl: String?,
    @SerializedName("title")
    val title: String = "",
    @SerializedName("content")
    val content: String = "",
    @SerializedName("memorialNo")
    val memorialNo: Int?,
    @SerializedName("createTime")
    val createTime: String = "",
    @SerializedName("repeatUse")
    val repeatUse: Int = 0
)

class ModifyArticleResponse : ProtocolResponse<ModifyArticleModel>()
class ModifyArticleModel(
    @SerializedName("id")
    val ids: Int,
    @SerializedName("picUrl")
    val picUrl: String?,
    @SerializedName("title")
    val title: String = "",
    @SerializedName("content")
    val content: String = "",
    @SerializedName("memorialNo")
    val memorialNo: Int?,
    @SerializedName("createTime")
    val createTime: String = "",
    @SerializedName("repeatUse")
    val repeatUse: Int = 0
)
class DeleteArticleResponse : ProtocolResponse<Boolean>()
class AlbumListResponse : ProtocolResponse<ArrayList<AlbumListModel>>()
class AlbumListModel(
    @SerializedName("id")
    val ids: Int,
    @SerializedName("picUrl")
    val picUrl: String?,
    @SerializedName("name")
    val name: String = "",
    @SerializedName("content")
    val content: String = "",
    @SerializedName("createTime")
    val createTime: String = "",
    @SerializedName("userId")
    val userId: Int = 0
)

class CreateAlbumResponse : ProtocolResponse<CreateAlbumModel>()
class CreateAlbumModel(
    @SerializedName("id")
    val ids: Int,
    @SerializedName("picUrl")
    val picUrl: String?,
    @SerializedName("title")
    val title: String = "",
    @SerializedName("content")
    val content: String = "",
    @SerializedName("memorialNo")
    val memorialNo: Int?,
    @SerializedName("createTime")
    val createTime: String = "",
    @SerializedName("repeatUse")
    val repeatUse: Int = 0
)
class DeleteAlbumResponse : ProtocolResponse<Boolean>()

class AllMaterialOfferListResponse : ProtocolResponse<ArrayList<AllMaterialOfferModel>>()
class AllMaterialOfferModel(
    @SerializedName("id")
    val ids: Int,
    @SerializedName("picUrl")
    val picUrl: String?,
    @SerializedName("name")
    val name: String = "",
    @SerializedName("materialTypeName")
    val materialTypeName: String = "",
    @SerializedName("memorialTypeNo")
    val memorialTypeNo: Int?,
    @SerializedName("qianChengB")
    val qianChengB: Int = 0,
    @SerializedName("children")
    val children: ArrayList<AllMaterialOfferItemModel> = ArrayList()
)

class AllMaterialOfferItemModel(
    @SerializedName("id")
    val ids: Int,
    @SerializedName("picUrl")
    val picUrl: String?,
    @SerializedName("name")
    val name: String = "",
    @SerializedName("materialTypeName")
    val materialTypeName: String = "",
    @SerializedName("memorialTypeNo")
    val memorialTypeNo: Int?,
    @SerializedName("qianChengB")
    val qianChengB: Int = 0,
    var isChoose: Boolean = false,
)

class StyleLongLightModel(
    @SerializedName("id")
    val ids: Int,
    @SerializedName("picUrl")
    val picUrl: String?,
    @SerializedName("name")
    val name: String = "",
    @SerializedName("repeatUse")
    val repeatUse: Int = 0,
    var isChoose: Boolean = false,
)

class AlbumPicListResponse : ProtocolResponse<ArrayList<AlbumPicListModel>>()
class AlbumPicListModel(
    @SerializedName("id")
    val ids: Int,
    @SerializedName("picUrl")
    val picUrl: String?,
    @SerializedName("albumId")
    val albumId: Int = -1,
    @SerializedName("picDesc")
    val picDesc: String = "",
    @SerializedName("createTime")
    val createTime: String = "",
    @SerializedName("userId")
    val userId: Int = 0
)

class UploadAlbumPicResponse : ProtocolResponse<UploadAlbumPicModel>()
class UploadAlbumPicModel(
    @SerializedName("id")
    val ids: Int,
    @SerializedName("picUrl")
    val picUrl: String?,
    @SerializedName("name")
    val name: String = "",
    @SerializedName("content")
    val content: String = "",
    @SerializedName("createTime")
    val createTime: String = "",
    @SerializedName("userId")
    val userId: Int = 0
)

class DeletePicResponse : ProtocolResponse<Boolean>()