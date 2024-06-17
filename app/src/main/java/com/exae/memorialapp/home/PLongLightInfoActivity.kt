package com.exae.memorialapp.home

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.OnItemChildClickListener
import com.exae.memorialapp.R
import com.exae.memorialapp.adapter.LongLightStyleAdapter
import com.exae.memorialapp.base.PosBaseActivity
import com.exae.memorialapp.base.handleResponse
import com.exae.memorialapp.bean.AllMaterialOfferItemModel
import com.exae.memorialapp.bean.StyleLongLightModel
import com.exae.memorialapp.databinding.ActivityPlongLightInfoBinding
import com.exae.memorialapp.viewmodel.MemorialModel
import com.lxj.xpopup.XPopup
import com.scwang.smart.refresh.header.BezierRadarHeader
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
@Route(path = "/app/long/light")
class PLongLightInfoActivity : PosBaseActivity<ActivityPlongLightInfoBinding>(),
    OnItemChildClickListener {
    private val viewModel: MemorialModel by viewModels()

    @Inject
    lateinit var listAdapter: LongLightStyleAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
//        setContentView(R.layout.activity_plong_light_info)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        setToolTitle("点亮长明灯")
        setBackState(true)
        setSettingImage(false)
        initView()
        requestNetData()
    }

    private fun initView() {
        binding.apply {
//            smartRefreshLayout.setRefreshHeader(BezierRadarHeader(this@PLongLightInfoActivity))
            mListView.layoutManager = GridLayoutManager(this@PLongLightInfoActivity, 2)
            mListView.adapter = listAdapter
            //下拉刷新
//            smartRefreshLayout.setOnRefreshListener {
//                requestNetData()
//                smartRefreshLayout.finishRefresh(true)
//            }
            emptyView.setOnClickListener {
                requestNetData()
            }
            light.setOnClickListener {
//                chooseWay()
            }
        }
        listAdapter.setOnItemChildClickListener(this)
        listAdapter.addChildClickViewIds(R.id.choose)

        viewModel.allMaterialOfferResponse.observe(this, Observer { resources ->
            handleResponse(resources) {
                if (!it.data.isNullOrEmpty()) {
//                    listAdapter.data.clear()
//                    listAdapter.data.addAll(it.data)
//                    listAdapter.notifyDataSetChanged()
                    listAdapter.setNewInstance(it.data.get(0).children)
                    listAdapter.setAnimationWithDefault(BaseQuickAdapter.AnimationType.SlideInBottom)
                    binding.emptyView.visibility = View.GONE
                    binding.mListView.visibility = View.VISIBLE
                    binding.light.visibility = View.VISIBLE
                } else {
                    binding.emptyView.visibility = View.VISIBLE
                    binding.mListView.visibility = View.GONE
                    binding.light.visibility = View.GONE
                }
            }
        })
    }

    private fun requestNetData() {
        viewModel.getAllMaterialOfferRequest("1")
    }

    override fun getViewBinding(): ActivityPlongLightInfoBinding {
        return ActivityPlongLightInfoBinding.inflate(layoutInflater)
    }

    override fun onItemChildClick(adapter: BaseQuickAdapter<*, *>, view: View, position: Int) {
        val itemData = adapter.data[position] as AllMaterialOfferItemModel
        when (view.id) {
            R.id.choose -> {
                if (position != listAdapter.selectPos) {
                    itemData.isChoose = true
                    listAdapter.notifyItemChanged(position)
                    if (listAdapter.selectPos != -1) {
                        (adapter.data.get(listAdapter.selectPos) as AllMaterialOfferItemModel).isChoose =
                            false
                        listAdapter.notifyItemChanged(listAdapter.selectPos)
                    }
                    listAdapter.selectPos = position
                }
            }
        }
    }
   private fun chooseWay() {
        val pop = XPopup.Builder(this)
            .asBottomList("请选择一项", arrayOf("拍照", "相册")) { position, text ->
                when (position) {
                    0 -> {}
                    1 -> {}
                }
            }.show()
    }
}