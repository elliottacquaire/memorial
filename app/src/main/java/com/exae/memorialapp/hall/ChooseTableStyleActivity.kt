package com.exae.memorialapp.hall

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.OnItemChildClickListener
import com.exae.memorialapp.R
import com.exae.memorialapp.adapter.MemorialTabletStyleAdapter
import com.exae.memorialapp.base.PosBaseActivity
import com.exae.memorialapp.base.handleResponse
import com.exae.memorialapp.bean.StyleMemorialModel
import com.exae.memorialapp.databinding.ActivityChooseTableStyleBinding
import com.exae.memorialapp.viewmodel.MemorialModel
import com.scwang.smart.refresh.header.BezierRadarHeader
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
@Route(path = "/app/choose/table")
class ChooseTableStyleActivity : PosBaseActivity<ActivityChooseTableStyleBinding>(), OnItemChildClickListener {
    @Inject
    lateinit var listAdapter: MemorialTabletStyleAdapter

    private val viewModel: MemorialModel by viewModels()

    private var clickType = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setToolTitle("灵牌位样式选择")
        setBackState(true)

        clickType = intent.getIntExtra("clickType", -1)

        binding.apply {
            smartRefreshLayout.setRefreshHeader(BezierRadarHeader(this@ChooseTableStyleActivity))
            mListView.layoutManager = GridLayoutManager(this@ChooseTableStyleActivity,2)
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

        viewModel.styleTableResponse.observe(this, Observer { resources ->
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
        viewModel.styleTableRequest(clickType)
    }

    override fun getViewBinding(): ActivityChooseTableStyleBinding {
        return ActivityChooseTableStyleBinding.inflate(layoutInflater)
    }

    override fun onItemChildClick(adapter: BaseQuickAdapter<*, *>, view: View, position: Int) {
        val itemData = adapter.data[position] as StyleMemorialModel
        when(view.id){
            R.id.choose -> {
                val intent = Intent()
                intent.putExtra("name", itemData.name)
                intent.putExtra("ids", itemData.ids)
                setResult(1, intent)
                finish()
            }
        }
    }
}