package com.exae.memorialapp.repository

import com.exae.memorialapp.animation.RetrofitAnno
import com.exae.memorialapp.api.MemorialService
import com.exae.memorialapp.api.VerificationCodeService
import com.exae.memorialapp.bean.BannerResponse
import com.exae.memorialapp.bean.LoginResultResponse
import com.exae.memorialapp.bean.ManageMemorialResponse
import com.exae.memorialapp.bean.ResultBean
import com.exae.memorialapp.requestData.BannerRequest
import com.exae.memorialapp.requestData.BaseRequest
import com.exae.memorialapp.requestData.VerificationCodeLoginRequest
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject
import javax.inject.Singleton
import retrofit2.Retrofit

@Singleton
class MemorialRepository @Inject constructor(@RetrofitAnno var retrofit: Retrofit) {

    suspend fun getBannerList(request: BannerRequest): ResultBean<BannerResponse> {
        return ResultBean.success(
            retrofit.create(MemorialService::class.java)
                .bannerRequest(request.path)
        )
    }

    suspend fun getManageMerioList(request: BannerRequest): ResultBean<ManageMemorialResponse> {
        return ResultBean.success(
            retrofit.create(MemorialService::class.java)
                .manageMerioRequest(request.path)
        )
    }
}