package com.exae.memorialapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.exae.memorialapp.base.errorHandle
import com.exae.memorialapp.base.launch
import com.exae.memorialapp.bean.ApplyHistoryListResponse
import com.exae.memorialapp.bean.ApplyMemorialResponse
import com.exae.memorialapp.bean.AttentionListResponse
import com.exae.memorialapp.bean.BannerResponse
import com.exae.memorialapp.bean.DeleteMemorialResponse
import com.exae.memorialapp.bean.DoubleMemorialResponse
import com.exae.memorialapp.bean.HandleApplyListResponse
import com.exae.memorialapp.bean.HandleApplyMemorialResponse
import com.exae.memorialapp.bean.ManageMemorialResponse
import com.exae.memorialapp.bean.MoreMemorialResponse
import com.exae.memorialapp.bean.ResultBean
import com.exae.memorialapp.bean.SingleMemorialResponse
import com.exae.memorialapp.bean.StyleMemorialResponse
import com.exae.memorialapp.bean.UploadImageResponse
import com.exae.memorialapp.repository.MemorialRepository
import com.exae.memorialapp.requestData.ApplyMemorialListAllRequest
import com.exae.memorialapp.requestData.ApplyMemorialRequest
import com.exae.memorialapp.requestData.AttentionListRequest
import com.exae.memorialapp.requestData.BannerRequest
import com.exae.memorialapp.requestData.ChooseHallRequest
import com.exae.memorialapp.requestData.ChooseMemorialRequest
import com.exae.memorialapp.requestData.ChooseTableRequest
import com.exae.memorialapp.requestData.DeleteMemorialRequest
import com.exae.memorialapp.requestData.DoubleMemorialRequest
import com.exae.memorialapp.requestData.HandleApplyMemorialListAllRequest
import com.exae.memorialapp.requestData.HandleApplyMemorialRequest
import com.exae.memorialapp.requestData.MemorialListAllRequest
import com.exae.memorialapp.requestData.MoreDetailRequest
import com.exae.memorialapp.requestData.MoreMemorialRequest
import com.exae.memorialapp.requestData.SingleDetailRequest
import com.exae.memorialapp.requestData.SingleMemorialRequest
import com.exae.memorialapp.requestData.TwoDetailRequest
import com.exae.memorialapp.requestData.UploadImageRequest
import dagger.hilt.android.lifecycle.HiltViewModel
import java.io.File
import javax.inject.Inject
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody

data class BannerState(var request: BannerRequest)

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

    var attentionListResponse = MutableLiveData<ResultBean<AttentionListResponse>>()
    fun attentionListRequest() {
        launch(
            {
                attentionListResponse.value = repository.attentionList(AttentionListRequest(""))
            },
            {
                attentionListResponse.value = errorHandle(it)
            }
        )
    }

    var manageMerioResponse = MutableLiveData<ResultBean<ManageMemorialResponse>>()
    fun manageMerioRequest() {
        launch(
            {
                manageMerioResponse.value =
                    repository.getManageMerioList(MemorialListAllRequest(""))
            },
            {
                manageMerioResponse.value = errorHandle(it)
            }
        )
    }

    var styleMerioResponse = MutableLiveData<ResultBean<StyleMemorialResponse>>()
    fun styleMerioRequest(type: Int) {
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
    fun styleHallRequest(type: Int) {
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
    fun styleTableRequest(type: Int) {
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

    var singleMemorialDetailResponse = MutableLiveData<ResultBean<SingleMemorialResponse>>()
    fun getSingleMemorialDetailRequest(ememorialNo: Int) {
        launch(
            {
                singleMemorialDetailResponse.value = repository.getSingleMemorialDetailRequest(
                    SingleDetailRequest(ememorialNo)
                )
            },
            {
                singleMemorialDetailResponse.value = errorHandle(it)
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

    var moreMemorialResponse = MutableLiveData<ResultBean<MoreMemorialResponse>>()
    fun moreMemorialRequest(request: MoreMemorialRequest) {
        launch(
            {
                moreMemorialResponse.value = repository.moreMemorialRequest(request)
            },
            {
                moreMemorialResponse.value = errorHandle(it)
            }
        )
    }

    var moreMemorialDetailResponse = MutableLiveData<ResultBean<MoreMemorialResponse>>()
    fun getMoreDetailMemorialRequest(request: Int) {
        launch(
            {
                moreMemorialDetailResponse.value =
                    repository.getMoreDetailMemorialRequest(MoreDetailRequest(request))
            },
            {
                moreMemorialDetailResponse.value = errorHandle(it)
            }
        )
    }

    var moreMemorialModifyResponse = MutableLiveData<ResultBean<MoreMemorialResponse>>()
    fun moreMemorialModifyRequest(request: MoreMemorialRequest) {
        launch(
            {
                moreMemorialModifyResponse.value = repository.moreMemorialModifyRequest(request)
            },
            {
                moreMemorialModifyResponse.value = errorHandle(it)
            }
        )
    }

    var twoMemorialResponse = MutableLiveData<ResultBean<DoubleMemorialResponse>>()
    fun twoMemorialRequest(request: DoubleMemorialRequest) {
        launch(
            {
                twoMemorialResponse.value = repository.twoMemorialRequest(request)
            },
            {
                twoMemorialResponse.value = errorHandle(it)
            }
        )
    }

    var twoMemorialModifyResponse = MutableLiveData<ResultBean<DoubleMemorialResponse>>()
    fun twoMemorialModifyRequest(request: DoubleMemorialRequest) {
        launch(
            {
                twoMemorialModifyResponse.value = repository.twoMemorialModifyRequest(request)
            },
            {
                twoMemorialModifyResponse.value = errorHandle(it)
            }
        )
    }

    var twoMemorialDetailResponse = MutableLiveData<ResultBean<DoubleMemorialResponse>>()
    fun getTwoMemorialDetailRequest(request: Int) {
        launch(
            {
                twoMemorialDetailResponse.value =
                    repository.getTwoMemorialDetailRequest(TwoDetailRequest(request))
            },
            {
                twoMemorialDetailResponse.value = errorHandle(it)
            }
        )
    }

    var deleteMemorialResponse = MutableLiveData<ResultBean<DeleteMemorialResponse>>()
    fun deleteMemorialRequest(request: Int) {
        launch(
            {
                deleteMemorialResponse.value = repository.deleteMemorialRequest(
                    DeleteMemorialRequest(request)
                )
            },
            {
                deleteMemorialResponse.value = errorHandle(it)
            }
        )
    }

    var applyMemorialResponse = MutableLiveData<ResultBean<ApplyMemorialResponse>>()
    fun applyMemorialRequest(invitationCode: String, note: String) {
        launch(
            {
                applyMemorialResponse.value = repository.applyMemorialRequest(
                    ApplyMemorialRequest(invitationCode, note)
                )
            },
            {
                applyMemorialResponse.value = errorHandle(it)
            }
        )
    }

    var handleApplyMemorialResponse = MutableLiveData<ResultBean<HandleApplyMemorialResponse>>()
    fun handleApplyMemorialRequest(id: Int, status: Int) {
        launch(
            {
                handleApplyMemorialResponse.value = repository.handleApplyMemorialRequest(
                    HandleApplyMemorialRequest(id, status)
                )
            },
            {
                handleApplyMemorialResponse.value = errorHandle(it)
            }
        )
    }

    var applyHistoryMemorialResponse = MutableLiveData<ResultBean<ApplyHistoryListResponse>>()
    fun applyHistoryMemorialRequest(statusType: Int) {
        launch(
            {
                applyHistoryMemorialResponse.value = repository.applyHistoryMemorialRequest(
                    ApplyMemorialListAllRequest(statusType)
                )
            },
            {
                applyHistoryMemorialResponse.value = errorHandle(it)
            }
        )
    }

    var handleApplyListMemorialResponse = MutableLiveData<ResultBean<HandleApplyListResponse>>()
    fun handleApplyListMemorialRequest(statusType: Int) {
        launch(
            {
                handleApplyListMemorialResponse.value = repository.handleApplyListMemorialRequest(
                    HandleApplyMemorialListAllRequest(statusType)
                )
            },
            {
                handleApplyListMemorialResponse.value = errorHandle(it)
            }
        )
    }

}