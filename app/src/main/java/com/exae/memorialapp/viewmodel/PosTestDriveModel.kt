package com.exae.memorialapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.switchMap
import com.exae.memorialapp.base.errorHandle
import com.exae.memorialapp.base.launch
import com.exae.memorialapp.bean.ProtocolResponse
import com.exae.memorialapp.bean.ResultBean
import com.exae.memorialapp.bean.TransferListModel
import com.exae.memorialapp.repository.TestRepository
import com.exae.memorialapp.requestData.FindOrder
import com.exae.memorialapp.bean.LoginResultModel
import dagger.hilt.android.scopes.ViewModelScoped

class TestDriveListState {
//    var request = TestDriveListRequest()
//    var requestHistory = TestDriveHistoryListRequest()
}


class PosTestDriveModel @ViewModelScoped constructor(
    private val repository: TestRepository
) : ViewModel() {

    var state = TestDriveListState()

    //result
    var listResponse = MutableLiveData<ResultBean<ProtocolResponse<LoginResultModel>>>()
//    var listResponse = MutableLiveData<Resource<TestDriveEntryListResponse>>()

    var searchOrderRequestRes =
        MutableLiveData<ResultBean<ProtocolResponse<List<TransferListModel>>>>()

    fun searchOrderRequest(request: FindOrder) {
        launch(
            {
                searchOrderRequestRes.value =
                    repository.findOrderToUse(request)

            },
            {
                searchOrderRequestRes.value = errorHandle(it)
            }
        )
    }


    private val itemId = MutableLiveData<String>()
    val resule = itemId.switchMap {
        liveData {
            emit(it)
        }
    }


//    fun requestTestDriveHistoryList() {
//        launch(
//            {
//                listResponse.value =
//                    testDriveListRepository.getTestDriveHistoryListResponse(state.requestHistory)
//            },
//            {
//                listResponse.value = errorHandle(it)
//            }
//        )
//    }

}