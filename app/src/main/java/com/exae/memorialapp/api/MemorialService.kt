package com.exae.memorialapp.api

import com.exae.memorialapp.bean.AddCommentResponse
import com.exae.memorialapp.bean.AlbumListResponse
import com.exae.memorialapp.bean.ApplyHistoryListResponse
import com.exae.memorialapp.bean.ApplyMemorialResponse
import com.exae.memorialapp.bean.AttentionListResponse
import com.exae.memorialapp.bean.BannerResponse
import com.exae.memorialapp.bean.CommentListResponse
import com.exae.memorialapp.bean.CreateIntroduceResponse
import com.exae.memorialapp.bean.DeleteCommentResponse
import com.exae.memorialapp.bean.DeleteIntroduceResponse
import com.exae.memorialapp.bean.DeleteMemorialResponse
import com.exae.memorialapp.bean.DoubleMemorialResponse
import com.exae.memorialapp.bean.HandleApplyListResponse
import com.exae.memorialapp.bean.HandleApplyMemorialResponse
import com.exae.memorialapp.bean.IntroduceResponse
import com.exae.memorialapp.bean.ManageMemorialResponse
import com.exae.memorialapp.bean.ModifyIntroduceResponse
import com.exae.memorialapp.bean.MoreMemorialResponse
import com.exae.memorialapp.bean.SingleMemorialResponse
import com.exae.memorialapp.bean.StyleMemorialResponse
import com.exae.memorialapp.bean.UploadImageResponse
import com.exae.memorialapp.requestData.AddCommentRequest
import com.exae.memorialapp.requestData.ApplyMemorialRequest
import com.exae.memorialapp.requestData.CreateIntroduceRequest
import com.exae.memorialapp.requestData.DeleteCommentRequest
import com.exae.memorialapp.requestData.DeleteIntroduceRequest
import com.exae.memorialapp.requestData.DoubleMemorialRequest
import com.exae.memorialapp.requestData.ModifyIntroduceRequest
import com.exae.memorialapp.requestData.MoreMemorialRequest
import com.exae.memorialapp.requestData.SingleMemorialRequest
import com.exae.memorialapp.requestData.UploadImageRequest
import okhttp3.MultipartBody
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query


interface MemorialService {

    @GET("{path}")
    suspend fun bannerRequest(
        @Path(
            value = "path",
            encoded = true
        ) url: String
    ): BannerResponse

    @GET("{path}")
    suspend fun attentionRequest(
        @Path(
            value = "path",
            encoded = true
        ) url: String
    ): AttentionListResponse

    @GET("{path}")
    suspend fun manageMerioRequest(
        @Path(
            value = "path",
            encoded = true
        ) url: String
    ): ManageMemorialResponse

    @GET("{path}")
    suspend fun chooseHallRequestRequest(
        @Path(
            value = "path",
            encoded = true
        ) url: String
    ): StyleMemorialResponse

    @GET("{path}")
    suspend fun chooseTableRequestRequest(
        @Path(
            value = "path",
            encoded = true
        ) url: String
    ): StyleMemorialResponse

    @GET("{path}")
    suspend fun chooseMemorialRequestRequest(
        @Path(
            value = "path",
            encoded = true
        ) url: String
    ): StyleMemorialResponse

    //使用@Multipart注解方法，并用@Part注解方法参数，类型是List< okhttp3.MultipartBody.Part>
    //不使用@Multipart注解方法，直接使用@Body注解方法参数，类型是okhttp3.MultipartBody
    /**
     * 通过 List<MultipartBody.Part> 传入多个part实现多文件上传
     * @param parts 每个part代表一个
     * @return
     */
//    @Multipart
//    @POST("users/image")
//    open fun uploadFilesWithParts(@Part parts: List<Part?>?): Call<BaseResponse<String?>?>?

    /**
     * 通过 MultipartBody和@body作为参数来上传
     * @param multipartBody MultipartBody包含多个Part
     * @return*/
//    @POST("users/image")
//    fun uploadFileWithRequestBody(@Body body: MultipartBody): Call<BaseResponse<String>>

//    @Multipart
    @POST("{path}")
    suspend fun uploadImageRequest1(
        @Path(
            value = "path",
            encoded = true
        ) url: String,
        @Body request: UploadImageRequest,
        @Body file: MultipartBody?
    ): UploadImageResponse

    @POST("{path}")
    @Multipart
    suspend fun uploadImageRequest(
        @Path(
            value = "path",
            encoded = true
        ) url: String,
//        @Body request: UploadImageRequest,
        @Part("file") file: MultipartBody?
    ): UploadImageResponse

    @POST("{path}")
    suspend fun singleMemorialRequest(
        @Path(
            value = "path",
            encoded = true
        ) url: String,
        @Body request: SingleMemorialRequest,
    ): SingleMemorialResponse

    @GET("{path}")
    suspend fun getSingleMemorialDetailRequest(
        @Path(
            value = "path",
            encoded = true
        ) url: String,
    ): SingleMemorialResponse

    @PUT("{path}")
    suspend fun singleMemorialModifyRequest(
        @Path(
            value = "path",
            encoded = true
        ) url: String,
        @Body request: SingleMemorialRequest,
    ): SingleMemorialResponse

    @POST("{path}")
    suspend fun moreMemorialRequest(
        @Path(
            value = "path",
            encoded = true
        ) url: String,
        @Body request: MoreMemorialRequest,
    ): MoreMemorialResponse

    @GET("{path}")
    suspend fun getMoreDetailMemorialRequest(
        @Path(
            value = "path",
            encoded = true
        ) url: String,
    ): MoreMemorialResponse

    @PUT("{path}")
    suspend fun moreMemorialModifyRequest(
        @Path(
            value = "path",
            encoded = true
        ) url: String,
        @Body request: MoreMemorialRequest,
    ): MoreMemorialResponse

    @POST("{path}")
    suspend fun twoMemorialRequest(
        @Path(
            value = "path",
            encoded = true
        ) url: String,
        @Body request: DoubleMemorialRequest,
    ): DoubleMemorialResponse

    @GET("{path}")
    suspend fun getTwoMemorialDetailRequest(
        @Path(
            value = "path",
            encoded = true
        ) url: String,
    ): DoubleMemorialResponse

    @PUT("{path}")
    suspend fun twoMemorialModifyRequest(
        @Path(
            value = "path",
            encoded = true
        ) url: String,
        @Body request: DoubleMemorialRequest,
    ): DoubleMemorialResponse

    @DELETE("{path}")
    suspend fun deleteMemorialRequest(
        @Path(
            value = "path",
            encoded = true
        ) url: String,
    ): DeleteMemorialResponse

    @POST("{path}")
    suspend fun applyMemorialRequest(
        @Path(
            value = "path",
            encoded = true
        ) url: String,
        @Body request: ApplyMemorialRequest,
    ): ApplyMemorialResponse

    @PUT("{path}")
    suspend fun handleApplyMemorialRequest(
        @Path(
            value = "path",
            encoded = true
        ) url: String,
    ): HandleApplyMemorialResponse

    @GET("{path}")
    suspend fun applyHistoryMemorialRequest(
        @Path(
            value = "path",
            encoded = true
        ) url: String,
        @Query("status") status: Int,
    ): ApplyHistoryListResponse

    @GET("{path}")
    suspend fun handleApplyListMemorialRequest(
        @Path(
            value = "path",
            encoded = true
        ) url: String,
        @Query("status") status: Int,
    ): HandleApplyListResponse

    @GET("{path}")
    suspend fun getCommentListRequest(
        @Path(
            value = "path",
            encoded = true
        ) url: String,
        @Query("memorialNo") memorialNo: Int,
        @Query("pageNum") pageNum: Int,
        @Query("pageSize") pageSize: Int,
    ): CommentListResponse

    @POST("{path}")
    suspend fun addCommentRequest(
        @Path(
            value = "path",
            encoded = true
        ) url: String,
        @Body request: AddCommentRequest,
    ): AddCommentResponse

    @DELETE("{path}")
    suspend fun deleteCommentRequest(
        @Path(
            value = "path",
            encoded = true
        ) url: String,
        @Body request: DeleteCommentRequest,
    ): DeleteCommentResponse

    @POST("{path}")
    suspend fun deleteCommentRequest(
        @Path(
            value = "path",
            encoded = true
        ) url: String,
        @Body request: AddCommentRequest,
    ): DeleteCommentResponse

    @GET("{path}")
    suspend fun getMemorialIntroduceRequest(
        @Path(
            value = "path",
            encoded = true
        ) url: String,
    ): IntroduceResponse

    @POST("{path}")
    suspend fun createMemorialIntroduceRequest(
        @Path(
            value = "path",
            encoded = true
        ) url: String,
        @Body request: CreateIntroduceRequest,
    ): CreateIntroduceResponse

    @PUT("{path}")
    suspend fun modifyMemorialIntroduceRequest(
        @Path(
            value = "path",
            encoded = true
        ) url: String,
        @Body request: ModifyIntroduceRequest,
    ): ModifyIntroduceResponse

    @DELETE("{path}")
    suspend fun deleteMemorialIntroduceRequest(
        @Path(
            value = "path",
            encoded = true
        ) url: String,
        @Body request: DeleteIntroduceRequest,
    ): DeleteIntroduceResponse

    @GET("{path}")
    suspend fun getAlbumListRequest(
        @Path(
            value = "path",
            encoded = true
        ) url: String,
        @Query("memorialNo") memorialNo: Int,
        @Query("pageNum") pageNum: Int,
        @Query("pageSize") pageSize: Int,
    ): AlbumListResponse

}