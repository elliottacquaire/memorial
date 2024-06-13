package com.exae.memorialapp.home.artical

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import com.alibaba.android.arouter.facade.annotation.Route
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.request.RequestOptions
import com.exae.memorialapp.R
import com.exae.memorialapp.base.PosBaseActivity
import com.exae.memorialapp.base.handleResponse
import com.exae.memorialapp.databinding.ActivityAlbumPublishBinding
import com.exae.memorialapp.databinding.ActivityWorshipRecordBinding
import com.exae.memorialapp.requestData.nationList
import com.exae.memorialapp.util.GlideEngine
import com.exae.memorialapp.utils.ToastUtil
import com.exae.memorialapp.viewmodel.MemorialModel
import com.luck.picture.lib.basic.PictureSelector
import com.luck.picture.lib.config.SelectMimeType
import com.luck.picture.lib.config.SelectModeConfig
import com.luck.picture.lib.engine.CompressFileEngine
import com.luck.picture.lib.entity.LocalMedia
import com.luck.picture.lib.interfaces.OnResultCallbackListener
import com.lxj.xpopup.XPopup
import com.yalantis.ucrop.UCrop
import dagger.hilt.android.AndroidEntryPoint
import java.io.File
import top.zibin.luban.Luban
import top.zibin.luban.OnNewCompressListener

@AndroidEntryPoint
@Route(path = "/app/album/publish")
class AlbumPublishActivity : PosBaseActivity<ActivityAlbumPublishBinding>() {
    private var memorialNo = -1
    private var name = ""
    private var albumId = -1
//    private var type = -1
//    private var checkPosition = 0
//    private val nameList = arrayOf<String>()
//    private val albumIdList = arrayOf<Int>()
    private var picUrl = ""
    private val viewModel: MemorialModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
//        setContentView(R.layout.activity_album_publish)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        setToolTitle("发布图片")
        setBackState(true)

        memorialNo = intent.getIntExtra("memorialNo", -1)
        name = intent.getStringExtra("name") ?: ""
        albumId = intent.getIntExtra("albumId", -1)
//        type = intent.getIntExtra("type", -1)
//        articleId = intent.getIntExtra("articleId", -1)
//        requestNetData()
        initView()
    }

    private fun initView() {
        binding.albumTv.text = name
        binding.addPic.setOnClickListener {
            chooseImage()
        }
        binding.upload.setOnClickListener {
            if (picUrl.isEmpty()) {
                ToastUtil.showCenter("请选择图片")
                return@setOnClickListener
            }
            val desc = binding.edtContent.text.toString().trim()
            viewModel.uploadAlbumPicRequest(albumId, desc, picUrl)
            showLoading()
        }

        viewModel.uploadImageResponse.observe(this, Observer { resources ->
            handleResponse(resources, {
                dismissLoading()
                binding.addPic.visibility = View.GONE
                binding.pic.visibility = View.VISIBLE
                picUrl = it.data?.url ?: ""
                Glide.with(this)
                    .load(it.data?.url)
                    .placeholder(R.mipmap.head)
                    .error(R.mipmap.head)
//                    .apply(RequestOptions.bitmapTransform(CircleCrop()))
                    .into(binding.pic)
            },
                {
                    dismissLoading()
                }
            )
        })

        viewModel.uploadAlbumPicResponse.observe(this, Observer { resources ->
            handleResponse(resources, {
                dismissLoading()
//                binding.addPic.visibility = View.GONE
//                binding.pic.visibility = View.VISIBLE
                finish()
            },
                {
                    dismissLoading()
                }
            )
        })
    }

    override fun getViewBinding(): ActivityAlbumPublishBinding {
        return ActivityAlbumPublishBinding.inflate(layoutInflater)
    }

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

    fun upLoadImgToService() {
        viewModel.uploadImageRequest(chooseImageUrl)
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

}