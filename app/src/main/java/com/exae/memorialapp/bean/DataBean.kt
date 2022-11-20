package com.exae.memorialapp.bean

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName


class LoginResultResponse : ProtocolResponse<LoginResultModel>()
class GetCodeResponse : ProtocolResponse<SmsCodeResourceModel>()

@Keep
class SmsCodeResourceModel {
    @SerializedName("code")
    var code: String = ""
}