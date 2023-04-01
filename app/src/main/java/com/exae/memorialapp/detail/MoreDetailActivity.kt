package com.exae.memorialapp.detail

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.request.RequestOptions
import com.exae.memorialapp.R
import com.exae.memorialapp.base.PosBaseActivity
import com.exae.memorialapp.base.handleResponse
import com.exae.memorialapp.bean.MoreMemorialModel
import com.exae.memorialapp.bean.SingleMemorialModel
import com.exae.memorialapp.databinding.ActivityMoreDetailBinding
import com.exae.memorialapp.requestData.HallType
import com.exae.memorialapp.requestData.MoreMemorialRequest
import com.exae.memorialapp.requestData.requestCodeHallStyle
import com.exae.memorialapp.requestData.requestCodeHallStyleDouble
import com.exae.memorialapp.requestData.requestCodeHallStyleOne
import com.exae.memorialapp.requestData.requestCodeMemorialStyle
import com.exae.memorialapp.requestData.requestCodeMemorialStyleDouble
import com.exae.memorialapp.requestData.requestCodeMemorialStyleOne
import com.exae.memorialapp.requestData.requestCodeTableStyle
import com.exae.memorialapp.requestData.requestCodeTableStyleDouble
import com.exae.memorialapp.requestData.requestCodeTableStyleDouble1
import com.exae.memorialapp.requestData.requestCodeTableStyleOne
import com.exae.memorialapp.utils.ToastUtil
import com.exae.memorialapp.viewmodel.MemorialModel
import com.lxj.xpopup.XPopup
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
@Route(path = "/app/more/detail")
class MoreDetailActivity : PosBaseActivity<ActivityMoreDetailBinding>() {
    var memorialNo = -1
    private var chooseType = HallType.MORE_HALL.type
    private val viewModel: MemorialModel by viewModels()

    private val requestMore = MoreMemorialRequest()
    private var resultData: MoreMemorialModel? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setBackState(true)
        memorialNo = intent.getIntExtra("memorialNo", -1)

        viewModel.getMoreDetailMemorialRequest(memorialNo)
        showLoading()

        viewModel.moreMemorialDetailResponse.observe(this, Observer { resources ->
            handleResponse(resources, {
                dismissLoading()
                val result = it.data
                resultData = it.data
                setToolTitle("${result?.name}纪念馆详情")
                binding.apply {
                    edtMemorialName.setText(result?.name ?: "")
                    tvMemorialStyle.text = result?.memorialName
                    tvHallStyle.text = result?.hallName
                    tvTableStyle.text = result?.tabletName
                    edtMemorialTheme.setText(result?.theme ?: "")
                    edtMemorialCreateName.setText(result?.monumentMaker ?: "")
                    edtMemorialLocal.setText(result?.ancestralHome ?: "")
                }
                requestMore.memorialNo = result?.memorialNo
                requestMore.memorialId = result?.memorialId ?: -1
                requestMore.hallId = result?.hallId ?: -1
                requestMore.tabletId = result?.tabletId ?: -1

                Glide.with(this@MoreDetailActivity)
                    .load(result?.picUrlPrefix + result?.memorialPicUrl)
                    .apply(RequestOptions.bitmapTransform(CircleCrop()))
                    .into(binding.styleImg)
            },
                {
                    dismissLoading()
                }
            )
        })

        initMoreCreate()
    }

    private fun initMoreCreate() {
        binding.tvMemorialStyle.setOnClickListener {
            ARouter.getInstance().build("/app/choose/memorial")
                .withInt("clickType", chooseType)
                .navigation(this, requestCodeMemorialStyle)
        }
        binding.tvHallStyle.setOnClickListener {
            ARouter.getInstance().build("/app/choose/hall")
                .withInt("clickType", chooseType)
                .navigation(this, requestCodeHallStyle)
        }
        binding.tvTableStyle.setOnClickListener {
            ARouter.getInstance().build("/app/choose/table")
                .withInt("clickType", chooseType)
                .navigation(this, requestCodeTableStyle)
        }
        binding.butCreateOne.setOnClickListener {
            requestMore.name = binding.edtMemorialName.text.trim().toString()
            requestMore.theme = binding.edtMemorialTheme.text.trim().toString()
            requestMore.monumentMaker = binding.edtMemorialCreateName.text.trim().toString()
            requestMore.ancestralHome = binding.edtMemorialLocal.text.trim().toString()
            if (isChanged()) {
                viewModel.moreMemorialModifyRequest(requestMore)
                showLoading()
            } else {
                ToastUtil.showCenter("修改成功")
                finish()
            }
        }
        viewModel.moreMemorialModifyResponse.observe(this, Observer { resources ->
            handleResponse(resources, {
                dismissLoading()
                ToastUtil.showCenter("修改成功")
                finish()
            },
                {
                    dismissLoading()
                }
            )
        })
    }

    private fun isChanged(): Boolean {
        if (resultData?.name == binding.edtMemorialName.text.trim().toString() &&
            resultData?.theme == binding.edtMemorialTheme.text.trim().toString() &&
            resultData?.memorialName == binding.tvMemorialStyle.text.trim().toString() &&
            resultData?.hallName == binding.tvHallStyle.text.trim().toString() &&
            resultData?.tabletName == binding.tvTableStyle.text.trim().toString() &&

            resultData?.monumentMaker == binding.edtMemorialCreateName.text.trim().toString() &&
            resultData?.ancestralHome == binding.edtMemorialLocal.text.trim().toString()
        ) {
            return false
        }
        return true
    }

    override fun onBackPressed() {
        if (isChanged()) {
            confirmDialog()
        } else {
            super.onBackPressed()
        }
    }

    private fun confirmDialog() {
        XPopup.Builder(this)
            .hasStatusBarShadow(false)
            .hasNavigationBar(false)
            .isDestroyOnDismiss(true)
            .isDarkTheme(true)
            .asConfirm("温馨提示", "退出当前页将不保存已修改的内容，\n确定返回吗？") {
                finish()
            }.show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == requestCodeMemorialStyle && resultCode == 1) {
            val name = data?.getStringExtra("name") ?: ""
            val ids = data?.getIntExtra("ids", -1) ?: -1
            binding.tvMemorialStyle.text = name
            requestMore.memorialId = ids
        } else if (requestCode == requestCodeHallStyle && resultCode == 1) {
            val name = data?.getStringExtra("name") ?: ""
            val ids = data?.getIntExtra("ids", -1) ?: -1
            binding.tvHallStyle.text = name
            requestMore.hallId = ids
        } else if (requestCode == requestCodeTableStyle && resultCode == 1) {
            val name = data?.getStringExtra("name") ?: ""
            val ids = data?.getIntExtra("ids", -1) ?: -1
            binding.tvTableStyle.text = name
            requestMore.tabletId = ids
        }
    }

    override fun getViewBinding(): ActivityMoreDetailBinding {
        return ActivityMoreDetailBinding.inflate(layoutInflater)
    }
}