package com.exae.memorialapp.hall

import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.exae.memorialapp.R
import com.exae.memorialapp.base.PosBaseActivity
import com.exae.memorialapp.databinding.ActivityChooseTableStyleBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
@Route(path = "/app/choose/table")
class ChooseTableStyleActivity : PosBaseActivity<ActivityChooseTableStyleBinding>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choose_table_style)
    }

    override fun getViewBinding(): ActivityChooseTableStyleBinding {
        return ActivityChooseTableStyleBinding.inflate(layoutInflater)
    }
}