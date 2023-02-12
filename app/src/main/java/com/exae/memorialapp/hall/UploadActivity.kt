package com.exae.memorialapp.hall

import android.os.Bundle
import com.exae.memorialapp.R
import com.exae.memorialapp.base.PosBaseActivity
import com.exae.memorialapp.databinding.ActivityUploadBinding

class UploadActivity : PosBaseActivity<ActivityUploadBinding>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upload)
    }

    override fun getViewBinding(): ActivityUploadBinding {
        return ActivityUploadBinding.inflate(layoutInflater)
    }
}