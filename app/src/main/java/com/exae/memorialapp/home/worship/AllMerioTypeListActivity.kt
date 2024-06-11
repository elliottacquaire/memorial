package com.exae.memorialapp.home.worship

import android.os.Bundle
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.alibaba.android.arouter.facade.annotation.Route
import com.exae.memorialapp.R
import com.exae.memorialapp.base.PosBaseActivity
import com.exae.memorialapp.databinding.ActivityAllMerioTypeListBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
@Route(path = "/app/merio/type")
class AllMerioTypeListActivity : PosBaseActivity<ActivityAllMerioTypeListBinding>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
//        setContentView(R.layout.activity_all_merio_type_list)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        setToolTitle("")
        setBackState(true)
        initView()
    }

    override fun getViewBinding(): ActivityAllMerioTypeListBinding {
        return ActivityAllMerioTypeListBinding.inflate(layoutInflater)
    }

    private fun initView() {

    }
}