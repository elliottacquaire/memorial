package com.exae.memorialapp.hall

import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.exae.memorialapp.base.PosBaseActivity
import com.exae.memorialapp.databinding.ActivityCreateHallBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
@Route(path = "/app/create/hall")
class CreateHallActivity : PosBaseActivity<ActivityCreateHallBinding>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun getViewBinding(): ActivityCreateHallBinding {
        return ActivityCreateHallBinding.inflate(layoutInflater)
    }
}