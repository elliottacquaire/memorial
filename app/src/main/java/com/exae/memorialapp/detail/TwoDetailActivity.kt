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
import com.exae.memorialapp.bean.DoubleMemorialModel
import com.exae.memorialapp.databinding.ActivityTwoDetailBinding
import com.exae.memorialapp.hall.UploadImageActivity
import com.exae.memorialapp.requestData.DoubleMemorialRequest
import com.exae.memorialapp.requestData.HallType
import com.exae.memorialapp.requestData.requestCodeHallStyleDouble
import com.exae.memorialapp.requestData.requestCodeMemorialStyleDouble
import com.exae.memorialapp.requestData.requestCodeTableStyleDouble
import com.exae.memorialapp.requestData.requestCodeTableStyleDouble1
import com.exae.memorialapp.utils.CommonUtils
import com.exae.memorialapp.utils.ToastUtil
import com.exae.memorialapp.viewmodel.MemorialModel
import com.loper7.date_time_picker.DateTimePicker
import com.loper7.date_time_picker.dialog.CardDatePickerDialog
import com.lxj.xpopup.XPopup
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
@Route(path = "/app/two/detail")
class TwoDetailActivity : UploadImageActivity<ActivityTwoDetailBinding>() {
    private var memorialNo = -1
    private var chooseType = HallType.TWO_HALL.type
    private val viewModel: MemorialModel by viewModels()
    private val requestDouble = DoubleMemorialRequest()
    private var resultData: DoubleMemorialModel? = null
    private var headClickType: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setToolTitle("大厅风格选择")
        setBackState(true)
        memorialNo = intent.getIntExtra("memorialNo", -1)
        viewModel.getTwoMemorialDetailRequest(memorialNo)
        showLoading()

        viewModel.twoMemorialDetailResponse.observe(this, Observer { resources ->
            handleResponse(resources, {
                val result = it.data
                resultData = it.data
                dismissLoading()
                setToolTitle("${result?.name}纪念馆详情")
                binding.apply {
                    edtMemorialName.setText(result?.name ?: "")
                    edtMember.setText(result?.relationship ?: "")
                    edtBirthInfo.setText(result?.description ?: "")
                    tvMemorialStyle.text = result?.memorialName ?: ""
                    tvHallStyle.text = result?.hallName ?: ""

                    requestDouble.memorialNo = result?.memorialNo
                    requestDouble.hallId = result?.hallId ?: -1
                    requestDouble.memorialId = result?.memorialId ?: -1
                    requestDouble.tabletId1 = result?.tabletId1 ?: -1
                    requestDouble.tabletId2 = result?.tabletId2 ?: -1

                    edtPersonName.setText(result?.name1 ?: "")
                    when (result?.sex1) {
                        "0" -> {
                            tvGender.text = "男"
                            requestDouble.sex1 = "0"
                        }
                        "1" -> {
                            tvGender.text = "女"
                            requestDouble.sex1 = "1"
                        }
                        else -> {
                            tvGender.text = "保密"
                            requestDouble.sex1 = "2"
                        }
                    }
                    tvBrithData.text = CommonUtils.getSplitTime(result?.birthDate1 ?: "")
                    tvDeathData.text = CommonUtils.getSplitTime(result?.leaveDate1 ?: "")
                    tvTableStyle.text = result?.tabletName1
                    Glide.with(this@TwoDetailActivity)
                        .load(result?.picUrlPrefix + result?.avatarPicUrl1)
                        .placeholder(R.mipmap.headdd)
                        .error(R.mipmap.headdd)
                        .apply(RequestOptions.bitmapTransform(CircleCrop()))
                        .into(binding.headImg)


                    edtPersonName1.setText(result?.name2 ?: "")
                    when (result?.sex2) {
                        "0" -> {
                            tvGender1.text = "男"
                            requestDouble.sex2 = "0"
                        }
                        "1" -> {
                            tvGender1.text = "女"
                            requestDouble.sex2 = "1"
                        }
                        else -> {
                            tvGender1.text = "保密"
                            requestDouble.sex2 = "2"
                        }
                    }
                    tvBrithData1.text = CommonUtils.getSplitTime(result?.birthDate2 ?: "")
                    tvDeathData1.text = CommonUtils.getSplitTime(result?.leaveDate2 ?: "")
                    tvTableStyle1.text = result?.tabletName2
                    Glide.with(this@TwoDetailActivity)
                        .load(result?.picUrlPrefix + result?.avatarPicUrl2)
                        .placeholder(R.mipmap.headdd)
                        .error(R.mipmap.headdd)
                        .apply(RequestOptions.bitmapTransform(CircleCrop()))
                        .into(binding.headImg1)

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

        initTwoCreate()
    }

    private fun initTwoCreate() {
        binding.tvMemorialStyle.setOnClickListener {
            ARouter.getInstance().build("/app/choose/memorial")
                .withInt("clickType", chooseType)
                .navigation(this, requestCodeMemorialStyleDouble)
        }
        binding.tvHallStyle.setOnClickListener {
            ARouter.getInstance().build("/app/choose/hall")
                .withInt("clickType", chooseType)
                .navigation(this, requestCodeHallStyleDouble)
        }
        binding.tvTableStyle.setOnClickListener {
            ARouter.getInstance().build("/app/choose/table")
                .withInt("clickType", chooseType)
                .navigation(this, requestCodeTableStyleDouble)
        }
        binding.tvTableStyle1.setOnClickListener {
            ARouter.getInstance().build("/app/choose/table")
                .withInt("clickType", chooseType)
                .navigation(this, requestCodeTableStyleDouble1)
        }
        binding.butCreateOne.setOnClickListener {
            requestDouble.name = binding.edtMemorialName.text.trim().toString()
            requestDouble.relationship = binding.edtMember.text.trim().toString()
            requestDouble.description = binding.edtBirthInfo.text.trim().toString()

            requestDouble.name1 = binding.edtPersonName.text.trim().toString()
            requestDouble.name2 = binding.edtPersonName1.text.trim().toString()

            requestDouble.birthDate1 = binding.tvBrithData.text.trim().toString()
            requestDouble.birthDate2 = binding.tvBrithData1.text.trim().toString()
            requestDouble.leaveDate1 = binding.tvDeathData.text.trim().toString()
            requestDouble.leaveDate2 = binding.tvDeathData1.text.trim().toString()

//            requestDouble.sex1 = binding.tvGender.text.trim().toString()
//            requestDouble.sex2 = binding.tvGender1.text.trim().toString()

            if (isChanged()) {
                viewModel.twoMemorialModifyRequest(requestDouble)
                showLoading()
            } else {
                ToastUtil.showCenter("修改成功")
                finish()
            }
        }

        binding.tvGender.setOnClickListener {
            val pop = XPopup.Builder(this)
                .asBottomList("请选择一项", arrayOf("男", "女", "保密")) { position, text ->
                    when (position) {
                        0 -> {
                            binding.tvGender.text = "男"
                            requestDouble.sex1 = "0"
                        }
                        1 -> {
                            binding.tvGender.text = "女"
                            requestDouble.sex1 = "1"
                        }
                        2 -> {
                            binding.tvGender.text = "保密"
                            requestDouble.sex1 = "2"
                        }
                    }
                }.show()
        }
        binding.tvGender1.setOnClickListener {
            val pop = XPopup.Builder(this)
                .asBottomList("请选择一项", arrayOf("男", "女", "保密")) { position, text ->
                    when (position) {
                        0 -> {
                            binding.tvGender1.text = "男"
                            requestDouble.sex2 = "0"
                        }
                        1 -> {
                            binding.tvGender1.text = "女"
                            requestDouble.sex2 = "1"
                        }
                        2 -> {
                            binding.tvGender1.text = "保密"
                            requestDouble.sex2 = "2"
                        }
                    }
                }.show()
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

        binding.tvBrithData1.setOnClickListener {
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
                        binding.tvBrithData1.text = CommonUtils.getTime(millisecond)
                    }
                }).build().show()
        }

        binding.tvDeathData1.setOnClickListener {
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
                        binding.tvDeathData1.text = CommonUtils.getTime(millisecond)
                    }

                }).build().show()
        }

        viewModel.twoMemorialModifyResponse.observe(this, Observer { resources ->
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

        binding.headImg.setOnClickListener {
            headClickType = 1
            chooseImage()
        }

        binding.headImg1.setOnClickListener {
            headClickType = 2
            chooseImage()
        }

        chooseRelationShip()
        upLoadImgResult()
    }

    private fun isChanged(): Boolean {
        if (resultData?.avatarPicUrl1 == requestDouble.avatarPicUrl1 &&
            resultData?.avatarPicUrl2 == requestDouble.avatarPicUrl2 &&
            resultData?.name == binding.edtMemorialName.text.trim().toString() &&
            resultData?.relationship == binding.edtMember.text.trim().toString() &&
            resultData?.description == binding.edtBirthInfo.text.trim().toString() &&
            resultData?.memorialName == binding.tvMemorialStyle.text.trim().toString() &&
            resultData?.hallName == binding.tvHallStyle.text.trim().toString() &&

            resultData?.name1 == binding.edtPersonName.text.trim().toString() &&
            resultData?.sex1 == requestDouble.sex1 &&
            (resultData?.birthDate1 ?: "") == binding.tvBrithData.text.trim().toString() &&
            (resultData?.leaveDate1 ?: "") == binding.tvDeathData.text.trim().toString() &&
            resultData?.tabletName1 == binding.tvTableStyle.text.trim().toString() &&

            resultData?.name2 == binding.edtPersonName1.text.trim().toString() &&
            resultData?.sex2 == requestDouble.sex2 &&
            (resultData?.birthDate2 ?: "") == binding.tvBrithData1.text.trim().toString() &&
            (resultData?.leaveDate2 ?: "") == binding.tvDeathData1.text.trim().toString() &&
            resultData?.tabletName2 == binding.tvTableStyle1.text.trim().toString()
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

    private fun chooseRelationShip() {
        /*binding.edtMember.setOnClickListener {
            var position = -1
            shipList.forEachIndexed { index, s ->
                if (s == binding.edtMember.text) {
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
                    binding.edtMember.text = text
                }.show()
        }*/
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
                when (headClickType) {
                    1 -> {
                        requestDouble.avatarPicUrl1 = it.data?.fileName ?: ""
                        Glide.with(this)
                            .load(it.data?.url)
                            .placeholder(R.mipmap.headdd)
                            .error(R.mipmap.headdd)
                            .apply(RequestOptions.bitmapTransform(CircleCrop()))
                            .into(binding.headImg)
                    }
                    2 -> {
                        requestDouble.avatarPicUrl2 = it.data?.fileName ?: ""
                        Glide.with(this)
                            .load(it.data?.url)
                            .placeholder(R.mipmap.headdd)
                            .error(R.mipmap.headdd)
                            .apply(RequestOptions.bitmapTransform(CircleCrop()))
                            .into(binding.headImg1)
                    }
                    else -> {}
                }

            },
                {
                    dismissLoading()
                }
            )
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == requestCodeMemorialStyleDouble && resultCode == 1) {
            val name = data?.getStringExtra("name") ?: ""
            val ids = data?.getIntExtra("ids", -1) ?: -1
            binding.tvMemorialStyle.text = name
            requestDouble.memorialId = ids
        } else if (requestCode == requestCodeHallStyleDouble && resultCode == 1) {
            val name = data?.getStringExtra("name") ?: ""
            val ids = data?.getIntExtra("ids", -1) ?: -1
            binding.tvHallStyle.text = name
            requestDouble.hallId = ids
        } else if (requestCode == requestCodeTableStyleDouble && resultCode == 1) {
            val name = data?.getStringExtra("name") ?: ""
            val ids = data?.getIntExtra("ids", -1) ?: -1
            binding.tvTableStyle.text = name
            requestDouble.tabletId1 = ids
        } else if (requestCode == requestCodeTableStyleDouble1 && resultCode == 1) {
            val name = data?.getStringExtra("name") ?: ""
            val ids = data?.getIntExtra("ids", -1) ?: -1
            binding.tvTableStyle1.text = name
            requestDouble.tabletId2 = ids
        }
    }

    override fun getViewBinding(): ActivityTwoDetailBinding {
        return ActivityTwoDetailBinding.inflate(layoutInflater)
    }
}