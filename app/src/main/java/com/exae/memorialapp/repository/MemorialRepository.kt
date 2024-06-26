package com.exae.memorialapp.repository

import com.exae.memorialapp.animation.RetrofitAnno
import com.exae.memorialapp.api.MemorialService
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
import javax.inject.Inject
import javax.inject.Singleton
import okhttp3.MultipartBody
import retrofit2.Retrofit

@Singleton
class MemorialRepository @Inject constructor(@RetrofitAnno var retrofit: Retrofit) {

    suspend fun getBannerList(request: BannerRequest): ResultBean<BannerResponse> {
        return ResultBean.success(
            retrofit.create(MemorialService::class.java)
                .bannerRequest(request.path)
        )
    }

    suspend fun attentionList(request: AttentionListRequest): ResultBean<AttentionListResponse> {
        return ResultBean.success(
            retrofit.create(MemorialService::class.java)
                .attentionRequest(request.path)
        )
    }

    suspend fun attention(request: AttentionRequest): ResultBean<AttentionCancelResponse> {
        return ResultBean.success(
            retrofit.create(MemorialService::class.java)
                .attention(request.path)
        )
    }

    suspend fun attentionCancel(request: AttentionCancelRequest): ResultBean<AttentionCancelResponse> {
        return ResultBean.success(
            retrofit.create(MemorialService::class.java)
                .attentionCancel(request.path)
        )
    }

    suspend fun getManageMerioList(request: MemorialListAllRequest): ResultBean<ManageMemorialResponse> {
        return ResultBean.success(
            retrofit.create(MemorialService::class.java)
                .manageMerioRequest(request.path)
        )
    }

    suspend fun getStyleMerioList(request: ChooseMemorialRequest): ResultBean<StyleMemorialResponse> {
        return ResultBean.success(
            retrofit.create(MemorialService::class.java)
                .chooseMemorialRequestRequest(request.path)
        )
    }

    suspend fun getStyleHallList(request: ChooseHallRequest): ResultBean<StyleMemorialResponse> {
        return ResultBean.success(
            retrofit.create(MemorialService::class.java)
                .chooseHallRequestRequest(request.path)
        )
    }

    suspend fun getStyleTableList(request: ChooseTableRequest): ResultBean<StyleMemorialResponse> {
        return ResultBean.success(
            retrofit.create(MemorialService::class.java)
                .chooseTableRequestRequest(request.path)
        )
    }

    suspend fun uploadImageRequest(
        request: UploadImageRequest,
        file: MultipartBody?
    ): ResultBean<UploadImageResponse> {
        return ResultBean.success(
            retrofit.create(MemorialService::class.java)
                .uploadImageRequest(request.path, file)
        )
    }

    suspend fun singleMemorialRequest(request: SingleMemorialRequest): ResultBean<SingleMemorialResponse> {
        return ResultBean.success(
            retrofit.create(MemorialService::class.java)
                .singleMemorialRequest(request.path, request)
        )
    }

    suspend fun getSingleMemorialDetailRequest(request: SingleDetailRequest): ResultBean<SingleMemorialResponse> {
        return ResultBean.success(
            retrofit.create(MemorialService::class.java)
                .getSingleMemorialDetailRequest(request.path)
        )
    }

    suspend fun singleMemorialModifyRequest(request: SingleMemorialRequest): ResultBean<SingleMemorialResponse> {
        return ResultBean.success(
            retrofit.create(MemorialService::class.java)
                .singleMemorialModifyRequest(request.path, request)
        )
    }

    suspend fun moreMemorialRequest(request: MoreMemorialRequest): ResultBean<MoreMemorialResponse> {
        return ResultBean.success(
            retrofit.create(MemorialService::class.java)
                .moreMemorialRequest(request.path, request)
        )
    }

    suspend fun getMoreDetailMemorialRequest(request: MoreDetailRequest): ResultBean<MoreMemorialResponse> {
        return ResultBean.success(
            retrofit.create(MemorialService::class.java)
                .getMoreDetailMemorialRequest(request.path)
        )
    }

    suspend fun moreMemorialModifyRequest(request: MoreMemorialRequest): ResultBean<MoreMemorialResponse> {
        return ResultBean.success(
            retrofit.create(MemorialService::class.java)
                .moreMemorialModifyRequest(request.path, request)
        )
    }

    suspend fun twoMemorialRequest(request: DoubleMemorialRequest): ResultBean<DoubleMemorialResponse> {
        return ResultBean.success(
            retrofit.create(MemorialService::class.java)
                .twoMemorialRequest(request.path, request)
        )
    }

    suspend fun getTwoMemorialDetailRequest(request: TwoDetailRequest): ResultBean<DoubleMemorialResponse> {
        return ResultBean.success(
            retrofit.create(MemorialService::class.java)
                .getTwoMemorialDetailRequest(request.path)
        )
    }

    suspend fun twoMemorialModifyRequest(request: DoubleMemorialRequest): ResultBean<DoubleMemorialResponse> {
        return ResultBean.success(
            retrofit.create(MemorialService::class.java)
                .twoMemorialModifyRequest(request.path, request)
        )
    }

    suspend fun deleteMemorialRequest(request: DeleteMemorialRequest): ResultBean<DeleteMemorialResponse> {
        return ResultBean.success(
            retrofit.create(MemorialService::class.java)
                .deleteMemorialRequest(request.path)
        )
    }

    suspend fun applyMemorialRequest(request: ApplyMemorialRequest): ResultBean<ApplyMemorialResponse> {
        return ResultBean.success(
            retrofit.create(MemorialService::class.java)
                .applyMemorialRequest(request.path, request)
        )
    }

    suspend fun handleApplyMemorialRequest(request: HandleApplyMemorialRequest): ResultBean<HandleApplyMemorialResponse> {
        return ResultBean.success(
            retrofit.create(MemorialService::class.java)
                .handleApplyMemorialRequest(request.path)
        )
    }

    suspend fun applyHistoryMemorialRequest(request: ApplyMemorialListAllRequest): ResultBean<ApplyHistoryListResponse> {
        return ResultBean.success(
            retrofit.create(MemorialService::class.java)
                .applyHistoryMemorialRequest(request.path, request.statusType)
        )
    }

    suspend fun handleApplyListMemorialRequest(request: HandleApplyMemorialListAllRequest): ResultBean<HandleApplyListResponse> {
        return ResultBean.success(
            retrofit.create(MemorialService::class.java)
                .handleApplyListMemorialRequest(request.path, request.status)
        )
    }

    suspend fun getCommentListRequest(request: CommentLisRequest): ResultBean<CommentListResponse> {
        return ResultBean.success(
            retrofit.create(MemorialService::class.java)
                .getCommentListRequest(
                    request.path,
                    request.memorialNo,
                    request.pageNum,
                    request.pageSize
                )
        )
    }

    suspend fun addCommentRequest(request: AddCommentRequest): ResultBean<AddCommentResponse> {
        return ResultBean.success(
            retrofit.create(MemorialService::class.java)
                .addCommentRequest(
                    request.path,
                    request,
                )
        )
    }

    suspend fun deleteCommentRequest(request: DeleteCommentRequest): ResultBean<DeleteCommentResponse> {
        return ResultBean.success(
            retrofit.create(MemorialService::class.java)
                .deleteCommentRequest(
                    request.path,
//                    request,
                )
        )
    }

    suspend fun getMemorialIntroduceRequest(request: IntroduceRequest): ResultBean<IntroduceResponse> {
        return ResultBean.success(
            retrofit.create(MemorialService::class.java)
                .getMemorialIntroduceRequest(request.path)
        )
    }

    suspend fun createMemorialIntroduceRequest(request: CreateIntroduceRequest): ResultBean<CreateIntroduceResponse> {
        return ResultBean.success(
            retrofit.create(MemorialService::class.java)
                .createMemorialIntroduceRequest(request.path, request)
        )
    }

    suspend fun deleteMemorialIntroduceRequest(request: DeleteIntroduceRequest): ResultBean<DeleteIntroduceResponse> {
        return ResultBean.success(
            retrofit.create(MemorialService::class.java)
                .deleteMemorialIntroduceRequest(request.path)
        )
    }

    suspend fun modifyMemorialIntroduceRequest(request: ModifyIntroduceRequest): ResultBean<ModifyIntroduceResponse> {
        return ResultBean.success(
            retrofit.create(MemorialService::class.java)
                .modifyMemorialIntroduceRequest(request.path, request)
        )
    }

    suspend fun getArticleListRequest(request: ArticleLisRequest): ResultBean<ArticleListResponse> {
        return ResultBean.success(
            retrofit.create(MemorialService::class.java)
                .getArticleListRequest(
                    request.path,
                    request.memorialNo,
                    request.pageNum,
                    request.pageSize
                )
        )
    }

    suspend fun getArticleDetailRequest(request: ArticleDetailRequest): ResultBean<ArticleDetailResponse> {
        return ResultBean.success(
            retrofit.create(MemorialService::class.java)
                .getArticleDetailRequest(request.path)
        )
    }

    suspend fun createArticleRequest(request: CreateArticleRequest): ResultBean<CreateArticleResponse> {
        return ResultBean.success(
            retrofit.create(MemorialService::class.java)
                .createArticleRequest(request.path, request)
        )
    }

    suspend fun modifyArticleRequest(request: ModifyArticleRequest): ResultBean<ModifyArticleResponse> {
        return ResultBean.success(
            retrofit.create(MemorialService::class.java)
                .modifyArticleRequest(request.path, request)
        )
    }

    suspend fun deleteArticleRequest(request: DeleteArticleRequest): ResultBean<DeleteArticleResponse> {
        return ResultBean.success(
            retrofit.create(MemorialService::class.java)
                .deleteArticleRequest(
                    request.path,
                )
        )
    }

    suspend fun getAlbumListRequest(request: AlbumLisRequest): ResultBean<AlbumListResponse> {
        return ResultBean.success(
            retrofit.create(MemorialService::class.java)
                .getAlbumListRequest(
                    request.path,
                    request.memorialNo,
                    request.pageNum,
                    request.pageSize
                )
        )
    }

    suspend fun createAlbumRequest(request: CreateAlbumRequest): ResultBean<CreateAlbumResponse> {
        return ResultBean.success(
            retrofit.create(MemorialService::class.java)
                .createAlbumRequest(request.path, request)
        )
    }

    suspend fun deleteAlbumRequest(request: DeleteAlbumRequest): ResultBean<DeleteAlbumResponse> {
        return ResultBean.success(
            retrofit.create(MemorialService::class.java)
                .deleteAlbumRequest(
                    request.path,
                )
        )
    }

    suspend fun modifyAlbumRequest(request: ModifyAlbumRequest): ResultBean<CreateAlbumResponse> {
        return ResultBean.success(
            retrofit.create(MemorialService::class.java)
                .modifyAlbumRequest(
                    request.path,
                    request,
                )
        )
    }

    suspend fun getAllMaterialOfferRequest(request: AllMaterialOfferRequest): ResultBean<AllMaterialOfferListResponse> {
        return ResultBean.success(
            retrofit.create(MemorialService::class.java)
                .getAllMaterialOfferRequest(request.path)
        )
    }

    suspend fun getAlbumPicListRequest(request: AlbumPicLisRequest): ResultBean<AlbumPicListResponse> {
        return ResultBean.success(
            retrofit.create(MemorialService::class.java)
                .getAlbumPicListRequest(
                    request.path,
                    request.albumId,
                    request.pageNum,
                    request.pageSize
                )
        )
    }

    suspend fun uploadAlbumPicRequest(request: UploadAlbumPicRequest): ResultBean<UploadAlbumPicResponse> {
        return ResultBean.success(
            retrofit.create(MemorialService::class.java)
                .uploadAlbumPicRequest(
                    request.path,
                    request,
                )
        )
    }

    suspend fun modifyAlbumPicRequest(request: ModifyAlbumPicRequest): ResultBean<UploadAlbumPicResponse> {
        return ResultBean.success(
            retrofit.create(MemorialService::class.java)
                .ModifyAlbumPicRequest(request.path, request)
        )
    }

    suspend fun deletePicRequest(request: DeletePicRequest): ResultBean<DeletePicResponse> {
        return ResultBean.success(
            retrofit.create(MemorialService::class.java)
                .deletePicRequest(
                    request.path,
                )
        )
    }


}