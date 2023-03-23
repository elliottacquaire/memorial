package com.exae.memorialapp.detail

import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.alibaba.android.arouter.facade.annotation.Route
import com.exae.memorialapp.base.PosBaseActivity
import com.exae.memorialapp.base.handleResponse
import com.exae.memorialapp.databinding.ActivityMoreDetailBinding
import com.exae.memorialapp.viewmodel.MemorialModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
@Route(path = "/app/more/detail")
class MoreDetailActivity : PosBaseActivity<ActivityMoreDetailBinding>() {
    var memorialNo = -1

    private val viewModel: MemorialModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setToolTitle("大厅风格选择")
        setBackState(true)
        memorialNo = intent.getIntExtra("memorialNo", -1)

        viewModel.getMoreDetailMemorialRequest(memorialNo)
        showLoading()

        viewModel.moreMemorialDetailResponse.observe(this, Observer { resources ->
            handleResponse(resources, {
                dismissLoading()
            },
                {
                    dismissLoading()
                }
            )
        })

    }

    override fun getViewBinding(): ActivityMoreDetailBinding {
        return ActivityMoreDetailBinding.inflate(layoutInflater)
    }
}