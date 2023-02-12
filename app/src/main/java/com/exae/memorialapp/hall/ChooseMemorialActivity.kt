package com.exae.memorialapp.hall

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.exae.memorialapp.R
import com.exae.memorialapp.base.PosBaseActivity
import com.exae.memorialapp.databinding.ActivityChooseMemorialBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
@Route(path = "/app/choose/memorial")
class ChooseMemorialActivity : PosBaseActivity<ActivityChooseMemorialBinding>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choose_memorial)
    }

    override fun getViewBinding(): ActivityChooseMemorialBinding {
        return ActivityChooseMemorialBinding.inflate(layoutInflater)
    }
}