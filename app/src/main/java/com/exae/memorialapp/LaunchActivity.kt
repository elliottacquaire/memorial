package com.exae.memorialapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.Window
import android.view.WindowManager
import com.alibaba.android.arouter.launcher.ARouter
import com.exae.memorialapp.animation.TokenPreference
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
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        binding = ActivityLaunchBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Handler(Looper.getMainLooper()).postDelayed({
            if (tokenPref.get()?.isNotEmpty() == true) {
                ARouter.getInstance().build("/app/main").navigation()
            } else {
                ARouter.getInstance().build("/login/login").navigation()
            }
            finish()
        }, 2000)

//        binding.button.setOnClickListener {
//            Handler(Looper.getMainLooper()).postDelayed({
//                startActivity(Intent(this,MainActivity::class.java))
//                startActivity(Intent(this,PosVerificationCodeLoginActivity::class.java))
//                ARouter.getInstance()
//                    .build("/login/login")
//                    .build("/app/main")
////            .withString("ROLE", role)
//                    .withTransition(0, 0)
//                    .navigation()
//                finish()
//            }, 2000)
//        }
    }
}