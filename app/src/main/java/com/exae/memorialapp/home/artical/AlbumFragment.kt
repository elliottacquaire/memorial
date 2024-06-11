package com.exae.memorialapp.home.artical

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.alibaba.android.arouter.launcher.ARouter
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.OnItemLongClickListener
import com.exae.memorialapp.R
import com.exae.memorialapp.base.CoreFragment
import com.exae.memorialapp.base.handleResponse
import com.exae.memorialapp.bean.CommentListModel
import com.exae.memorialapp.databinding.FragmentAlbumBinding
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
import com.scwang.smart.refresh.header.BezierRadarHeader
import com.yalantis.ucrop.UCrop
import dagger.hilt.android.AndroidEntryPoint
import java.io.File
import javax.inject.Inject
import top.zibin.luban.Luban
import top.zibin.luban.OnNewCompressListener

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "memorialNo"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [AlbumFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint
class AlbumFragment : CoreFragment(R.layout.fragment_album) {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private var _binding: FragmentAlbumBinding? = null
    private val binding get() = _binding!!

    private var memorialNo: Int? = -1

    @Inject
    lateinit var listAdapter: AlbumAdapter

    private val viewModel: MemorialModel by viewModels()
    private var pageNum: Int = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            memorialNo = it.getInt(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentAlbumBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            smartRefreshLayout.setRefreshHeader(BezierRadarHeader(activity))
            mListView.layoutManager = GridLayoutManager(activity, 2)
            mListView.adapter = listAdapter
            //下拉刷新
            smartRefreshLayout.setOnRefreshListener {
                pageNum = 1
                requestNetData()
                smartRefreshLayout.finishRefresh(true)
            }
            emptyView.setOnClickListener {
                pageNum = 1
                requestNetData()
            }
            commit.setOnClickListener {
                ARouter.getInstance().build("/app/album/publish")
                    .withInt("memorialNo", memorialNo ?: -1)
//                    .withInt("albumId", item.ids)
//                    .withString("content", item.content)
                    .withInt("type", 1)
                    .navigation(activity)
            }
        }

        viewModel.getAlbumtListResponse.observe(viewLifecycleOwner, Observer { resources ->
            handleResponse(resources, {
                if (!it.data.isNullOrEmpty()) {
                    if (pageNum == 1) {
                        listAdapter.data.clear()
                        binding.smartRefreshLayout.finishRefresh(true)
                    } else {

                    }
                    listAdapter.setNewInstance(it.data)
                    if (it.data.size < 20) {
                        listAdapter.data.addAll(it.data)
                        listAdapter.loadMoreModule.loadMoreEnd()
                    } else {
                        listAdapter.loadMoreModule.loadMoreComplete()
                    }
                    pageNum++
//                    listAdapter.data.addAll(it.data)
                    listAdapter.setAnimationWithDefault(BaseQuickAdapter.AnimationType.SlideInBottom)
                    binding.emptyView.visibility = View.GONE
                    binding.smartRefreshLayout.visibility = View.VISIBLE
                } else {
                    binding.emptyView.visibility = View.VISIBLE
                    binding.smartRefreshLayout.visibility = View.GONE
                }
            },
                {

                }
            )
        })

        listAdapter.loadMoreModule.setOnLoadMoreListener {
            requestNetData()
        }

        viewModel.createAlbumResponse.observe(viewLifecycleOwner, Observer { resources ->
            handleResponse(resources, {
                val result = it.data
                ToastUtil.showCenter("操作成功")
            },
                {
                    ToastUtil.showCenter("操作失败，请重试")
                }
            )
        })

        viewModel.deleteAlbumResponse.observe(viewLifecycleOwner, Observer { resources ->
            handleResponse(resources, {
                val result = it.data
                requestNetData()
                ToastUtil.showCenter(it.message)
            },
                {
                }
            )
        })

        initListClickEvent()

    }

    override fun onResume() {
        super.onResume()
        requestNetData()
    }

    private fun requestNetData() {
        if ((memorialNo ?: -1) == -1) return
        memorialNo?.let { viewModel.getAlbumListRequest(it, 1) }
    }

    private fun deleteAlbum(albumId: Int) {
//        if ((memorialNo ?: -1) == -1) return
        viewModel.deleteAlbumRequest(albumId)
    }

    private fun initListClickEvent() {
        listAdapter.setOnItemLongClickListener { p0, p1, position ->
            val data = listAdapter.data.get(position) as CommentListModel
            XPopup.Builder(requireContext())
                .hasStatusBarShadow(false)
                .hasNavigationBar(false)
                .isDestroyOnDismiss(true)
                .isDarkTheme(true)
                .asConfirm("温馨提示", "确定要删除此相册吗？") {
                    deleteAlbum(data.ids)
                }.show()
            true
        }
    }

    private fun inputAlbumDialog() {
        XPopup.Builder(context)
            .hasStatusBarShadow(false)
            .hasNavigationBar(false)
            .isDestroyOnDismiss(true)
            .isDarkTheme(true)
            .asInputConfirm("新建相册", "") {text ->
                if (text.isNullOrBlank()){
                    ToastUtil.showCenter("输入内容不能为空")
                    return@asInputConfirm
                }
                memorialNo?.let { viewModel.createAlbumRequest(it, text) }
            }.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment AlbumFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(memorialNo: Int, param2: String) =
            AlbumFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_PARAM1, memorialNo)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    var chooseImageUrl = ""
    fun chooseImage() {
        val pop = XPopup.Builder(context)
            .asBottomList("请选择一项", arrayOf("拍照", "相册")) { position, text ->
                when (position) {
                    0 -> openCamera()
                    1 -> openGallery()
                }
            }.show()
    }

    fun upLoadImgToService() {

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