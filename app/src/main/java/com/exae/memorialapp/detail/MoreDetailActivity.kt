package com.exae.memorialapp.detail

import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.exae.memorialapp.base.PosBaseActivity
import com.exae.memorialapp.databinding.ActivityMoreDetailBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
@Route(path = "/app/more/detail")
class MoreDetailActivity : PosBaseActivity<ActivityMoreDetailBinding>() {
    var memorialNo = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setToolTitle("大厅风格选择")
        setBackState(true)
        memorialNo = intent.getIntExtra("memorialNo", -1)
    }

    override fun getViewBinding(): ActivityMoreDetailBinding {
        return ActivityMoreDetailBinding.inflate(layoutInflater)
    }
}