package com.exae.memorialapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.alibaba.android.arouter.launcher.ARouter
import com.exae.memorialapp.databinding.ActivityLaunchBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint //1
class LaunchActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLaunchBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLaunchBinding.inflate(layoutInflater)
        setContentView(binding.root)
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
            }, 1000)
        }
    }
}