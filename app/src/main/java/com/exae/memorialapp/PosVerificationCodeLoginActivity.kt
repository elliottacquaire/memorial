package com.exae.memorialapp

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.exae.memorialapp.utils.CommonUtils
import com.exae.memorialapp.utils.ToastUtil
import com.exae.memorialapp.widget.CodeInputView
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.exae.memorialapp.base.PosBaseActivity
import com.exae.memorialapp.base.handleResponse
import com.exae.memorialapp.common.ShareUtil
import com.exae.memorialapp.databinding.ActivityPosVerificationCodeLoginBinding
import com.exae.memorialapp.viewmodel.PosLoginModel
import dagger.hilt.android.AndroidEntryPoint

@Route(path = "/login/login")
@AndroidEntryPoint
class PosVerificationCodeLoginActivity :
    PosBaseActivity<ActivityPosVerificationCodeLoginBinding>(),
    CodeInputView.CodeGetListener {

    private val longViewModel: PosLoginModel by viewModels()
//    private lateinit var binding: ActivityPosVerificationCodeLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        binding = ActivityPosVerificationCodeLoginBinding.inflate(layoutInflater)
//        setContentView(binding.root)
        binding.codeInput.setCodeGetListener(this)
//        tv_tips.porscheNextBold()
        longViewModel.getCodeResponse.observe(this, Observer { resources ->
            handleResponse(resources) {
                ToastUtil.showCenter("验证码发送成功")
                binding.codeInput.starTime()
            }
        })

        longViewModel.loginCodeResponse.observe(this, Observer { resources ->
            handleResponse(resources, {
//                val roles = it.data?.roles
//                if (!roles.isNullOrEmpty()) {
//                    userRole = Gson().toJson(roles)
//                }
//                longViewModel.setUserRole(userRole)
                dismissLoading()
                ToastUtil.showCenter("登录成功")
                longViewModel.handleLoginResult(it.data)
                ShareUtil.putToken("token-sssss")
                ARouter.getInstance()
                    .build("/app/main")
                    .navigation()

                finish()
//                judgeRole(userRole)
//                userPermissionJump()
            },
                {
                    dismissLoading()
                }
            )
        })

//        longViewModel.registerResponse.observe(this, Observer {
//            handleResponse(it) {
//            }
//        })

        binding.tvLogin.setOnClickListener {
            val editContent = binding.phoneInput.getEditText()
            val codeContent = binding.codeInput.getEditText()
            if (editContent.isBlank() || codeContent.isBlank()) {
                ToastUtil.showCenter("手机或验证码不能为空")
                return@setOnClickListener
            }
            if (editContent.length != 11) {
                ToastUtil.showCenter(getString(R.string.code_phone_tips))
                return@setOnClickListener
            }
            showLoading()
            longViewModel.codeLoginRequest(editContent, codeContent)

        }

    binding.phoneInput.setEditText(longViewModel.getUser().trim())

    }

    override fun getCodeRequest() {
        val editContent = binding.phoneInput.getEditText()
        if (editContent.isBlank()) return ToastUtil.showCenter("手机号不能为空")
        if (CommonUtils.isMobileNO(editContent)) {
            longViewModel.getCodeRequest(editContent)
        } else {
            ToastUtil.showCenter(getString(R.string.code_phone_tips))
        }

    }

    //go login ppn
    private fun forwardLogin() {
        ARouter.getInstance()
            .build("/pos/main")
//            .withString("ROLE", role)
            .navigation()
    }

    private fun forwardList(role: String) {
        ARouter.getInstance()
            .build("/pos/main")
//            .withString("ROLE", role)
            .withTransition(0, 0)
            .navigation()
        finish()
    }

    //go main
    private fun judgeRole(role: String) {
//        when (role) {
//            "ROLE_SALES_CONSULTANT" -> longViewModel.requestDriveRequest()
//            else -> forwardList(role) ////add 顾问，经理同时存在权限审批
//        }
//        when (longViewModel.getRolePermission()) {
//            2 -> longViewModel.requestDriveRequest()
//            else -> forwardList() ////add 顾问，经理同时存在权限审批
//        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1001) {
            if (resultCode == Activity.RESULT_OK) {
                var role = ""
//                if (data != null) {
//                    role = data.getStringExtra("ROLE") ?: ""
//                }
//                judgeRole(role)
//                userPermissionJump()
            } else {
//                finish()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.codeInput.endTime()
    }

    override fun onBackPressed() {
        super.onBackPressed()
//        EventBus.getDefault().post(BackEvent())
    }

    override fun getViewBinding(): ActivityPosVerificationCodeLoginBinding {
        return ActivityPosVerificationCodeLoginBinding.inflate(layoutInflater)
    }
}
