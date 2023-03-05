package com.exae.memorialapp.hall

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.core.provider.FontsContractCompat.Columns.RESULT_CODE
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

    val bannerList = mutableListOf<ManageMemorialModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setToolTitle("管理纪念馆")
        setBackState(true)
        setSettingImage(true)
        setRightImg(R.mipmap.add)
        bannerList.add(
            ManageMemorialModel(
                11,
                "ee1",
                "1232321",
                "name1",
                "2000-11-111",
                "level 11",
                "eee1"
            )
        )
        bannerList.add(
            ManageMemorialModel(
                1,
                "ee",
                "123232",
                "name",
                "2000-11-11",
                "level 1",
                "eee"
            )
        )
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
        listAdapter.data.clear()
        listAdapter.data.addAll(bannerList)

        listAdapter.setOnItemChildClickListener(this)
        listAdapter.addChildClickViewIds(R.id.modify)
        listAdapter.addChildClickViewIds(R.id.hall)

        viewModel.manageMerioResponse.observe(this, Observer { resources ->
            handleResponse(resources) {
                if (it.data != null && !it.data.isNullOrEmpty()) {
                    listAdapter.data.clear()
                    listAdapter.data.addAll(it.data)
//                    listAdapter.notifyDataSetChanged()
                    listAdapter.setAnimationWithDefault(BaseQuickAdapter.AnimationType.SlideInBottom)
//                    emptyView.visibility = View.GONE
//                    smartRefreshLayout.visibility = View.VISIBLE
                } else {
//                    emptyView.visibility = View.VISIBLE
//                    smartRefreshLayout.visibility = View.GONE
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
//        ARouter.getInstance().build("/app/choose/table").navigation(this)
    }

    private fun requestNetData() {
//        viewModel.manageMerioRequest()
    }

    override fun getViewBinding(): ActivityManageMemorialBinding {
        return ActivityManageMemorialBinding.inflate(layoutInflater)
    }

    override fun onItemChildClick(adapter: BaseQuickAdapter<*, *>, view: View, position: Int) {
        when(view.id){
            R.id.modify -> {
                ARouter.getInstance().build("/app/choose/hall").navigation(this,101)
            }
            R.id.hall -> {
                ARouter.getInstance().build("/app/choose/memorial").navigation(this)
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