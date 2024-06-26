package com.exae.memorialapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.exae.memorialapp.base.errorHandle
import com.exae.memorialapp.base.launch
import com.exae.memorialapp.bean.AddCommentResponse
import com.exae.memorialapp.bean.AlbumListResponse
import com.exae.memorialapp.bean.AlbumPicListResponse
import com.exae.memorialapp.bean.AllMaterialOfferListResponse
import com.exae.memorialapp.bean.ApplyHistoryListResponse
import com.exae.memorialapp.bean.ApplyMemorialResponse
import com.exae.memorialapp.bean.ArticleDetailResponse
import com.exae.memorialapp.bean.ArticleListResponse
import com.exae.memorialapp.bean.AttentionCancelResponse
import com.exae.memorialapp.bean.AttentionListResponse
import com.exae.memorialapp.bean.BannerResponse
import com.exae.memorialapp.bean.CommentListResponse
import com.exae.memorialapp.bean.CreateAlbumResponse
import com.exae.memorialapp.bean.CreateArticleResponse
import com.exae.memorialapp.bean.CreateIntroduceResponse
import com.exae.memorialapp.bean.DeleteAlbumResponse
import com.exae.memorialapp.bean.DeleteArticleResponse
import com.exae.memorialapp.bean.DeleteCommentResponse
import com.exae.memorialapp.bean.DeleteIntroduceResponse
import com.exae.memorialapp.bean.DeleteMemorialResponse
import com.exae.memorialapp.bean.DeletePicResponse
import com.exae.memorialapp.bean.DoubleMemorialResponse
import com.exae.memorialapp.bean.HandleApplyListResponse
import com.exae.memorialapp.bean.HandleApplyMemorialResponse
import com.exae.memorialapp.bean.IntroduceResponse
import com.exae.memorialapp.bean.ManageMemorialResponse
import com.exae.memorialapp.bean.ModifyArticleResponse
import com.exae.memorialapp.bean.ModifyIntroduceResponse
import com.exae.memorialapp.bean.MoreMemorialResponse
import com.exae.memorialapp.bean.ResultBean
import com.exae.memorialapp.bean.SingleMemorialResponse
import com.exae.memorialapp.bean.StyleMemorialResponse
import com.exae.memorialapp.bean.UploadAlbumPicResponse
import com.exae.memorialapp.bean.UploadImageResponse
import com.exae.memorialapp.repository.MemorialRepository
import com.exae.memorialapp.requestData.AddCommentRequest
import com.exae.memorialapp.requestData.AlbumLisRequest
import com.exae.memorialapp.requestData.AlbumPicLisRequest
import com.exae.memorialapp.requestData.AllMaterialOfferRequest
import com.exae.memorialapp.requestData.ApplyMemorialListAllRequest
import com.exae.memorialapp.requestData.ApplyMemorialRequest
import com.exae.memorialapp.requestData.ArticleDetailRequest
import com.exae.memorialapp.requestData.ArticleLisRequest
import com.exae.memorialapp.requestData.AttentionCancelRequest
import com.exae.memorialapp.requestData.AttentionListRequest
import com.exae.memorialapp.requestData.AttentionRequest
import com.exae.memorialapp.requestData.BannerRequest
import com.exae.memorialapp.requestData.ChooseHallRequest
import com.exae.memorialapp.requestData.ChooseMemorialRequest
import com.exae.memorialapp.requestData.ChooseTableRequest
import com.exae.memorialapp.requestData.CommentLisRequest
import com.exae.memorialapp.requestData.CreateAlbumRequest
import com.exae.memorialapp.requestData.CreateArticleRequest
import com.exae.memorialapp.requestData.CreateIntroduceRequest
import com.exae.memorialapp.requestData.DeleteAlbumRequest
import com.exae.memorialapp.requestData.DeleteArticleRequest
import com.exae.memorialapp.requestData.DeleteCommentRequest
import com.exae.memorialapp.requestData.DeleteIntroduceRequest
import com.exae.memorialapp.requestData.DeleteMemorialRequest
import com.exae.memorialapp.requestData.DeletePicRequest
import com.exae.memorialapp.requestData.DoubleMemorialRequest
import com.exae.memorialapp.requestData.HandleApplyMemorialListAllRequest
import com.exae.memorialapp.requestData.HandleApplyMemorialRequest
import com.exae.memorialapp.requestData.IntroduceRequest
import com.exae.memorialapp.requestData.MemorialListAllRequest
import com.exae.memorialapp.requestData.ModifyAlbumPicRequest
import com.exae.memorialapp.requestData.ModifyAlbumRequest
import com.exae.memorialapp.requestData.ModifyArticleRequest
import com.exae.memorialapp.requestData.ModifyIntroduceRequest
import com.exae.memorialapp.requestData.MoreDetailRequest
import com.exae.memorialapp.requestData.MoreMemorialRequest
import com.exae.memorialapp.requestData.SingleDetailRequest
import com.exae.memorialapp.requestData.SingleMemorialRequest
import com.exae.memorialapp.requestData.TwoDetailRequest
import com.exae.memorialapp.requestData.UploadAlbumPicRequest
import com.exae.memorialapp.requestData.UploadImageRequest
import dagger.hilt.android.lifecycle.HiltViewModel
import java.io.File
import javax.inject.Inject
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody

data class BannerState(var request: BannerRequest)

@HiltViewModel
class MemorialModel @Inject constructor(
    private val repository: MemorialRepository
) : ViewModel() {

    var bannerResponse = MutableLiveData<ResultBean<BannerResponse>>()
    fun bannerRequest() {
//        state.request = BannerRequest(code)
        launch(
            {
                bannerResponse.value = repository.getBannerList(BannerRequest(""))
            },
            {
                bannerResponse.value = errorHandle(it)
            }
        )
    }

    var attentionListResponse = MutableLiveData<ResultBean<AttentionListResponse>>()
    fun attentionListRequest() {
        launch(
            {
                attentionListResponse.value = repository.attentionList(AttentionListRequest(""))
            },
            {
                attentionListResponse.value = errorHandle(it)
            }
        )
    }

    var attentionResponse = MutableLiveData<ResultBean<AttentionCancelResponse>>()
    fun attentionRequest(memorialNo:Int) {
        launch(
            {
                attentionResponse.value = repository.attention(AttentionRequest(memorialNo))
            },
            {
                attentionResponse.value = errorHandle(it)
            }
        )
    }

    var attentionCancelResponse = MutableLiveData<ResultBean<AttentionCancelResponse>>()
    fun attentionCancelRequest(memorialNo:Int) {
        launch(
            {
                attentionCancelResponse.value = repository.attentionCancel(AttentionCancelRequest(memorialNo))
            },
            {
                attentionCancelResponse.value = errorHandle(it)
            }
        )
    }

    var manageMerioResponse = MutableLiveData<ResultBean<ManageMemorialResponse>>()
    fun manageMerioRequest() {
        launch(
            {
                manageMerioResponse.value =
                    repository.getManageMerioList(MemorialListAllRequest(""))
            },
            {
                manageMerioResponse.value = errorHandle(it)
            }
        )
    }

    var styleMerioResponse = MutableLiveData<ResultBean<StyleMemorialResponse>>()
    fun styleMerioRequest(type: Int) {
        launch(
            {
                styleMerioResponse.value = repository.getStyleMerioList(ChooseMemorialRequest(type))
            },
            {
                styleMerioResponse.value = errorHandle(it)
            }
        )
    }

    var styleHallResponse = MutableLiveData<ResultBean<StyleMemorialResponse>>()
    fun styleHallRequest(type: Int) {
        launch(
            {
                styleHallResponse.value = repository.getStyleHallList(ChooseHallRequest(type))
            },
            {
                styleHallResponse.value = errorHandle(it)
            }
        )
    }

    var styleTableResponse = MutableLiveData<ResultBean<StyleMemorialResponse>>()
    fun styleTableRequest(type: Int) {
        launch(
            {
                styleTableResponse.value = repository.getStyleTableList(ChooseTableRequest(type))
            },
            {
                styleTableResponse.value = errorHandle(it)
            }
        )
    }

    var uploadImageResponse = MutableLiveData<ResultBean<UploadImageResponse>>()
    fun uploadImageRequest(imgPath: String) {
        launch(
            {
                val file = File(imgPath)
                val requestBody = file.asRequestBody("multipart/form-data".toMediaTypeOrNull())
                val mulBody = MultipartBody.Builder()
                    .addFormDataPart("file", file.name, requestBody)
                    .build()
                uploadImageResponse.value =
                    repository.uploadImageRequest(UploadImageRequest("11", "00"), mulBody)
            },
            {
                uploadImageResponse.value = errorHandle(it)
            }
        )
    }

    var singleMemorialResponse = MutableLiveData<ResultBean<SingleMemorialResponse>>()
    fun singleMemorialRequest(request: SingleMemorialRequest) {
        launch(
            {
                singleMemorialResponse.value = repository.singleMemorialRequest(request)
            },
            {
                singleMemorialResponse.value = errorHandle(it)
            }
        )
    }

    var singleMemorialDetailResponse = MutableLiveData<ResultBean<SingleMemorialResponse>>()
    fun getSingleMemorialDetailRequest(ememorialNo: Int) {
        launch(
            {
                singleMemorialDetailResponse.value = repository.getSingleMemorialDetailRequest(
                    SingleDetailRequest(ememorialNo)
                )
            },
            {
                singleMemorialDetailResponse.value = errorHandle(it)
            }
        )
    }

    var singleMemorialModifyResponse = MutableLiveData<ResultBean<SingleMemorialResponse>>()
    fun singleMemorialModifyRequest(request: SingleMemorialRequest) {
        launch(
            {
                singleMemorialModifyResponse.value = repository.singleMemorialModifyRequest(request)
            },
            {
                singleMemorialModifyResponse.value = errorHandle(it)
            }
        )
    }

    var moreMemorialResponse = MutableLiveData<ResultBean<MoreMemorialResponse>>()
    fun moreMemorialRequest(request: MoreMemorialRequest) {
        launch(
            {
                moreMemorialResponse.value = repository.moreMemorialRequest(request)
            },
            {
                moreMemorialResponse.value = errorHandle(it)
            }
        )
    }

    var moreMemorialDetailResponse = MutableLiveData<ResultBean<MoreMemorialResponse>>()
    fun getMoreDetailMemorialRequest(request: Int) {
        launch(
            {
                moreMemorialDetailResponse.value =
                    repository.getMoreDetailMemorialRequest(MoreDetailRequest(request))
            },
            {
                moreMemorialDetailResponse.value = errorHandle(it)
            }
        )
    }

    var moreMemorialModifyResponse = MutableLiveData<ResultBean<MoreMemorialResponse>>()
    fun moreMemorialModifyRequest(request: MoreMemorialRequest) {
        launch(
            {
                moreMemorialModifyResponse.value = repository.moreMemorialModifyRequest(request)
            },
            {
                moreMemorialModifyResponse.value = errorHandle(it)
            }
        )
    }

    var twoMemorialResponse = MutableLiveData<ResultBean<DoubleMemorialResponse>>()
    fun twoMemorialRequest(request: DoubleMemorialRequest) {
        launch(
            {
                twoMemorialResponse.value = repository.twoMemorialRequest(request)
            },
            {
                twoMemorialResponse.value = errorHandle(it)
            }
        )
    }

    var twoMemorialModifyResponse = MutableLiveData<ResultBean<DoubleMemorialResponse>>()
    fun twoMemorialModifyRequest(request: DoubleMemorialRequest) {
        launch(
            {
                twoMemorialModifyResponse.value = repository.twoMemorialModifyRequest(request)
            },
            {
                twoMemorialModifyResponse.value = errorHandle(it)
            }
        )
    }

    var twoMemorialDetailResponse = MutableLiveData<ResultBean<DoubleMemorialResponse>>()
    fun getTwoMemorialDetailRequest(request: Int) {
        launch(
            {
                twoMemorialDetailResponse.value =
                    repository.getTwoMemorialDetailRequest(TwoDetailRequest(request))
            },
            {
                twoMemorialDetailResponse.value = errorHandle(it)
            }
        )
    }

    var deleteMemorialResponse = MutableLiveData<ResultBean<DeleteMemorialResponse>>()
    fun deleteMemorialRequest(request: Int) {
        launch(
            {
                deleteMemorialResponse.value = repository.deleteMemorialRequest(
                    DeleteMemorialRequest(request)
                )
            },
            {
                deleteMemorialResponse.value = errorHandle(it)
            }
        )
    }

    var applyMemorialResponse = MutableLiveData<ResultBean<ApplyMemorialResponse>>()
    fun applyMemorialRequest(invitationCode: String, note: String) {
        launch(
            {
                applyMemorialResponse.value = repository.applyMemorialRequest(
                    ApplyMemorialRequest(invitationCode, note)
                )
            },
            {
                applyMemorialResponse.value = errorHandle(it)
            }
        )
    }

    var handleApplyMemorialResponse = MutableLiveData<ResultBean<HandleApplyMemorialResponse>>()
    fun handleApplyMemorialRequest(id: Int, status: Int) {
        launch(
            {
                handleApplyMemorialResponse.value = repository.handleApplyMemorialRequest(
                    HandleApplyMemorialRequest(id, status)
                )
            },
            {
                handleApplyMemorialResponse.value = errorHandle(it)
            }
        )
    }

    var applyHistoryMemorialResponse = MutableLiveData<ResultBean<ApplyHistoryListResponse>>()
    fun applyHistoryMemorialRequest(statusType: Int) {
        launch(
            {
                applyHistoryMemorialResponse.value = repository.applyHistoryMemorialRequest(
                    ApplyMemorialListAllRequest(statusType)
                )
            },
            {
                applyHistoryMemorialResponse.value = errorHandle(it)
            }
        )
    }

    var handleApplyListMemorialResponse = MutableLiveData<ResultBean<HandleApplyListResponse>>()
    fun handleApplyListMemorialRequest(statusType: Int) {
        launch(
            {
                handleApplyListMemorialResponse.value = repository.handleApplyListMemorialRequest(
                    HandleApplyMemorialListAllRequest(statusType)
                )
            },
            {
                handleApplyListMemorialResponse.value = errorHandle(it)
            }
        )
    }

    var getCommentListResponse = MutableLiveData<ResultBean<CommentListResponse>>()
    fun getCommentListRequest(memorialNo: Int, pageNum: Int) {
        launch(
            {
                getCommentListResponse.value =
                    repository.getCommentListRequest(CommentLisRequest(memorialNo, pageNum, 20))
            },
            {
                getCommentListResponse.value = errorHandle(it)
            }
        )
    }

    var deleteCommenResponse = MutableLiveData<ResultBean<DeleteCommentResponse>>()
    fun deleteCommentRequest(memorialNo: Int, commentId: Int) {
        launch(
            {
                deleteCommenResponse.value =
                    repository.deleteCommentRequest(DeleteCommentRequest(commentId))
            },
            {
                deleteCommenResponse.value = errorHandle(it)
            }
        )
    }

    var addCommenResponse = MutableLiveData<ResultBean<AddCommentResponse>>()
    fun addCommentRequest(memorialNo: Int, content: String) {
        launch(
            {
                addCommenResponse.value =
                    repository.addCommentRequest(AddCommentRequest(memorialNo, content))
            },
            {
                addCommenResponse.value = errorHandle(it)
            }
        )
    }

    var getMemorialIntroduceResponse = MutableLiveData<ResultBean<IntroduceResponse>>()
    fun getMemorialIntroduceRequest(ememorialNo: Int) {
        launch(
            {
                getMemorialIntroduceResponse.value = repository.getMemorialIntroduceRequest(
                    IntroduceRequest(ememorialNo)
                )
            },
            {
                getMemorialIntroduceResponse.value = errorHandle(it)
            }
        )
    }

    var createMemorialIntroduceResponse = MutableLiveData<ResultBean<CreateIntroduceResponse>>()
    fun createMemorialIntroduceRequest(ememorialNo: Int, introduce: String) {
        launch(
            {
                createMemorialIntroduceResponse.value = repository.createMemorialIntroduceRequest(
                    CreateIntroduceRequest(ememorialNo, introduce)
                )
            },
            {
                createMemorialIntroduceResponse.value = errorHandle(it)
            }
        )
    }

    var modifyMemorialIntroduceResponse = MutableLiveData<ResultBean<ModifyIntroduceResponse>>()
    fun modifyMemorialIntroduceRequest(ememorialNo: Int, introId: Int, introduce: String) {
        launch(
            {
                modifyMemorialIntroduceResponse.value = repository.modifyMemorialIntroduceRequest(
                    ModifyIntroduceRequest(ememorialNo, introId, introduce)
                )
            },
            {
                modifyMemorialIntroduceResponse.value = errorHandle(it)
            }
        )
    }

    var deleteMemorialIntroduceResponse = MutableLiveData<ResultBean<DeleteIntroduceResponse>>()
    fun deleteMemorialIntroduceRequest(ids: Int) {
        launch(
            {
                deleteMemorialIntroduceResponse.value = repository.deleteMemorialIntroduceRequest(
                    DeleteIntroduceRequest(ids)
                )
            },
            {
                deleteMemorialIntroduceResponse.value = errorHandle(it)
            }
        )
    }

    var getArticleListResponse = MutableLiveData<ResultBean<ArticleListResponse>>()
    fun getArticleListRequest(memorialNo: Int, pageNum: Int) {
        launch(
            {
                getArticleListResponse.value =
                    repository.getArticleListRequest(ArticleLisRequest(memorialNo, pageNum, 20))
            },
            {
                getArticleListResponse.value = errorHandle(it)
            }
        )
    }

    var getArticleDetailResponse = MutableLiveData<ResultBean<ArticleDetailResponse>>()
    fun getArticleDetailRequest(articleId: Int) {
        launch(
            {
                getArticleDetailResponse.value =
                    repository.getArticleDetailRequest(ArticleDetailRequest(articleId))
            },
            {
                getArticleDetailResponse.value = errorHandle(it)
            }
        )
    }

    var createArticleResponse = MutableLiveData<ResultBean<CreateArticleResponse>>()
    fun createArticleRequest(ememorialNo: Int, content: String, title: String) {
        launch(
            {
                createArticleResponse.value = repository.createArticleRequest(
                    CreateArticleRequest(ememorialNo, content, title)
                )
            },
            {
                createArticleResponse.value = errorHandle(it)
            }
        )
    }

    var modifyArticleResponse = MutableLiveData<ResultBean<ModifyArticleResponse>>()
    fun modifyArticleRequest(ememorialNo: Int, articleId: Int, content: String, title: String) {
        launch(
            {
                modifyArticleResponse.value = repository.modifyArticleRequest(
                    ModifyArticleRequest(ememorialNo, articleId, content, title)
                )
            },
            {
                modifyArticleResponse.value = errorHandle(it)
            }
        )
    }

    var deleteArticleResponse = MutableLiveData<ResultBean<DeleteArticleResponse>>()
    fun deleteArticleRequest(articleId: Int) {
        launch(
            {
                deleteArticleResponse.value =
                    repository.deleteArticleRequest(DeleteArticleRequest(articleId))
            },
            {
                deleteArticleResponse.value = errorHandle(it)
            }
        )
    }

    var getAlbumtListResponse = MutableLiveData<ResultBean<AlbumListResponse>>()
    fun getAlbumListRequest(memorialNo: Int, pageNum: Int) {
        launch(
            {
                getAlbumtListResponse.value =
                    repository.getAlbumListRequest(AlbumLisRequest(memorialNo, pageNum, 20))
            },
            {
                getAlbumtListResponse.value = errorHandle(it)
            }
        )
    }

    var createAlbumResponse = MutableLiveData<ResultBean<CreateAlbumResponse>>()
    fun createAlbumRequest(memorialNo: Int, name: String) {
        launch(
            {
                createAlbumResponse.value = repository.createAlbumRequest(
                    CreateAlbumRequest(memorialNo, name)
                )
            },
            {
                createAlbumResponse.value = errorHandle(it)
            }
        )
    }

    var deleteAlbumResponse = MutableLiveData<ResultBean<DeleteAlbumResponse>>()
    fun deleteAlbumRequest(albumId: Int) {
        launch(
            {
                deleteAlbumResponse.value =
                    repository.deleteAlbumRequest(DeleteAlbumRequest(albumId))
            },
            {
                deleteAlbumResponse.value = errorHandle(it)
            }
        )
    }

    var modifyAlbumResponse = MutableLiveData<ResultBean<CreateAlbumResponse>>()
    fun modifyAlbumRequest(albumId: Int, name: String) {
        launch(
            {
                modifyAlbumResponse.value =
                    repository.modifyAlbumRequest(ModifyAlbumRequest(albumId, name))
            },
            {
                modifyAlbumResponse.value = errorHandle(it)
            }
        )
    }

    var allMaterialOfferResponse = MutableLiveData<ResultBean<AllMaterialOfferListResponse>>()
    fun getAllMaterialOfferRequest(type: String) {
        launch(
            {
                allMaterialOfferResponse.value =
                    repository.getAllMaterialOfferRequest(AllMaterialOfferRequest(type))
            },
            {
                allMaterialOfferResponse.value = errorHandle(it)
            }
        )
    }

    var getAlbumPicListResponse = MutableLiveData<ResultBean<AlbumPicListResponse>>()
    fun getAlbumPicListRequest(albumId: Int, pageNum: Int) {
        launch(
            {
                getAlbumPicListResponse.value =
                    repository.getAlbumPicListRequest(AlbumPicLisRequest(albumId, pageNum, 20))
            },
            {
                getAlbumPicListResponse.value = errorHandle(it)
            }
        )
    }

    var uploadAlbumPicResponse = MutableLiveData<ResultBean<UploadAlbumPicResponse>>()
    fun uploadAlbumPicRequest(albumId: Int, picDesc: String, picUrl: String) {
        launch(
            {
                uploadAlbumPicResponse.value =
                    repository.uploadAlbumPicRequest(
                        UploadAlbumPicRequest(
                            albumId,
                            picDesc,
                            picUrl
                        )
                    )
            },
            {
                uploadAlbumPicResponse.value = errorHandle(it)
            }
        )
    }

    var modifyPicResponse = MutableLiveData<ResultBean<UploadAlbumPicResponse>>()
    fun modifyPicRequest(albumId: Int, id: Int, picDesc: String, picUrl: String) {
        launch(
            {
                modifyPicResponse.value = repository.modifyAlbumPicRequest(
                    ModifyAlbumPicRequest(albumId, id, picDesc, picUrl)
                )
            },
            {
                modifyPicResponse.value = errorHandle(it)
            }
        )
    }

    var deletePicResponse = MutableLiveData<ResultBean<DeletePicResponse>>()
    fun deletePicRequest(id: Int) {
        launch(
            {
                deletePicResponse.value =
                    repository.deletePicRequest(DeletePicRequest(id))
            },
            {
                deletePicResponse.value = errorHandle(it)
            }
        )
    }


}