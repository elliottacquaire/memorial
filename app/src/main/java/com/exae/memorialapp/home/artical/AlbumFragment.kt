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
import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.launcher.ARouter
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.OnItemLongClickListener
import com.exae.memorialapp.R
import com.exae.memorialapp.base.CoreFragment
import com.exae.memorialapp.base.handleResponse
import com.exae.memorialapp.bean.AlbumListModel
import com.exae.memorialapp.bean.ArticleListModel
import com.exae.memorialapp.bean.CommentListModel
import com.exae.memorialapp.databinding.FragmentAlbumBinding
import com.exae.memorialapp.dialog.CancelDialog
import com.exae.memorialapp.eventbus.AttentionEvent
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
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
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
    lateinit var listAdapter: AlbumItemAdapter

    private val viewModel: MemorialModel by viewModels()
    private var pageNum: Int = 1
    private var isEditable = false

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
        EventBus.getDefault().register(this)
        _binding = FragmentAlbumBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            smartRefreshLayout.setRefreshHeader(BezierRadarHeader(activity))
            mListView.layoutManager = LinearLayoutManager(activity)
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
                inputAlbumDialog()
            }
        }

        viewModel.getAlbumtListResponse.observe(viewLifecycleOwner, Observer { resources ->
            handleResponse(resources, {
                if (!it.data.isNullOrEmpty()) {
                    if (pageNum == 1) {
                        listAdapter.data.clear()
                        binding.smartRefreshLayout.finishRefresh(true)
                        listAdapter.setNewInstance(it.data)
                    } else {
                        listAdapter.addData(it.data)
                    }
                    if (it.data.size < 20) {
                        listAdapter.loadMoreModule.loadMoreEnd()
                    } else {
                        listAdapter.loadMoreModule.loadMoreComplete()
                        pageNum++
                    }
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
                requestNetData()
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

        viewModel.modifyAlbumResponse.observe(viewLifecycleOwner, Observer { resources ->
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
            if (!isEditable) return@setOnItemLongClickListener true
            val data = listAdapter.data.get(position) as AlbumListModel
//            XPopup.Builder(requireContext())
//                .hasStatusBarShadow(false)
//                .hasNavigationBar(false)
//                .isDestroyOnDismiss(true)
//                .isDarkTheme(true)
//                .asConfirm("温馨提示", "确定要删除此相册吗？") {
//                    deleteAlbum(data.ids)
//                }.show()
//            fragmentManager?.let {
//                CancelDialog().show(it, block = {
//                    ToastUtil.showCenter("ddd")
//                }, blockModify = {
//
//                })
//            }

            CancelDialog.getInstants(
                data.name,
                deleteClick = {
                    deleteAlbum(data.ids)
                },
                modifyClick = {
                    if (it.isBlank()) {
                        ToastUtil.showCenter("请输入相册名")
                        return@getInstants
                    }
                    viewModel.modifyAlbumRequest(data.ids, it)
                },
            ).show(childFragmentManager, "dialog")

            true
        }

        listAdapter.setOnItemClickListener { adapter, _, position ->
            val item = adapter.getItem(position) as AlbumListModel
            ARouter.getInstance().build("/app/album/pic")
                .withInt("memorialNo", memorialNo ?: -1)
                .withInt("albumId", item.ids)
                .withString("name", item.name)
                .withBoolean("isEdit", isEditable)
//                .withInt("type", 1)
                .navigation(activity)
        }
    }

    private fun inputAlbumDialog() {
        XPopup.Builder(context)
            .hasStatusBarShadow(false)
            .hasNavigationBar(false)
            .isDestroyOnDismiss(true)
            .isDarkTheme(true)
            .asInputConfirm("新建相册", "") { text ->
                if (text.isNullOrBlank()) {
                    ToastUtil.showCenter("输入内容不能为空")
                    return@asInputConfirm
                }
                memorialNo?.let { viewModel.createAlbumRequest(it, text) }
            }.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        EventBus.getDefault().unregister(this)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun attentionStatus(event: AttentionEvent) {
        isEditable = event.edit
        if (event.edit) {
            binding.commit.visibility = View.VISIBLE
        } else {
            binding.commit.visibility = View.GONE
        }
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

}