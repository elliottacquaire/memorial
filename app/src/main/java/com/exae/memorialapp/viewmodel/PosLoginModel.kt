package com.exae.memorialapp.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.exae.memorialapp.animation.TokenPreference
import com.exae.memorialapp.animation.UserPreference
import com.exae.memorialapp.base.errorHandle
import com.exae.memorialapp.base.launch
import com.exae.memorialapp.bean.GetCodeResponse
import com.exae.memorialapp.bean.Resource
import com.exae.memorialapp.requestData.VerificationCodeLoginRequest
import com.exae.memorialapp.utils.StringPreferenceType
import com.exae.memorialapp.bean.LoginResultModel
import com.exae.memorialapp.bean.LoginResultResponse
import com.exae.memorialapp.bean.ProtocolResponse
import com.exae.memorialapp.bean.ResultBean
import com.exae.memorialapp.bean.SmsCodeResourceModel
import com.exae.memorialapp.repository.LoginRepository
import com.exae.memorialapp.requestData.LoginRequest
import com.exae.memorialapp.requestData.VerificationCodeRequest
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

data class PosLoginState(
    var code: LoginRequest? = null,
    var verificationCodeRequest: VerificationCodeRequest = VerificationCodeRequest(),
    var verificationCodeLoginRequest: VerificationCodeLoginRequest = VerificationCodeLoginRequest()
)

@HiltViewModel
class PosLoginModel @Inject constructor(
    private val loginRepository: LoginRepository
//    @TokenPreference val tokenPref: StringPreferenceType,
//    @UserPreference val users: StringPreferenceType
) : ViewModel() {

    var state = PosLoginState()

    //    var registerResponse = MutableLiveData<Resource<DeviceRegisterResponse>>()
    fun handleLoginResult(loginResultModel: LoginResultModel?) {
        loginResultModel?.let {
//            tokenPref.set(it.accessToken)
//            Log.i("DealerApp", devicePref.get())
//            state.registerDeviceRequest = DeviceRegsiterRequest(
//                deviceId = devicePref.get(),
//                userId = it.userDetailResource?.uid ?: ""
//            )
//            launch(
//                {
//                    state.registerDeviceRequest?.let { it ->
//                        registerResponse.value = loginRepository.registerDevice(it)
//                    }
//                },
//                {
//                    registerResponse.value = errorHandle(it)
//                }
//            )
        }
    }

    var requestLoginResponse = MutableLiveData<ResultBean<LoginResultResponse>>()
    fun requestLogin(code: String) {
        state.code = LoginRequest(code)
        launch(
            {
                requestLoginResponse.value = loginRepository.login(state.code!!)
            },
            {
                requestLoginResponse.value = errorHandle(it)
            }
        )
    }

    var getCodeResponse = MutableLiveData<ResultBean<GetCodeResponse>>()
    fun getCodeRequest(phone: String) {
//        state.verificationCodeRequest.phone = phone
        launch(
            {
                getCodeResponse.value =
                    loginRepository.getVerificationCode(state.verificationCodeRequest)
            },
            {
                getCodeResponse.value = errorHandle(it)
            }
        )
    }

    var loginCodeResponse = MutableLiveData<ResultBean<LoginResultResponse>>()
    fun codeLoginRequest(phone: String, code: String) {

//        setUserPhone(phone)

        Log.i("sss","--------$phone---------")

        state.verificationCodeLoginRequest.also {
            it.phone = phone
            it.verify_code = code
        }
        launch(
            {
                loginCodeResponse.value =
                    loginRepository.verificationCodeLogin(state.verificationCodeLoginRequest)
            },
            {
                loginCodeResponse.value = errorHandle(it)
            }
        )
    }

//    var driveResponse = MutableLiveData<Resource<DriveStateResponse>>()
//    fun requestDriveRequest() {
//        launch(
//            {
//                driveResponse.value =
//                    posLaunchRepository.requestDriveState(state.driveRequest)
//            },
//            {
//                driveResponse.value = errorHandle(it)
//            }
//        )
//    }

//    fun setUserPhone(phone: String) {
//        val userBean = getUser()
//        userBean.phoneNum = phone
//        user.set(Gson().toJson(userBean))
//    }
}