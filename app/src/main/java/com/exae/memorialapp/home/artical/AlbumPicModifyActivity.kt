package com.exae.memorialapp.home.artical

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import com.alibaba.android.arouter.facade.annotation.Route
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.exae.memorialapp.R
import com.exae.memorialapp.base.PosBaseActivity
import com.exae.memorialapp.base.handleResponse
import com.exae.memorialapp.databinding.ActivityAlbumPicModifyBinding
import com.exae.memorialapp.utils.ToastUtil
import com.exae.memorialapp.viewmodel.MemorialModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
@Route(path = "/app/album/modify")
class AlbumPicModifyActivity : PosBaseActivity<ActivityAlbumPicModifyBinding>() {
    private var memorialNo: Int? = -1
    private var name = ""
    private var desc = ""
    private var picUrl = ""
    private var albumId = -1
    private var id = -1

    private val viewModel: MemorialModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
//        setContentView(R.layout.activity_album_pic_modify)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        memorialNo = intent.getIntExtra("memorialNo", -1)
        name = intent.getStringExtra("name") ?: ""
        desc = intent.getStringExtra("desc") ?: ""
        picUrl = intent.getStringExtra("picUrl") ?: ""
        albumId = intent.getIntExtra("albumId", -1)
        id = intent.getIntExtra("id", -1)
        setToolTitle("编辑")
        setBackState(true)
        setRecordTitle("修改")
        setRightTv(true)
        initView()
    }

    override fun getViewBinding(): ActivityAlbumPicModifyBinding {
        return ActivityAlbumPicModifyBinding.inflate(layoutInflater)
    }

    override fun rightTvClick() {
        val descModify = binding.edit.text.toString().trim()
//        if (descModify.isBlank()){
//            ToastUtil.showCenter("图片描述不能为空")
//        }
        requestNetData(descModify)
    }

    private fun initView() {
        binding.edit.setText(desc)
        Glide.with(this)
            .load(picUrl)
//            .placeholder(R.mipmap.head)
//            .error(R.mipmap.head)
            .into(binding.imgShow)

        viewModel.modifyPicResponse.observe(this, Observer { resources ->
            handleResponse(resources, {
                dismissLoading()
                ToastUtil.showCenter("操作成功")
                finish()
            },
                {
                    dismissLoading()
                }
            )
        })
    }

    private fun requestNetData(desc: String) {
        if ((albumId ?: -1) == -1) return
        showLoading()
        albumId?.let { viewModel.modifyPicRequest(it,id, desc, picUrl) }
    }
}