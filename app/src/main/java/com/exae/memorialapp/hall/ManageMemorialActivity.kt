package com.exae.memorialapp.hall

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.OnItemChildClickListener
import com.exae.memorialapp.R
import com.exae.memorialapp.adapter.ManageMemorialAdapter
import com.exae.memorialapp.base.PosBaseActivity
import com.exae.memorialapp.base.handleResponse
import com.exae.memorialapp.bean.ManageMemorialModel
import com.exae.memorialapp.databinding.ActivityManageMemorialBinding
import com.exae.memorialapp.viewmodel.MemorialModel
import com.luck.picture.lib.utils.ToastUtils
import com.scwang.smart.refresh.header.BezierRadarHeader
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
@Route(path = "/app/manage/hall")
class ManageMemorialActivity : PosBaseActivity<ActivityManageMemorialBinding>() ,OnItemChildClickListener{

    @Inject
    lateinit var listAdapter: ManageMemorialAdapter

    private val viewModel: MemorialModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setToolTitle("管理纪念馆")
        setBackState(true)
        setSettingImage(true)
        setRightImg(R.mipmap.add)
        binding.apply {
            smartRefreshLayout.setRefreshHeader(BezierRadarHeader(this@ManageMemorialActivity))
            mListView.layoutManager = LinearLayoutManager(this@ManageMemorialActivity)
            mListView.adapter = listAdapter
            //下拉刷新
            smartRefreshLayout.setOnRefreshListener {
                requestNetData()
                smartRefreshLayout.finishRefresh(true)
            }
            emptyView.setOnClickListener {
                requestNetData()
            }
        }

        listAdapter.setOnItemChildClickListener(this)
        listAdapter.addChildClickViewIds(R.id.modify)
        listAdapter.addChildClickViewIds(R.id.parent)

        viewModel.manageMerioResponse.observe(this, Observer { resources ->
            handleResponse(resources) {
                if (it.data != null && it.data.isNotEmpty()) {
                    listAdapter.data.clear()
                    listAdapter.data.addAll(it.data)
                    listAdapter.notifyDataSetChanged()
                    listAdapter.setAnimationWithDefault(BaseQuickAdapter.AnimationType.SlideInBottom)
                    binding.emptyView.visibility = View.GONE
                    binding.smartRefreshLayout.visibility = View.VISIBLE
                } else {
                    binding.emptyView.visibility = View.VISIBLE
                    binding.smartRefreshLayout.visibility = View.GONE
                }
            }
        })

    }

    override fun onResume() {
        super.onResume()
        requestNetData()
    }

    override fun leftTvClick() {
        super.leftTvClick()
        finish()
    }

    override fun rightImageClick() {
        super.rightImageClick()
        ARouter.getInstance().build("/app/create/hall").navigation(this)
    }

    private fun requestNetData() {
        viewModel.manageMerioRequest()
    }

    override fun getViewBinding(): ActivityManageMemorialBinding {
        return ActivityManageMemorialBinding.inflate(layoutInflater)
    }

    override fun onItemChildClick(adapter: BaseQuickAdapter<*, *>, view: View, position: Int) {
        val item = adapter.getItem(position) as ManageMemorialModel
        when (view.id) {
            R.id.modify -> {
                ARouter.getInstance().build("/app/modify/hall")
                    .withInt("memorialNo", item.ememorialNo)
                    .withString("memorialName", item.name)
                    .withString("memorialType", item.type)
                    .navigation(this)
            }
            R.id.parent -> {

            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 101 && resultCode == 1) {
            val ss = data?.getStringExtra("data")?:"qq"
            ToastUtils.showToast(this@ManageMemorialActivity, ss)
        }
    }
}