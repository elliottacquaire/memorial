package com.exae.memorialapp.util

import java.io.File
import java.net.URISyntaxException
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.http.Multipart

// 上传背景图片的方法
fun uploadBgImg(userid: String, imgPath: String) {
    var file: File? = null
    try {
        file = File(imgPath)
    } catch (e: URISyntaxException) {
        e.printStackTrace()
    }

    val mOkHttpClent = OkHttpClient()
    val requestBody = MultipartBody.Builder()
        .setType(MultipartBody.FORM)
        .addFormDataPart("userid", userid)  // 上传参数
        .addFormDataPart(
            "bgImg", file?.name,
            RequestBody.create("multipart/form-data".toMediaTypeOrNull(), file!!)
        )   // 上传文件
        .build()

    val type = "image/*".toMediaTypeOrNull()

    val request = Request.Builder()
//        .url(ConstantConfig.JACKSON_BASE_URL + "phoneUser/uploadBgImg")
        .post(requestBody)
        .build()
    val call = mOkHttpClent.newCall(request)
//    call.enqueue(object : Callback {
//        override fun onFailure(call: Call, e: IOException) {
//            LogUtils.e("yyy" + e.message)
//            listener.uploadFailed(e.message.toString())
//        }
//
//        override fun onResponse(call: Call, response: Response) {
//            if (response.isSuccessful) {
//                listener.uploadSuccess()
//            } else {
//                listener.uploadSuccess()
//                LogUtils.e("tttttt" + response.code() + response.message())
//            }
//        }
//    })

//    val requestBody1 = RequestBody.create("multipart/form-data".toMediaTypeOrNull())
    val requestBody1 = file.asRequestBody("multipart/form-data".toMediaTypeOrNull())
    val part = file?.name?.let { MultipartBody.Part.createFormData("file", it) }

}
