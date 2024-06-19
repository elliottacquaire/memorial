package com.exae.memorialapp

import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.exae.memorialapp.animation.TokenPreference
import com.exae.memorialapp.base.PosBaseActivity
import com.exae.memorialapp.databinding.ActivitySettingBinding
import com.exae.memorialapp.utils.StringPreferenceType
import com.lxj.xpopup.XPopup
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
@Route(path = "/pos/setting")
class SettingActivity : PosBaseActivity<ActivitySettingBinding>() {

    @Inject
    @TokenPreference
    lateinit var tokenPref: StringPreferenceType

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
//        setContentView(R.layout.activity_setting)

        setToolTitle("设置")
        setBackState(true)
        setSettingImage(false)
        initView()
    }

    private fun initView() {
        binding.apply {
            versionNo.text = "Version" + getAppVersionName(this@SettingActivity)
            loginOut.setOnClickListener {
                XPopup.Builder(this@SettingActivity)
                    .hasStatusBarShadow(false)
                    .hasNavigationBar(false)
                    .isDestroyOnDismiss(true)
                    .isDarkTheme(true)
                    .asConfirm("温馨提示", "确定要退出吗？") {
                        tokenPref.delete()
                        ARouter.getInstance().build("/login/login").navigation()
                        finish()
                    }.show()
            }
            aboutRelative.setOnClickListener {
                ARouter.getInstance().build("/about/us").navigation()
            }
        }
    }

    override fun getViewBinding(): ActivitySettingBinding {
        return ActivitySettingBinding.inflate(layoutInflater)
    }

    fun getAppVersionName(context: Context): String? {
        return try {
            val packageInfo = context.packageManager.getPackageInfo(context.packageName, 0)
            packageInfo.versionName
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
            null
        }
    }
}