package com.exae.memorialapp

import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.exae.memorialapp.base.PosBaseActivity
import com.exae.memorialapp.databinding.ActivityAboutUsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
@Route(path = "/about/us")
class AboutUsActivity : PosBaseActivity<ActivityAboutUsBinding>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setToolTitle("关于我们")
        setBackState(true)
        setSettingImage(false)
        initView()
    }

    private fun initView() {

    }

    override fun getViewBinding(): ActivityAboutUsBinding {
        return ActivityAboutUsBinding.inflate(layoutInflater)
    }
}