package com.exae.memorialapp.visit

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.launcher.ARouter
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.OnItemChildClickListener
import com.exae.memorialapp.R
import com.exae.memorialapp.adapter.AgreeHistoryAdapter
import com.exae.memorialapp.base.CoreFragment
import com.exae.memorialapp.base.handleResponse
import com.exae.memorialapp.bean.ApplyListModel
import com.exae.memorialapp.bean.ManageMemorialModel
import com.exae.memorialapp.databinding.FragmentAgreeHistoryBinding
import com.exae.memorialapp.requestData.ApplyType
import com.exae.memorialapp.requestData.HandleApplyType
import com.exae.memorialapp.requestData.handleStatusList
import com.exae.memorialapp.requestData.statusList
import com.exae.memorialapp.utils.ToastUtil
import com.exae.memorialapp.viewmodel.MemorialModel
import com.lxj.xpopup.XPopup
import com.scwang.smart.refresh.header.BezierRadarHeader
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [AgreeHistoryFragment.newInstance] factory method to
 * create an instance of this fragment.
 */

@AndroidEntryPoint
class AgreeHistoryFragment : CoreFragment(R.layout.fragment_agree_history),
    OnItemChildClickListener {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private var _binding: FragmentAgreeHistoryBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MemorialModel by viewModels()

    private var status: String = ApplyType.ELSE.tips
    private var statusType: Int = ApplyType.ELSE.type

    @Inject
    lateinit var listAdapter: AgreeHistoryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentAgreeHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            smartRefreshLayout.setRefreshHeader(BezierRadarHeader(context))
            mListView.layoutManager = LinearLayoutManager(context)
            mListView.adapter = listAdapter
            //下拉刷新
            smartRefreshLayout.setOnRefreshListener {
                requestNetData()
                smartRefreshLayout.finishRefresh(true)
            }
            emptyView.setOnClickListener {
                requestNetData()
            }
            relChoose.setOnClickListener {
                chooseStatus()
            }
        }

        listAdapter.setOnItemChildClickListener(this)
        listAdapter.addChildClickViewIds(R.id.modify)
        listAdapter.addChildClickViewIds(R.id.parent)

        viewModel.handleApplyListMemorialResponse.observe(this, Observer { resources ->
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

        viewModel.handleApplyMemorialResponse.observe(this, Observer { resources ->
            handleResponse(resources, {
                dismissLoading()
            },
                {
                    dismissLoading()
                }
            )
        })

        requestNetData()
    }

    private fun chooseStatus() {
        var position = -1
        statusList.forEachIndexed { index, s ->
            if (s == status) {
                position = index
                return@forEachIndexed
            }
        }
        val pop = XPopup.Builder(requireContext())
            .isDarkTheme(false)
            .hasShadowBg(false)
            .isViewMode(true)
            .isDestroyOnDismiss(true)
            .asBottomList("请选择一项", statusList, null, position) { _, text ->
                status = text
                binding.tvChoose.text = text
                when (text) {
                    ApplyType.ELSE.tips -> statusType = ApplyType.ELSE.type
                    ApplyType.APPLYING.tips -> statusType = ApplyType.APPLYING.type
                    ApplyType.APPLYING_PASS.tips -> statusType = ApplyType.APPLYING_PASS.type
                    ApplyType.APPLYING_REJECT.tips -> statusType = ApplyType.APPLYING_REJECT.type
                }
                requestNetData()
            }.show()
    }

    private fun requestNetData() {
        viewModel.handleApplyListMemorialRequest(statusType)
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
         * @return A new instance of fragment AgreeHistoryFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AgreeHistoryFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    private fun handleStatus(id: Int) {
        XPopup.Builder(requireContext())
            .isDarkTheme(false)
            .hasShadowBg(false)
            .isViewMode(true)
            .isDestroyOnDismiss(true)
            .asBottomList("请选择一项", handleStatusList, null, -1) { _, text ->
                val type = when (text) {
                    HandleApplyType.APPLYING_PASS.tips -> HandleApplyType.APPLYING_PASS.type
                    HandleApplyType.APPLYING_REJECT.tips -> HandleApplyType.APPLYING_REJECT.type
                    else -> -1
                }
                viewModel.handleApplyMemorialRequest(id, type)
            }.show()
    }

    override fun onItemChildClick(adapter: BaseQuickAdapter<*, *>, view: View, position: Int) {
        val item = adapter.getItem(position) as ApplyListModel
        when (view.id) {
            R.id.modify -> {
//                ARouter.getInstance().build("/app/modify/hall")
//                    .withInt("memorialNo", item.memorialNo)
//                    .withString("memorialName", item.name)
//                    .withString("memorialType", item.type)
//                    .navigation(this)
                when (item.status) {
//                    ApplyType.ELSE.type -> ApplyType.ELSE.tips
                    ApplyType.APPLYING.type -> {
                        handleStatus(item.id ?: -1)
                    }
//                    ApplyType.APPLYING_PASS.type -> ApplyType.APPLYING_PASS.tips
//                    ApplyType.APPLYING_REJECT.type -> ApplyType.APPLYING_REJECT.tips
                    else -> {}
                }

//                viewModel.handleApplyMemorialRequest(item.id ?: -1, item.status ?: -1)
            }
            R.id.parent -> {
//                ARouter.getInstance().build("/app/home")
//                    .withInt("memorialNo", item.memorialNo)
//                    .withString("memorialName", item.name)
//                    .withString("memorialType", item.type)
//                    .navigation(this)
            }
        }
    }
}