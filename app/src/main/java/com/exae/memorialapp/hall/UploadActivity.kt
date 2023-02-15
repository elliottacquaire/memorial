package com.exae.memorialapp.hall

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import com.alibaba.android.arouter.facade.annotation.Route
import com.exae.memorialapp.base.PosBaseActivity
import com.exae.memorialapp.databinding.ActivityUploadBinding
import com.exae.memorialapp.util.GlideEngine
import com.luck.picture.lib.basic.PictureSelector
import com.luck.picture.lib.config.PictureConfig
import com.luck.picture.lib.config.SelectMimeType
import com.luck.picture.lib.config.SelectModeConfig
import com.luck.picture.lib.engine.CompressFileEngine
import com.luck.picture.lib.entity.LocalMedia
import com.luck.picture.lib.interfaces.OnKeyValueResultCallbackListener
import com.luck.picture.lib.interfaces.OnResultCallbackListener
import com.luck.picture.lib.utils.ToastUtils
import com.lxj.xpopup.XPopup
import com.yalantis.ucrop.UCrop
import dagger.hilt.android.AndroidEntryPoint
import java.io.File
import top.zibin.luban.Luban
import top.zibin.luban.OnNewCompressListener

@AndroidEntryPoint
@Route(path = "/app/upload/img")
class UploadActivity : PosBaseActivity<ActivityUploadBinding>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.click.setOnClickListener {
            openGallery()
        }
        binding.dialog.setOnClickListener {
            val pop = XPopup.Builder(this)
                .asBottomList("请选择一项", arrayOf("拍照","相册")){ position , text ->
                    ToastUtils.showToast(this, text)
                    when(position){
                        0 -> openCamera()
                        1 -> openGallery()
                    }
                }.show()
        }
    }

    private fun openGallery(){
        PictureSelector.create(this)
            .openGallery(SelectMimeType.ofImage())
            .setSelectionMode(SelectModeConfig.SINGLE)
            .setMaxSelectNum(1)
            .setImageSpanCount(3)

            .setImageEngine(GlideEngine.createGlideEngine())
            .setCropEngine { fragment, srcUri, destinationUri, dataSource, requestCode ->
//                UCrop.of(srcUri, destinationUri).start(this@UploadActivity)
//                fragment.activity?.finish()
                val uCrop = UCrop.of(srcUri,destinationUri,dataSource)
                fragment.activity?.let { uCrop.start(it,fragment,requestCode) }
            }
            .setCompressEngine(CompressFileEngine { context, source, call ->
                Luban.with(context).load(source).ignoreBy(100).setCompressListener(object : OnNewCompressListener{
                    override fun onStart() {
                        Log.i("sss","----compress-----start-------")
                    }

                    override fun onSuccess(source: String?, compressFile: File?) {
                        call?.onCallback(source, compressFile?.absolutePath)
                        Log.i("sss","----compress-----success---${compressFile?.absolutePath}----")
                    }

                    override fun onError(source: String?, e: Throwable?) {
                        call?.onCallback(source, null)
                        Log.i("sss","----compress-----error-------")
                    }

                }).launch()
            })
            .isGif(false)
            .isDisplayCamera(false)
            .forResult(object : OnResultCallbackListener<LocalMedia> {
                override fun onResult(result: ArrayList<LocalMedia>?) {
                    ToastUtils.showToast(this@UploadActivity, result?.get(0)?.compressPath ?: "nulll")
                    val srcUri = result?.get(0)?.path ?: "nulll"
//                    UCrop.of(srcUri, destinationUri).start(this@UploadActivity)
                }

                override fun onCancel() {
                    ToastUtils.showToast(this@UploadActivity, "cancel")
                }

            })
    }
    private fun openCamera(){
        PictureSelector.create(this)
            .openCamera(SelectMimeType.ofImage())
            .setCropEngine { fragment, srcUri, destinationUri, dataSource, requestCode ->
                Log.i("sss","----url-----$srcUri-------")
//                val uCrop = UCrop.of(srcUri, destinationUri).start(this@UploadActivity)
                val uCrop = UCrop.of(srcUri,destinationUri,dataSource)
                fragment.activity?.let { uCrop.start(it,fragment,requestCode) }
//                fragment.activity?.finish()
            }
            .setCompressEngine(object : CompressFileEngine{
                override fun onStartCompress(
                    context: Context?,
                    source: ArrayList<Uri>?,
                    call: OnKeyValueResultCallbackListener?
                ) {
                    Luban.with(context).load(source).ignoreBy(100).setCompressListener(object : OnNewCompressListener{
                        override fun onStart() {
                            Log.i("sss","----compress-----start-------")
                        }

                        override fun onSuccess(source: String?, compressFile: File?) {
                            call?.onCallback(source, compressFile?.absolutePath)
                            Log.i("sss","----compress-----success---${compressFile?.absolutePath}----")
                        }

                        override fun onError(source: String?, e: Throwable?) {
                            call?.onCallback(source, null)
                            Log.i("sss","----compress-----error-------")
                        }

                    }).launch()
                }
            })
            .forResult(object : OnResultCallbackListener<LocalMedia> {
                override fun onResult(result: ArrayList<LocalMedia>?) {
                    ToastUtils.showToast(this@UploadActivity, result?.get(0)?.path ?: "nulll")
                }

                override fun onCancel() {
                    ToastUtils.showToast(this@UploadActivity, "cancel")
                }
            })
    }

//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        if (resultCode == RESULT_OK && requestCode == UCrop.REQUEST_CROP) {
//            val resultUri = data?.let { UCrop.getOutput(it) }
//            ToastUtils.showToast(this@UploadActivity, "-crop-result----$resultUri-----")
//        } else if (resultCode == UCrop.RESULT_ERROR) {
//            val cropError = data?.let { UCrop.getError(it) }
//        }else if (resultCode == RESULT_OK && resultCode == PictureConfig.CHOOSE_REQUEST) {
//            val resultUri = data?.let { UCrop.getOutput(it) }
//            ToastUtils.showToast(this@UploadActivity, "-crop-result----$resultUri-----")
//        }
//    }

    override fun getViewBinding(): ActivityUploadBinding {
        return ActivityUploadBinding.inflate(layoutInflater)
    }
}