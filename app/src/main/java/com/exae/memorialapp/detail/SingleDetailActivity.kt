package com.exae.memorialapp.detail

import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.exae.memorialapp.base.PosBaseActivity
import com.exae.memorialapp.databinding.ActivitySingleDetailBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
@Route(path = "/app/single/detail")
class SingleDetailActivity : PosBaseActivity<ActivitySingleDetailBinding>() {

//    @JvmField
//    @Autowired(name = "memorialNo")
//    var memorialNo = "ss"

    var memorialNo = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        ARouter.getInstance().inject(this)
        setToolTitle("大厅风格选择")
        setBackState(true)
        memorialNo = intent.getStringExtra("memorialNo") ?: ""
    }

    override fun getViewBinding(): ActivitySingleDetailBinding {
        return ActivitySingleDetailBinding.inflate(layoutInflater)
    }
}