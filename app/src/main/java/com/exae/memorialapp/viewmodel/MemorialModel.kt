package com.exae.memorialapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.exae.memorialapp.base.errorHandle
import com.exae.memorialapp.base.launch
import com.exae.memorialapp.bean.BannerResponse
import com.exae.memorialapp.bean.ManageMemorialResponse
import com.exae.memorialapp.bean.ResultBean
import com.exae.memorialapp.repository.MemorialRepository
import com.exae.memorialapp.requestData.BannerRequest
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

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
//        state.request = BannerRequest(code)
        launch(
            {
                manageMerioResponse.value = repository.getManageMerioList(BannerRequest(""))
            },
            {
                manageMerioResponse.value = errorHandle(it)
            }
        )
    }
}