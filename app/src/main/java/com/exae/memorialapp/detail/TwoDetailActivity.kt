package com.exae.memorialapp.detail

import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.exae.memorialapp.base.PosBaseActivity
import com.exae.memorialapp.databinding.ActivityTwoDetailBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
@Route(path = "/app/two/detail")
class TwoDetailActivity : PosBaseActivity<ActivityTwoDetailBinding>() {
    private var memorialNo = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setToolTitle("大厅风格选择")
        setBackState(true)
        memorialNo = intent.getIntExtra("memorialNo", -1)
    }

    override fun getViewBinding(): ActivityTwoDetailBinding {
        return ActivityTwoDetailBinding.inflate(layoutInflater)
    }
}