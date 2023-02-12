package com.exae.memorialapp.hall

import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.exae.memorialapp.R
import com.exae.memorialapp.base.PosBaseActivity
import com.exae.memorialapp.databinding.ActivityChooseHallStyleBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
@Route(path = "/app/choose/hall")
class ChooseHallStyleActivity : PosBaseActivity<ActivityChooseHallStyleBinding>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choose_hall_style)
    }

    override fun getViewBinding(): ActivityChooseHallStyleBinding {
        return ActivityChooseHallStyleBinding.inflate(layoutInflater)
    }
}