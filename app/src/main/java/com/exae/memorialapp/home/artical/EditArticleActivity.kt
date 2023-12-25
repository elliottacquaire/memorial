package com.exae.memorialapp.home.artical

import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.alibaba.android.arouter.facade.annotation.Route
import com.exae.memorialapp.base.PosBaseActivity
import com.exae.memorialapp.base.handleResponse
import com.exae.memorialapp.databinding.ActivityEditArticleBinding
import com.exae.memorialapp.utils.ToastUtil
import com.exae.memorialapp.viewmodel.MemorialModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
@Route(path = "/app/edit/article")
class EditArticleActivity : PosBaseActivity<ActivityEditArticleBinding>() {
    private var memorialNo = -1
    private var introduceText = ""
    private var articleId = -1
    private var showMessage = ""
    private val viewModel: MemorialModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_edit_article)
        setToolTitle("文选")
        setBackState(true)

        memorialNo = intent.getIntExtra("memorialNo", -1)
        introduceText = intent.getStringExtra("introduceText") ?: ""
//        memorialType = intent.getStringExtra("memorialType") ?: ""
        articleId = intent.getIntExtra("articleId", -1)

        initCallBack()
        getArticleDetail()
        binding.save.setOnClickListener {
            when (articleId) {
                -1 -> {
                    showMessage = "发布"
                    createIntroduce()
                }
//                1 -> modifyIntroduce()
                else -> {
                    showMessage = "修改"
                    modifyIntroduce()
                }
            }
        }

    }

    private fun initCallBack() {
        viewModel.getArticleDetailResponse.observe(this, Observer { resources ->
            handleResponse(resources, {
                val result = it.data
                binding.edtContent.setText(result?.content)
                binding.edtTitle.setText(result?.title)
            },
                {
                    ToastUtil.showCenter("${showMessage}失败，请重试")
                }
            )
        })
        viewModel.createArticleResponse.observe(this, Observer { resources ->
            handleResponse(resources, {
                val result = it.data
                ToastUtil.showCenter("${showMessage}成功")
                finish()
            },
                {
                    ToastUtil.showCenter("${showMessage}失败，请重试")
                }
            )
        })

        viewModel.modifyArticleResponse.observe(this, Observer { resources ->
            handleResponse(resources, {
                val result = it.data
                binding.apply {
                    edtTitle.setText(result?.title)
                    edtContent.setText(result?.content)
                }
                ToastUtil.showCenter("${showMessage}成功")
                finish()
            },
                {
                    ToastUtil.showCenter("${showMessage}失败，请重试")
                }
            )
        })
    }

    private fun getArticleDetail() {
        if (articleId < 0) return
//        articleId = 1
        viewModel.getArticleDetailRequest(articleId)
    }

    private fun createIntroduce() {
        val content = binding.edtContent.text.trim().toString()
        val title = binding.edtTitle.text.trim().toString()
        viewModel.createArticleRequest(memorialNo, content, title)
    }

    private fun modifyIntroduce() {
        val content = binding.edtContent.text.trim().toString()
        val title = binding.edtTitle.text.trim().toString()
        viewModel.modifyArticleRequest(memorialNo, articleId, content, title)
    }

    override fun getViewBinding(): ActivityEditArticleBinding {
        return ActivityEditArticleBinding.inflate(layoutInflater)
    }
}