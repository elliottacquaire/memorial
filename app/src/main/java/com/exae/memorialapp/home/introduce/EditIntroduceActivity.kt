package com.exae.memorialapp.home.introduce

import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.alibaba.android.arouter.facade.annotation.Route
import com.exae.memorialapp.base.PosBaseActivity
import com.exae.memorialapp.base.handleResponse
import com.exae.memorialapp.databinding.ActivityEditIntroduceBinding
import com.exae.memorialapp.utils.ToastUtil
import com.exae.memorialapp.viewmodel.MemorialModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
@Route(path = "/app/edit/introduce")
class EditIntroduceActivity : PosBaseActivity<ActivityEditIntroduceBinding>() {
    private var memorialType = ""
    private var memorialNo = -1
    private var memorialName = ""
    private var introduceId = -1
    private var type = -1 // 0 create,1 modify

    private val viewModel: MemorialModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setToolTitle("编辑生平")
        setBackState(true)

        memorialNo = intent.getIntExtra("memorialNo", -1)
        memorialName = intent.getStringExtra("memorialName") ?: ""
        memorialType = intent.getStringExtra("memorialType") ?: ""
        introduceId = intent.getIntExtra("introduceId", -1)
        type = intent.getIntExtra("type", -1)

        initCallBack()

        binding.save.setOnClickListener {
            when (type) {
                0 -> createIntroduce()
                1 -> modifyIntroduce()
                else -> {}
            }
        }
    }

    private fun initCallBack() {
        viewModel.createMemorialIntroduceResponse.observe(this, Observer { resources ->
            handleResponse(resources, {
                val result = it.data
                ToastUtil.showCenter("发布成功")
                finish()
            },
                {
                    ToastUtil.showCenter("发布失败，请重试")
                }
            )
        })

        viewModel.modifyMemorialIntroduceResponse.observe(this, Observer { resources ->
            handleResponse(resources, {
                val result = it.data
                binding.apply {
                    edtTitle.setText(result?.name)
                    edtContent.setText(result?.content)
                }
                ToastUtil.showCenter("修改成功")
                finish()
            },
                {
                    ToastUtil.showCenter("修改失败，请重试")
                }
            )
        })
    }

    private fun createIntroduce() {
        val introduce = binding.edtContent.text
        viewModel.createMemorialIntroduceRequest(memorialNo, introduce.toString())
    }

    private fun modifyIntroduce() {
        val introduce = binding.edtContent.text
        viewModel.modifyMemorialIntroduceRequest(memorialNo, introduceId, introduce.toString())
    }

    override fun getViewBinding(): ActivityEditIntroduceBinding {
        return ActivityEditIntroduceBinding.inflate(layoutInflater)
    }
}