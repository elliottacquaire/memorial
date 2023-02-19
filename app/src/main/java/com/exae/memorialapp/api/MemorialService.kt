package com.exae.memorialapp.api

import com.exae.memorialapp.bean.BannerResponse
import com.exae.memorialapp.bean.ManageMemorialResponse
import com.exae.memorialapp.bean.StyleMemorialResponse
import com.exae.memorialapp.requestData.UploadImageRequest
import okhttp3.MultipartBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
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
    @Multipart
    @POST("users/image")
    open fun uploadFilesWithParts(@Part parts: List<Part?>?): Call<BaseResponse<String?>?>?

    /**
     * 通过 MultipartBody和@body作为参数来上传
     * @param multipartBody MultipartBody包含多个Part
     * @return*/
    @POST("users/image")
    fun uploadFileWithRequestBody(@Body body: MultipartBody): Call<BaseResponse<String>>

    @Multipart
    @POST("{path}")
    suspend fun uploadImageRequest(
        @Path(
            value = "path",
            encoded = true
        ) url: String,
        @Body request: UploadImageRequest,
        @Part file: MultipartBody.Part
    ): StyleMemorialResponse
}