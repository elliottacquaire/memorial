package com.exae.memorialapp.home

import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import com.alibaba.android.arouter.facade.annotation.Route
import com.bumptech.glide.Glide
import com.exae.memorialapp.R
import com.exae.memorialapp.base.PosBaseActivity
import com.exae.memorialapp.base.handleResponse
import com.exae.memorialapp.databinding.ActivityChooseOfferingsBinding
import com.exae.memorialapp.viewmodel.MemorialModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
@Route(path = "/app/choose/offer")
class ChooseOfferingsActivity : PosBaseActivity<ActivityChooseOfferingsBinding>() {
    private val viewModel: MemorialModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
//        setContentView(R.layout.activity_choose_offerings)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        setToolTitle("祭品列表")
        setBackState(true)
        setSettingImage(false)
        viewModel.getAllMaterialOfferRequest("0")
        initRequest()
    }

    private fun initRequest() {
        viewModel.allMaterialOfferResponse.observe(this, Observer { resources ->
            handleResponse(resources, {
                val result = it.data
                binding.apply {
//                    Glide.with(baseContext)
//                        .load(result?.picUrlPrefix + result?.hallPicUrl)
//                        .into(binding.memorialPic)

//                    Glide.with(baseContext)
//                        .load(result?.picUrlPrefix + result?.avatarPicUrl)
//                        .placeholder(R.mipmap.headdd)
//                        .error(R.mipmap.headdd)
//                        .into(binding.headImg)
                }
            },
                {
                }
            )
        })
    }

    override fun getViewBinding(): ActivityChooseOfferingsBinding {
        return ActivityChooseOfferingsBinding.inflate(layoutInflater)
    }
}