package com.exae.memorialapp.repository

import com.exae.memorialapp.animation.ApiServiceAnno
import com.exae.memorialapp.animation.RetrofitAnno
import com.exae.memorialapp.api.NetwrokService
import com.exae.memorialapp.bean.ProtocolResponse
import com.exae.memorialapp.bean.ResultBean
import com.exae.memorialapp.bean.TransferListModel
import com.exae.memorialapp.requestData.FindCarToUse
import com.exae.memorialapp.requestData.FindOrder
import com.exae.memorialapp.requestData.MyOredrCarToUse
import com.exae.memorialapp.requestData.UserRequest
import com.exae.memorialapp.requestData.UserRequest3
import com.exae.memorialapp.requestData.UserRequest4
import com.exae.memorialapp.bean.LoginResultModel
import dagger.hilt.android.scopes.ActivityScoped
import retrofit2.Retrofit
import javax.inject.Inject

@ActivityScoped
class TestRepository @Inject constructor(
    @RetrofitAnno var retrofit: Retrofit,
    @ApiServiceAnno var apiService: NetwrokService
) {

//    suspend fun verificationCodeLogin(request: VerificationCodeLoginRequest) : ResultBean<ProtocolResponse<LoginResultModel>> {
//        return ResultBean.success(retrofit.create(NetwrokService::class.java).codeLoginRequest(request.path, request.verify_code,request.phone))
//    }


    suspend fun getUserRequest(request: UserRequest): ResultBean<ProtocolResponse<LoginResultModel>> {
        return ResultBean.success(apiService.codeLoginRequest(request.path))
    }

    suspend fun getUserRequest3(request: UserRequest3): ResultBean<ProtocolResponse<LoginResultModel>> {
        return ResultBean.success(apiService.codeLoginRequest(request.path))
    }

    suspend fun getUserRequest4(request: UserRequest4): ResultBean<ProtocolResponse<LoginResultModel>> {
        return ResultBean.success(apiService.codeLoginRequest(request.path))
    }

    suspend fun findCarToUse(request: FindCarToUse): ResultBean<ProtocolResponse<List<TransferListModel>>> {
        return ResultBean.success(apiService.findCarToUse(request.path))
    }

    suspend fun findOrderToUse(request: FindOrder): ResultBean<ProtocolResponse<List<TransferListModel>>> {
        return ResultBean.success(apiService.findOrderToUse(request.path))
    }

    suspend fun findOrToUse(request: MyOredrCarToUse): ResultBean<ProtocolResponse<List<TransferListModel>>> {
        return ResultBean.success(apiService.findOrdddderToUse(request.path))
    }
}