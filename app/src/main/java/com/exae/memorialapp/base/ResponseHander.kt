package com.exae.memorialapp.base

import android.content.Intent
import com.alibaba.android.arouter.launcher.ARouter
import com.exae.memorialapp.bean.ProtocolResponse
import com.exae.memorialapp.bean.ResultBean
import com.exae.memorialapp.utils.ToastUtil

fun <T : ProtocolResponse<*>> handleResponse(resource: ResultBean<T>, successHandler: (T) -> Unit) {
    if (resource.isSuccess()) {
        if (resource.data?.code == "200") {
            successHandler.invoke(resource.data)
        } else {
            if (resource.data?.code == "401") {
                jumpToLogin()
            } else {
                ToastUtil.showCenter(resource.data?.message)
            }
        }
    } else {
        if (resource.code == "401") {
            jumpToLogin()
        } else {
            ToastUtil.showCenter(resource.message)
        }
    }
}

fun <T : ProtocolResponse<*>> handleResponse(
    resource: ResultBean<T>,
    successHandler: (T) -> Unit,
    failedHandler: (code: String?) -> Unit
) {
    if (resource.isSuccess()) {
        if (resource.data?.code == "200") {
            successHandler.invoke(resource.data)
        } else {
            if (resource.data?.code == "401") {
                jumpToLogin()
            } else {
                ToastUtil.showCenter(resource.data?.message)
            }
            failedHandler.invoke(resource.data?.code.toString())
        }
    } else {
        failedHandler.invoke(resource.code)
        if (resource.code == "401") {
            jumpToLogin()
        } else {
            ToastUtil.showCenter(resource.message)
        }
    }
}

private fun jumpToLogin() {
    ARouter.getInstance().build("/login/login")
        .withFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
        .navigation()
}
