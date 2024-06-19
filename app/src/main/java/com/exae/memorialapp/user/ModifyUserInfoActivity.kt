package com.exae.memorialapp.user

import android.os.Bundle
import android.util.Log
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
import com.exae.memorialapp.common.ShareUtil
import com.exae.memorialapp.databinding.ActivityModifyUserInfoBinding
import com.exae.memorialapp.requestData.SexType
import com.exae.memorialapp.util.GlideEngine
import com.exae.memorialapp.utils.ToastUtil
import com.exae.memorialapp.viewmodel.MemorialModel
import com.loper7.date_time_picker.DateTimePicker
import com.loper7.date_time_picker.dialog.CardDatePickerDialog
import com.luck.picture.lib.basic.PictureSelector
import com.luck.picture.lib.config.SelectMimeType
import com.luck.picture.lib.config.SelectModeConfig
import com.luck.picture.lib.engine.CompressFileEngine
import com.luck.picture.lib.entity.LocalMedia
import com.luck.picture.lib.interfaces.OnResultCallbackListener
import com.luck.picture.lib.utils.ToastUtils
import com.lxj.xpopup.XPopup
import com.orhanobut.logger.Logger
import com.yalantis.ucrop.UCrop
import dagger.hilt.android.AndroidEntryPoint
import java.io.File
import top.zibin.luban.Luban
import top.zibin.luban.OnNewCompressListener

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
            XPopup.Builder(this)
                .asBottomList("请选择一项", arrayOf("拍照", "相册")) { position, text ->
                    when (position) {
                        0 -> openCamera()
                        1 -> openGalleryImage()
                    }
                }.show()
        }

        val url =
            "https://ss0.baidu.com/94o3dSag_xI4khGko9WTAnF6hhy/zhidao/pic/item/a6efce1b9d16fdfabf36882ab08f8c5495ee7b9f.jpg"


        binding.butSave.setOnClickListener {

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

        viewModel.uploadImageResponse.observe(this, Observer { resources ->
            handleResponse(resources, {
                dismissLoading()
                Glide.with(this)
                    .load(it.data?.url)
                    .placeholder(R.mipmap.head)
                    .error(R.mipmap.head)
                    .apply(RequestOptions.bitmapTransform(CircleCrop()))
                    .into(binding.mAvatar)
            },
                {
                    dismissLoading()
                }
            )
        })

    }

    private fun openCamera() {
        PictureSelector.create(this)
            .openCamera(SelectMimeType.ofImage())
            .setCropEngine { fragment, srcUri, destinationUri, dataSource, requestCode ->
                val uCrop = UCrop.of(srcUri, destinationUri, dataSource)
                fragment.activity?.let { uCrop.start(it, fragment, requestCode) }
            }
            .setCompressEngine(CompressFileEngine { context, source, call ->
                Luban.with(context).load(source).ignoreBy(100)
                    .setCompressListener(object : OnNewCompressListener {
                        override fun onStart() {
                        }

                        override fun onSuccess(source: String?, compressFile: File?) {
                            call?.onCallback(source, compressFile?.absolutePath)
                        }

                        override fun onError(source: String?, e: Throwable?) {
                            call?.onCallback(source, null)
                            ToastUtil.showCenter(e?.message)
                        }

                    }).launch()
            })
            .forResult(object : OnResultCallbackListener<LocalMedia> {
                override fun onResult(result: ArrayList<LocalMedia>?) {
                    val srcUri = result?.get(0)?.compressPath ?: ""
                    if (srcUri.isEmpty()) return
                    viewModel.uploadImageRequest(srcUri)
                }

                override fun onCancel() {
                }
            })
    }

    private fun openGalleryImage() {
        PictureSelector.create(this)
            .openGallery(SelectMimeType.ofImage())
            .setSelectionMode(SelectModeConfig.SINGLE)
            .setMaxSelectNum(1)
            .setImageSpanCount(3)
            .setImageEngine(GlideEngine.createGlideEngine())
            .setCropEngine { fragment, srcUri, destinationUri, dataSource, requestCode ->
                val uCrop = UCrop.of(srcUri, destinationUri, dataSource)
                uCrop.withAspectRatio(1f, 1f)
                fragment.activity?.let { uCrop.start(it, fragment, requestCode) }
            }
            .setCompressEngine(CompressFileEngine { context, source, call ->
                Luban.with(context).load(source).ignoreBy(100).setCompressListener(object :
                    OnNewCompressListener {
                    override fun onStart() {
                    }

                    override fun onSuccess(source: String?, compressFile: File?) {
                        call?.onCallback(source, compressFile?.absolutePath)
                    }

                    override fun onError(source: String?, e: Throwable?) {
                        call?.onCallback(source, null)
                        ToastUtil.showCenter(e?.message)
                    }

                }).launch()
            })
            .isGif(false)
            .isDisplayCamera(false)
            .forResult(object : OnResultCallbackListener<LocalMedia> {
                override fun onResult(result: ArrayList<LocalMedia>?) {
                    val srcUri = result?.get(0)?.compressPath ?: ""
                    if (srcUri.isEmpty()) return
                    viewModel.uploadImageRequest(srcUri)
                    showLoading()
                }

                override fun onCancel() {

                }

            })
    }

    override fun getViewBinding(): ActivityModifyUserInfoBinding {
        return ActivityModifyUserInfoBinding.inflate(layoutInflater)
    }

    private fun dataPicker() {
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
}