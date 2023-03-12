package com.exae.memorialapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.exae.memorialapp.base.errorHandle
import com.exae.memorialapp.base.launch
import com.exae.memorialapp.bean.BannerResponse
import com.exae.memorialapp.bean.ManageMemorialResponse
import com.exae.memorialapp.bean.ResultBean
import com.exae.memorialapp.bean.SingleMemorialResponse
import com.exae.memorialapp.bean.StyleMemorialResponse
import com.exae.memorialapp.bean.UploadImageModel
import com.exae.memorialapp.bean.UploadImageResponse
import com.exae.memorialapp.repository.MemorialRepository
import com.exae.memorialapp.requestData.BannerRequest
import com.exae.memorialapp.requestData.ChooseHallRequest
import com.exae.memorialapp.requestData.ChooseMemorialRequest
import com.exae.memorialapp.requestData.ChooseTableRequest
import com.exae.memorialapp.requestData.MemorialListAllRequest
import com.exae.memorialapp.requestData.SingleMemorialRequest
import com.exae.memorialapp.requestData.UploadImageRequest
import com.orhanobut.logger.Logger
import dagger.hilt.android.lifecycle.HiltViewModel
import java.io.File
import javax.inject.Inject
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody

data class BannerState(var request : BannerRequest)

//var state = BannerState("")
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

    var manageMerioResponse = MutableLiveData<ResultBean<ManageMemorialResponse>>()
    fun manageMerioRequest() {
        launch(
            {
                manageMerioResponse.value = repository.getManageMerioList(MemorialListAllRequest(""))
            },
            {
                manageMerioResponse.value = errorHandle(it)
            }
        )
    }

    var styleMerioResponse = MutableLiveData<ResultBean<StyleMemorialResponse>>()
    fun styleMerioRequest(type:Int) {
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
    fun styleHallRequest(type:Int) {
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
    fun styleTableRequest(type:Int) {
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
}