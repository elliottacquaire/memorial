package com.exae.memorialapp.hall

import android.os.Bundle
import com.exae.memorialapp.base.PosBaseActivity
import com.exae.memorialapp.databinding.ActivitySingleHallBinding

class SingleHallActivity : PosBaseActivity<ActivitySingleHallBinding>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun getViewBinding(): ActivitySingleHallBinding {
        return ActivitySingleHallBinding.inflate(layoutInflater)
    }
}