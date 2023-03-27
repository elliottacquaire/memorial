package com.exae.memorialapp.hall

import android.util.Log
import androidx.viewbinding.ViewBinding
import com.exae.memorialapp.base.PosBaseActivity
import com.exae.memorialapp.util.GlideEngine
import com.luck.picture.lib.basic.PictureSelector
import com.luck.picture.lib.config.SelectMimeType
import com.luck.picture.lib.config.SelectModeConfig
import com.luck.picture.lib.engine.CompressFileEngine
import com.luck.picture.lib.entity.LocalMedia
import com.luck.picture.lib.interfaces.OnResultCallbackListener
import com.lxj.xpopup.XPopup
import com.yalantis.ucrop.UCrop
import java.io.File
import top.zibin.luban.Luban
import top.zibin.luban.OnNewCompressListener

abstract class UploadImageActivity<T : ViewBinding> : PosBaseActivity<T>() {

    var chooseImageUrl = ""

    fun chooseImage() {
        val pop = XPopup.Builder(this)
            .asBottomList("请选择一项", arrayOf("拍照", "相册")) { position, text ->
                when (position) {
                    0 -> openCamera()
                    1 -> openGallery()
                }
            }.show()
    }

    private fun openGallery() {
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
                Luban.with(context).load(source).ignoreBy(100)
                    .setCompressListener(object : OnNewCompressListener {
                        override fun onStart() {
                            Log.i("sss", "----compress-----start-------")
                        }

                        override fun onSuccess(source: String?, compressFile: File?) {
                            call?.onCallback(source, compressFile?.absolutePath)
                            Log.i(
                                "sss",
                                "----compress-----success---${compressFile?.absolutePath}----"
                            )
                        }

                        override fun onError(source: String?, e: Throwable?) {
                            call?.onCallback(source, null)
                            Log.i("sss", "----compress-----error-------")
                        }

                    }).launch()
            })
            .isGif(false)
            .isDisplayCamera(false)
            .forResult(object : OnResultCallbackListener<LocalMedia> {
                override fun onResult(result: ArrayList<LocalMedia>?) {
                    chooseImageUrl = result?.get(0)?.compressPath ?: ""
                    upLoadImgToService()
                }

                override fun onCancel() {
//                    ToastUtils.showToast(this@UploadImageActivity, "cancel")
                }

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
                            Log.i("sss", "----compress-----start-------")
                        }

                        override fun onSuccess(source: String?, compressFile: File?) {
                            call?.onCallback(source, compressFile?.absolutePath)
                            Log.i(
                                "sss",
                                "----compress-----success---${compressFile?.absolutePath}----"
                            )
                        }

                        override fun onError(source: String?, e: Throwable?) {
                            call?.onCallback(source, null)
                            Log.i("sss", "----compress-----error-------")
                        }

                    }).launch()
            })
            .forResult(object : OnResultCallbackListener<LocalMedia> {
                override fun onResult(result: ArrayList<LocalMedia>?) {
                    chooseImageUrl = result?.get(0)?.compressPath ?: ""
                    upLoadImgToService()
                }

                override fun onCancel() {

                }
            })
    }

    open fun upLoadImgToService() {
    }
}