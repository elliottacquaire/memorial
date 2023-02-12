package com.exae.memorialapp.base.interceptor

import android.content.Context
import android.text.TextUtils
import com.exae.memorialapp.common.ShareUtil
import com.exae.memorialapp.utils.StringPreferenceType
import okhttp3.Interceptor
import okhttp3.Interceptor.Chain
import okhttp3.Request
import okhttp3.Response
import okhttp3.internal.closeQuietly

class ApiRequestInterceptor(
  private val context: Context,
  private val tokenStringPreference: StringPreferenceType,
) : Interceptor {

  override fun intercept(chain: Chain): Response {
    val response = authentication(chain)
//    updateToken(response)
    if (response.code in listOf(401, 403)) {
      response.closeQuietly()
      synchronized(this) {
        //尝试处理并发
//        if (loginPreference.get()) {
//          startLogin()
//        }
        return authentication(chain)
      }
    }
    return response
  }



  private fun updateToken(response: Response) {
    if (!TextUtils.isEmpty(response.header("x-access-token", ""))) {
//      ShareUtil.getToken().set(response.header("x-access-token", ""))
    }
  }

  private fun authentication(chain: Chain): Response {
    return chain.proceed(request(chain.request()))
  }

  private fun request(request: Request): Request {
    val builder = request.newBuilder()
//    builder.header("Accept-Language", I18nUtils.language())
    builder.addHeader("channel", "01")
//    builder.addHeader("Authorization", "${ShareUtil.getToken()}")
    builder.addHeader("Authorization", tokenStringPreference.get())
    builder.addHeader("platform", "android")
//    builder.addHeader("clientId", build.applicationId)
//    builder.addHeader("version", build.versionCode.toString())
    return builder.build()
  }

  private fun startLogin() {
//    loginPreference.set(false)
//    ARouter.getInstance().build(CoreRes.LOGIN)
//      .withFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
//      .navigation()
  }

}