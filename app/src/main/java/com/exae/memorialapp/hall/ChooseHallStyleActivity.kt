package com.exae.memorialapp.hall

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.OnItemChildClickListener
import com.exae.memorialapp.R
import com.exae.memorialapp.adapter.MemorialStyleAdapter
import com.exae.memorialapp.base.PosBaseActivity
import com.exae.memorialapp.base.handleResponse
import com.exae.memorialapp.databinding.ActivityChooseHallStyleBinding
import com.exae.memorialapp.viewmodel.MemorialModel
import com.scwang.smart.refresh.header.BezierRadarHeader
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
@Route(path = "/app/choose/hall")
class ChooseHallStyleActivity : PosBaseActivity<ActivityChooseHallStyleBinding>(), OnItemChildClickListener {
    @Inject
    lateinit var listAdapter: MemorialStyleAdapter

    private val viewModel: MemorialModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setToolTitle("大厅风格选择")
        setBackState(true)
        binding.apply {
            smartRefreshLayout.setRefreshHeader(BezierRadarHeader(this@ChooseHallStyleActivity))
            mListView.layoutManager = GridLayoutManager(this@ChooseHallStyleActivity,2)
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
        showLoading()
        requestNetData()
        listAdapter.setOnItemChildClickListener(this)
        listAdapter.addChildClickViewIds(R.id.choose)

        viewModel.styleHallResponse.observe(this, Observer { resources ->
            handleResponse(resources, {
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
                dismissLoading()
            },
                {
                    dismissLoading()
                }
            )
        })

    }

    private fun requestNetData() {
        viewModel.styleHallRequest(0)
    }

    override fun getViewBinding(): ActivityChooseHallStyleBinding {
        return ActivityChooseHallStyleBinding.inflate(layoutInflater)
    }

    override fun onItemChildClick(adapter: BaseQuickAdapter<*, *>, view: View, position: Int) {
        when(view.id){
            R.id.choose -> {
                ARouter.getInstance().build("/app/choose/hall").navigation(this)
            }
        }
    }
}