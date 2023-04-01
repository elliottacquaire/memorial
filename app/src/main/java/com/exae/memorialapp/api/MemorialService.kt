package com.exae.memorialapp.api

import com.exae.memorialapp.bean.AttentionListResponse
import com.exae.memorialapp.bean.BannerResponse
import com.exae.memorialapp.bean.DoubleMemorialResponse
import com.exae.memorialapp.bean.ManageMemorialResponse
import com.exae.memorialapp.bean.MoreMemorialResponse
import com.exae.memorialapp.bean.SingleMemorialResponse
import com.exae.memorialapp.bean.StyleMemorialResponse
import com.exae.memorialapp.bean.UploadImageResponse
import com.exae.memorialapp.requestData.DoubleMemorialRequest
import com.exae.memorialapp.requestData.MoreMemorialRequest
import com.exae.memorialapp.requestData.SingleMemorialRequest
import com.exae.memorialapp.requestData.UploadImageRequest
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path


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
}