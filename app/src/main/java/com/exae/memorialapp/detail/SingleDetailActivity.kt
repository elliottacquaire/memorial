package com.exae.memorialapp.detail

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.request.RequestOptions
import com.exae.memorialapp.R
import com.exae.memorialapp.base.handleResponse
import com.exae.memorialapp.bean.SingleMemorialModel
import com.exae.memorialapp.databinding.ActivitySingleDetailBinding
import com.exae.memorialapp.hall.UploadImageActivity
import com.exae.memorialapp.requestData.HallType
import com.exae.memorialapp.requestData.SingleMemorialRequest
import com.exae.memorialapp.requestData.nationList
import com.exae.memorialapp.requestData.*
import com.exae.memorialapp.requestData.shipList
import com.exae.memorialapp.utils.CommonUtils
import com.exae.memorialapp.utils.ToastUtil
import com.exae.memorialapp.viewmodel.MemorialModel
import com.loper7.date_time_picker.DateTimePicker
import com.loper7.date_time_picker.dialog.CardDatePickerDialog
import com.lxj.xpopup.XPopup
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
@Route(path = "/app/single/detail")
class SingleDetailActivity : UploadImageActivity<ActivitySingleDetailBinding>() {

    //    @JvmField
//    @Autowired(name = "memorialNo")
//    var memorialNo = "ss"
    private var chooseType = HallType.ONE_HALL.type
    private var memorialNo = -1
    private val viewModel: MemorialModel by viewModels()
    private var requestOne = SingleMemorialRequest()
    private var resultData: SingleMemorialModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        ARouter.getInstance().inject(this)
//        setToolTitle("纪念馆资料")
        setBackState(true)
        memorialNo = intent.getIntExtra("memorialNo", -1)
        viewModel.getSingleMemorialDetailRequest(memorialNo)
        showLoading()

        viewModel.singleMemorialDetailResponse.observe(this, Observer { resources ->
            handleResponse(resources, {
                val result = it.data
                resultData = it.data
                dismissLoading()
                setToolTitle("${result?.name}纪念馆详情")
                binding.apply {
                    tvBrithData.text = CommonUtils.getSplitTime(result?.birthDate ?: "")
                    tvDeathData.text = CommonUtils.getSplitTime(result?.leaveDate ?: "")
                    edtPersonName.setText(result?.name ?: "")
                    tvMemorialStyle.text = result?.memorialName
                    tvHallStyle.text = result?.hallName
                    tvTableStyle.text = result?.tabletName
                    when (result?.sex) {
                        "0" -> {
                            man.isChecked = true
                            requestOne.sex = "0"
                        }
                        "1" -> {
                            woman.isChecked = true
                            requestOne.sex = "1"
                        }
                        else -> {
                            secret.isChecked = true
                            requestOne.sex = "2"
                        }
                    }
                    tvNation.text = result?.nation ?: ""
                    tvRelation.text = result?.relationship ?: ""
                    tvEpitaph.setText(result?.epitaph ?: "")
                    tvAddress.setText(result?.address ?: "")
                    requestOne.avatarPicUrl = result?.avatarPicUrl ?: ""
                    requestOne.memorialNo = result?.memorialNo
                    requestOne.memorialId = result?.memorialId ?: -1
                    requestOne.hallId = result?.hallId ?: -1
                    requestOne.tabletId = result?.tabletId ?: -1

                    Glide.with(this@SingleDetailActivity)
                        .load(result?.picUrlPrefix + result?.avatarPicUrl)
                        .placeholder(R.mipmap.headdd)
                        .error(R.mipmap.headdd)
                        .apply(RequestOptions.bitmapTransform(CircleCrop()))
                        .into(binding.headImg)

                    if (result?.editable == true) {
                        butCreateOne.visibility = View.VISIBLE
                    } else {
                        butCreateOne.visibility = View.GONE
                    }
                }
            },
                {
                    dismissLoading()
                }
            )
        })

        initOneCreate()
        upLoadImgResult()
    }

    override fun upLoadImgToService() {
        super.upLoadImgToService()
        if (chooseImageUrl.isEmpty()) return
        viewModel.uploadImageRequest(chooseImageUrl)
        showLoading()
    }

    private fun upLoadImgResult() {
        viewModel.uploadImageResponse.observe(this, Observer { resources ->
            handleResponse(resources, {
                dismissLoading()
                requestOne.avatarPicUrl = it.data?.fileName ?: ""
                Glide.with(this)
                    .load(it.data?.url)
                    .placeholder(R.mipmap.headdd)
                    .error(R.mipmap.headdd)
                    .apply(RequestOptions.bitmapTransform(CircleCrop()))
                    .into(binding.headImg)
            },
                {
                    dismissLoading()
                }
            )
        })
    }

    private fun initOneCreate() {
        binding.radioGroup.setOnCheckedChangeListener { _, id ->
            when (id) {
                R.id.man -> {
                    requestOne.sex = "0"
                }
                R.id.woman -> {
                    requestOne.sex = "1"
                }
                R.id.secret -> {
                    requestOne.sex = "2"
                }
            }
        }

        binding.headImg.setOnClickListener {
            chooseImage()
        }

        binding.tvBrithData.setOnClickListener {
            CardDatePickerDialog.builder(this).setTitle("请选择日期")
                .setLabelText("年", "月", "日")
                .setDisplayType(
                    mutableListOf(
                        DateTimePicker.YEAR,
                        DateTimePicker.MONTH,
                        DateTimePicker.DAY
                    )
                )
                .setOnChoose(listener = object : CardDatePickerDialog.OnChooseListener {
                    override fun onChoose(millisecond: Long) {
                        binding.tvBrithData.text = CommonUtils.getTime(millisecond)
                    }
                }).build().show()
        }

        binding.tvDeathData.setOnClickListener {
            CardDatePickerDialog.builder(this).setTitle("请选择日期")
                .setLabelText("年", "月", "日")
                .setDisplayType(
                    mutableListOf(
                        DateTimePicker.YEAR,
                        DateTimePicker.MONTH,
                        DateTimePicker.DAY
                    )
                )
                .setOnChoose(listener = object : CardDatePickerDialog.OnChooseListener {
                    override fun onChoose(millisecond: Long) {
                        binding.tvDeathData.text = CommonUtils.getTime(millisecond)
                    }

                }).build().show()
        }
        binding.tvMemorialStyle.setOnClickListener {
            ARouter.getInstance().build("/app/choose/memorial")
                .withInt("clickType", chooseType)
                .navigation(this, requestCodeMemorialStyleOne)
        }
        binding.tvHallStyle.setOnClickListener {
            ARouter.getInstance().build("/app/choose/hall")
                .withInt("clickType", chooseType)
                .navigation(this, requestCodeHallStyleOne)
        }
        binding.tvTableStyle.setOnClickListener {
            ARouter.getInstance().build("/app/choose/table")
                .withInt("clickType", chooseType)
                .navigation(this, requestCodeTableStyleOne)
        }
        binding.butCreateOne.setOnClickListener {
            requestOne.name = binding.edtPersonName.text.trim().toString()
            requestOne.birthDate = binding.tvBrithData.text.trim().toString()
            requestOne.leaveDate = binding.tvDeathData.text.trim().toString()
            requestOne.epitaph = binding.tvEpitaph.text.trim().toString()
            requestOne.nation = binding.tvNation.text.trim().toString()
            requestOne.relationship = binding.tvRelation.text.trim().toString()
            requestOne.address = binding.tvAddress.text.trim().toString()
            if (isChanged()) {
                viewModel.singleMemorialModifyRequest(requestOne)
                showLoading()
            } else {
                ToastUtil.showCenter("修改成功")
                finish()
            }
        }

        viewModel.singleMemorialModifyResponse.observe(this, Observer { resources ->
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
        chooseNation()
        chooseRelationShip()
    }

    private fun isChanged(): Boolean {
        if (resultData?.avatarPicUrl == requestOne.avatarPicUrl &&
            resultData?.name == binding.edtPersonName.text.trim().toString() &&
            resultData?.sex == requestOne.sex &&
            resultData?.nation == binding.tvNation.text.trim().toString() &&
            (resultData?.birthDate ?: "") == binding.tvBrithData.text.trim().toString() &&
            (resultData?.leaveDate ?: "") == binding.tvDeathData.text.trim().toString() &&
            resultData?.memorialName == binding.tvMemorialStyle.text.trim().toString() &&
            resultData?.hallName == binding.tvHallStyle.text.trim().toString() &&
            resultData?.tabletName == binding.tvTableStyle.text.trim().toString() &&

            resultData?.relationship == binding.tvRelation.text.trim().toString() &&
            resultData?.address == binding.tvAddress.text.trim().toString() &&
            resultData?.epitaph == binding.tvEpitaph.text.trim().toString()
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

    private fun chooseNation() {
        binding.tvNation.setOnClickListener {
            var position = -1
            nationList.forEachIndexed { index, s ->
                if (s == binding.tvNation.text) {
                    position = index
                    return@forEachIndexed
                }
            }
            val pop = XPopup.Builder(this)
                .isDarkTheme(false)
                .hasShadowBg(false)
                .popupHeight(1200)
                .isViewMode(true)
                .isDestroyOnDismiss(true)
                .asBottomList("请选择一项", nationList, null, position) { _, text ->
                    binding.tvNation.text = text
                }.show()
        }
    }

    private fun chooseRelationShip() {
        binding.tvRelation.setOnClickListener {
            var position = -1
            shipList.forEachIndexed { index, s ->
                if (s == binding.tvRelation.text) {
                    position = index
                    return@forEachIndexed
                }
            }
            val pop = XPopup.Builder(this)
                .isDarkTheme(false)
                .hasShadowBg(true)
                .popupHeight(1200)
                .isViewMode(true)
                .isDestroyOnDismiss(true)
                .asBottomList("请选择一项", shipList, null, position) { _, text ->
                    binding.tvRelation.text = text
                }.show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == requestCodeMemorialStyleOne && resultCode == 1) {
            val name = data?.getStringExtra("name") ?: ""
            val ids = data?.getIntExtra("ids", -1) ?: -1
            binding.tvMemorialStyle.text = name
            requestOne.memorialId = ids
        } else if (requestCode == requestCodeHallStyleOne && resultCode == 1) {
            val name = data?.getStringExtra("name") ?: ""
            val ids = data?.getIntExtra("ids", -1) ?: -1
            binding.tvHallStyle.text = name
            requestOne.hallId = ids
        } else if (requestCode == requestCodeTableStyleOne && resultCode == 1) {
            val name = data?.getStringExtra("name") ?: ""
            val ids = data?.getIntExtra("ids", -1) ?: -1
            binding.tvTableStyle.text = name
            requestOne.tabletId = ids
        }
    }

    override fun getViewBinding(): ActivitySingleDetailBinding {
        return ActivitySingleDetailBinding.inflate(layoutInflater)
    }
}