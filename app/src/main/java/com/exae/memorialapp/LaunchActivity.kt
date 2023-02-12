package com.exae.memorialapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.alibaba.android.arouter.launcher.ARouter
import com.exae.memorialapp.animation.TokenPreference
import com.exae.memorialapp.common.ShareUtil
import com.exae.memorialapp.databinding.ActivityLaunchBinding
import com.exae.memorialapp.utils.StringPreferenceType
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint //1
class LaunchActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLaunchBinding

    @Inject
    @TokenPreference
    lateinit var tokenPref: StringPreferenceType

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLaunchBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if (tokenPref.get()?.isNotEmpty() == true) {
            ARouter.getInstance().build("/app/main").navigation()
        } else {
            ARouter.getInstance().build("/login/login").navigation()
        }
        binding.button.setOnClickListener {
            Handler(Looper.getMainLooper()).postDelayed({
//                startActivity(Intent(this,MainActivity::class.java))
                startActivity(Intent(this,PosVerificationCodeLoginActivity::class.java))
//                ARouter.getInstance()
//                    .build("/login/login")
//                    .build("/app/main")
////            .withString("ROLE", role)
//                    .withTransition(0, 0)
//                    .navigation()
                finish()
            }, 2000)
        }
    }
}