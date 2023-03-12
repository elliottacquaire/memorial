package com.exae.memorialapp.repository

import com.exae.memorialapp.animation.RetrofitAnno
import com.exae.memorialapp.api.MemorialService
import com.exae.memorialapp.api.VerificationCodeService
import com.exae.memorialapp.bean.BannerResponse
import com.exae.memorialapp.bean.LoginResultResponse
import com.exae.memorialapp.bean.ManageMemorialResponse
import com.exae.memorialapp.bean.ResultBean
import com.exae.memorialapp.bean.SingleMemorialResponse
import com.exae.memorialapp.bean.StyleMemorialResponse
import com.exae.memorialapp.bean.UploadImageResponse
import com.exae.memorialapp.requestData.BannerRequest
import com.exae.memorialapp.requestData.BaseRequest
import com.exae.memorialapp.requestData.ChooseHallRequest
import com.exae.memorialapp.requestData.ChooseMemorialRequest
import com.exae.memorialapp.requestData.ChooseTableRequest
import com.exae.memorialapp.requestData.MemorialListAllRequest
import com.exae.memorialapp.requestData.SingleMemorialRequest
import com.exae.memorialapp.requestData.UploadImageRequest
import com.exae.memorialapp.requestData.VerificationCodeLoginRequest
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject
import javax.inject.Singleton
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Retrofit

@Singleton
class MemorialRepository @Inject constructor(@RetrofitAnno var retrofit: Retrofit) {

    suspend fun getBannerList(request: BannerRequest): ResultBean<BannerResponse> {
        return ResultBean.success(
            retrofit.create(MemorialService::class.java)
                .bannerRequest(request.path)
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
    suspend fun uploadImageRequest(request: UploadImageRequest,file: MultipartBody?): ResultBean<UploadImageResponse> {
        return ResultBean.success(
            retrofit.create(MemorialService::class.java)
                .uploadImageRequest(request.path,file)
        )
    }
    suspend fun singleMemorialRequest(request: SingleMemorialRequest): ResultBean<SingleMemorialResponse> {
        return ResultBean.success(
            retrofit.create(MemorialService::class.java)
                .singleMemorialRequest(request.path,request)
        )
    }
    suspend fun singleMemorialModifyRequest(request: SingleMemorialRequest): ResultBean<SingleMemorialResponse> {
        return ResultBean.success(
            retrofit.create(MemorialService::class.java)
                .singleMemorialModifyRequest(request.path,request)
        )
    }
}