package com.exae.memorialapp.api

import com.exae.memorialapp.bean.GetCodeResponse
import com.exae.memorialapp.bean.LoginResultResponse
import com.exae.memorialapp.requestData.VerificationCodeRequest
import retrofit2.http.*

interface VerificationCodeService {

    @POST("{path}")
    suspend fun getCodeRequest(
        @Path(value = "path", encoded = true) url: String,
        @Body request: VerificationCodeRequest
    ): GetCodeResponse

    @FormUrlEncoded
    @POST("{path}")
    suspend fun codeLoginRequest(
        @Path(value = "path", encoded = true) url: String,
        @Field("verifyCode") verify_code: String,
        @Field("phone") phone: String

    ): LoginResultResponse

    @GET("{path}")
    suspend fun request(
        @Path(
            value = "path",
            encoded = true
        ) url: String
    ): LoginResultResponse

}