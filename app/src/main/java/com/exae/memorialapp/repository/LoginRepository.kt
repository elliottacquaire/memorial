package com.exae.memorialapp.repository

import android.util.Log
import com.exae.memorialapp.animation.RetrofitAnno
import com.exae.memorialapp.api.VerificationCodeService
import com.exae.memorialapp.bean.GetCodeResponse
import com.exae.memorialapp.bean.LoginResultResponse
import com.exae.memorialapp.bean.ResultBean
import com.exae.memorialapp.requestData.LoginRequest
import com.exae.memorialapp.requestData.VerificationCodeLoginRequest
import com.exae.memorialapp.requestData.VerificationCodeRequest
import dagger.hilt.android.scopes.ActivityScoped
import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LoginRepository @Inject constructor(@RetrofitAnno var retrofit: Retrofit) {
    suspend fun login(request: LoginRequest): ResultBean<LoginResultResponse> {
        return ResultBean.success(
            retrofit.create(VerificationCodeService::class.java).request(request.path)
        )
    }

    suspend fun getVerificationCode(request: VerificationCodeRequest): ResultBean<GetCodeResponse> {
        return ResultBean.success(
            retrofit.create(VerificationCodeService::class.java)
                .getCodeRequest(request.path, request)
        )
    }

    suspend fun verificationCodeLogin(request: VerificationCodeLoginRequest): ResultBean<LoginResultResponse> {
        Log.i("sss","-------${request.phone}---------")
        return ResultBean.success(
            retrofit.create(VerificationCodeService::class.java)
                .codeLoginRequest(request.path, request)
        )
    }

}