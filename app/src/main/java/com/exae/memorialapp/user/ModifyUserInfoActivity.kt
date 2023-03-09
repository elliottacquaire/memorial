package com.exae.memorialapp.user

import android.os.Bundle
import androidx.activity.viewModels
import com.alibaba.android.arouter.facade.annotation.Route
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.request.RequestOptions
import com.exae.memorialapp.R
import com.exae.memorialapp.base.PosBaseActivity
import com.exae.memorialapp.databinding.ActivityModifyUserInfoBinding
import com.exae.memorialapp.requestData.SexType
import com.exae.memorialapp.viewmodel.MemorialModel
import com.loper7.date_time_picker.DateTimePicker
import com.loper7.date_time_picker.dialog.CardDatePickerDialog
import com.luck.picture.lib.utils.ToastUtils
import com.lxj.xpopup.XPopup
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
@Route(path = "/app/modify/userinfo")
class ModifyUserInfoActivity : PosBaseActivity<ActivityModifyUserInfoBinding>() {
    private var chooseType = SexType.MAN.type

    private val viewModel: MemorialModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setToolTitle("修改个人资料")
        setBackState(true)
        binding.mAvatar.setOnClickListener {
            val pop = XPopup.Builder(this)
                .asBottomList("请选择一项", arrayOf("拍照", "相册")) { position, text ->
                    when (position) {
                        0 -> openCamera()
                        1 -> openGallery()
                    }
                }.show()
        }

        val url =
            "https://ss0.baidu.com/94o3dSag_xI4khGko9WTAnF6hhy/zhidao/pic/item/a6efce1b9d16fdfabf36882ab08f8c5495ee7b9f.jpg"
        Glide.with(this)
            .load(url)
            .placeholder(R.mipmap.head)
            .error(R.mipmap.head)
            .apply(RequestOptions.bitmapTransform(CircleCrop()))
            .into(binding.mAvatar)

        binding.butSave.setOnClickListener {
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
                        ToastUtils.showToast(this@ModifyUserInfoActivity, millisecond.toString())
                    }

                }).build().show()
        }
        binding.radioGroup.setOnCheckedChangeListener { _, id ->
            when (id) {
                R.id.man -> {
                    chooseType = SexType.MAN.type

                }
                R.id.woman -> {
                    chooseType = SexType.WOMAN.type

                }
                R.id.secret -> {
                    chooseType = SexType.SECRET.type

                }
            }
        }
    }

    private fun openGallery() {

    }

    private fun openCamera() {

    }

    override fun getViewBinding(): ActivityModifyUserInfoBinding {
        return ActivityModifyUserInfoBinding.inflate(layoutInflater)
    }
}