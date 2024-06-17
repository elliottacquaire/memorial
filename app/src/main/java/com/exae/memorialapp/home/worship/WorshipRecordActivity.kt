package com.exae.memorialapp.home.worship

import android.os.Bundle
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.exae.memorialapp.R
import com.exae.memorialapp.base.PosBaseActivity
import com.exae.memorialapp.databinding.ActivityWorshipRecordBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
@Route(path = "/app/worship/record")
class WorshipRecordActivity : PosBaseActivity<ActivityWorshipRecordBinding>() {
    private var type = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
//        setContentView(R.layout.activity_worship_record)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        setToolTitle("有效记录")
        setRecordTitle("只看我")
        setBackState(true)
        setRightTv(true)
        initView()
    }

    private fun initView() {

    }

    override fun rightTvClick() {
        super.rightTvClick()
        when (type) {
            0 -> {
                setRecordTitle("全部")
                type = 1
            }

            1 -> {
                setRecordTitle("只看我")
                type = 0
            }
        }
    }

    override fun getViewBinding(): ActivityWorshipRecordBinding {
        return ActivityWorshipRecordBinding.inflate(layoutInflater)
    }
}