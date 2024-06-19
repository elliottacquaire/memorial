package com.exae.memorialapp.hall

import android.os.Bundle
import android.view.KeyEvent
import android.view.MotionEvent
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.exae.memorialapp.base.PosBaseActivity
import com.exae.memorialapp.databinding.ActivityCreateHallResultBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
@Route(path = "/create/result")
class CreateHallResultActivity : PosBaseActivity<ActivityCreateHallResultBinding>() {
    private var memorialType = ""
    private var memorialNo = -1
    private var memorialName = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setBackState(false)
        memorialNo = intent.getIntExtra("memorialNo", -1)
        memorialName = intent.getStringExtra("memorialName") ?: ""
        memorialType = intent.getStringExtra("memorialType") ?: ""

        binding.apply {
            goToHome.setOnClickListener {
                ARouter.getInstance().build("/app/home")
                    .withInt("memorialNo", memorialNo)
                    .withString("memorialName", memorialName)
                    .withString("memorialType", memorialType)
                    .navigation()
                finish()
            }
            goToDetail.setOnClickListener {
                when (memorialType) {
                    "0" -> {
                        ARouter.getInstance().build("/app/single/detail")
                            .withInt("memorialNo", memorialNo)
                            .navigation()
                        finish()
                    }

                    "1" -> {
                        ARouter.getInstance().build("/app/more/detail")
                            .withInt("memorialNo", memorialNo)
                            .navigation()
                        finish()
                    }

                    "2" -> {
                        ARouter.getInstance().build("/app/two/detail")
                            .withInt("memorialNo", memorialNo)
                            .navigation()
                        finish()
                    }

                    else -> ""
                }
            }
        }
    }

    override fun getViewBinding(): ActivityCreateHallResultBinding {
        return ActivityCreateHallResultBinding.inflate(layoutInflater)
    }

    override fun dispatchKeyEvent(event: KeyEvent): Boolean {
        if (event.keyCode == KeyEvent.KEYCODE_BACK) {
            return true
        }
        return super.dispatchKeyEvent(event)
    }
}