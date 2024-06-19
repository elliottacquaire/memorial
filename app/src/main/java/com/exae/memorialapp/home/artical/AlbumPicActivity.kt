package com.exae.memorialapp.home.artical

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.chad.library.adapter.base.BaseQuickAdapter
import com.exae.memorialapp.base.PosBaseActivity
import com.exae.memorialapp.base.handleResponse
import com.exae.memorialapp.bean.AlbumPicListModel
import com.exae.memorialapp.databinding.ActivityAlbumPicBinding
import com.exae.memorialapp.utils.ToastUtil
import com.exae.memorialapp.viewmodel.MemorialModel
import com.lxj.xpopup.XPopup
import com.scwang.smart.refresh.header.BezierRadarHeader
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
@Route(path = "/app/album/pic")
class AlbumPicActivity : PosBaseActivity<ActivityAlbumPicBinding>() {

    private var memorialNo: Int? = -1
    private var name = ""
    private var albumId = -1
    private var pageNum: Int = 1
    @Inject
    lateinit var listAdapter: AlbumAdapter

    private val viewModel: MemorialModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
//        setContentView(R.layout.activity_album_pic)
        memorialNo = intent.getIntExtra("memorialNo", -1)
        name = intent.getStringExtra("name") ?: ""
//        memorialType = intent.getStringExtra("memorialType") ?: ""
        albumId = intent.getIntExtra("albumId", -1)
        setToolTitle(name)
        setBackState(true)
        initView()
    }

    private fun initView() {
        binding.apply {
            smartRefreshLayout.setRefreshHeader(BezierRadarHeader(this@AlbumPicActivity))
            mListView.layoutManager = GridLayoutManager(this@AlbumPicActivity, 2)
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
                    .withInt("albumId", albumId)
                    .withString("name", name)
//                    .withInt("type", 1)
                    .navigation(this@AlbumPicActivity)
            }
        }

        viewModel.getAlbumPicListResponse.observe(this, Observer { resources ->
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

        listAdapter.setOnItemClickListener { adapter, _, position ->
            val item = adapter.getItem(position) as AlbumPicListModel
            ARouter.getInstance().build("/app/album/modify")
                .withInt("memorialNo", memorialNo ?: -1)
                .withInt("albumId", item.albumId)
                .withInt("id", item.ids)
                .withString("desc", item.picDesc)
                .withString("picUrl", item.picUrl)
                .navigation(this)
        }

        listAdapter.setOnItemLongClickListener { p0, p1, position ->
            val data = listAdapter.data.get(position) as AlbumPicListModel
            XPopup.Builder(this)
                .hasStatusBarShadow(false)
                .hasNavigationBar(false)
                .isDestroyOnDismiss(true)
                .isDarkTheme(true)
                .asConfirm("温馨提示", "确定要删除此图片吗？") {
                    deletePic(data.ids)
                }.show()
            true
        }

        viewModel.deletePicResponse.observe(this, Observer { resources ->
            handleResponse(resources, {
                val result = it.data
                requestNetData()
                ToastUtil.showCenter(it.message)
            },
                {
                }
            )
        })
    }

    override fun onResume() {
        super.onResume()
        requestNetData()
    }

    private fun deletePic(id: Int) {
        viewModel.deletePicRequest(id)
    }

    private fun requestNetData() {
        if ((albumId ?: -1) == -1) return
        albumId?.let { viewModel.getAlbumPicListRequest(it, 1) }
    }

    override fun getViewBinding(): ActivityAlbumPicBinding {
        return ActivityAlbumPicBinding.inflate(layoutInflater)
    }
}