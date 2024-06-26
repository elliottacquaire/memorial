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
import com.exae.memorialapp.R
import com.exae.memorialapp.adapter.ManageMemorialAdapter
import com.exae.memorialapp.adapter.VisitHistoryAdapter
import com.exae.memorialapp.base.CoreFragment
import com.exae.memorialapp.base.handleResponse
import com.exae.memorialapp.bean.AlbumListModel
import com.exae.memorialapp.bean.ApplyListModel
import com.exae.memorialapp.databinding.FragmentGoVisitBinding
import com.exae.memorialapp.databinding.FragmentVisitHistoryBinding
import com.exae.memorialapp.requestData.ApplyType
import com.exae.memorialapp.requestData.statusList
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
 * Use the [VisitHistoryFragment.newInstance] factory method to
 * create an instance of this fragment.
 */

@AndroidEntryPoint
class VisitHistoryFragment : CoreFragment(R.layout.fragment_visit_history) {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private var status: String = ApplyType.ELSE.tips
    private var statusType: Int = ApplyType.ELSE.type

    private var _binding: FragmentVisitHistoryBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var listAdapter: VisitHistoryAdapter

    private val viewModel: MemorialModel by viewModels()

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
        _binding = FragmentVisitHistoryBinding.inflate(inflater, container, false)
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

        viewModel.applyHistoryMemorialResponse.observe(this, Observer { resources ->
            handleResponse(resources) {
                if (it.data != null && it.data.isNotEmpty()) {
                    listAdapter.setNewInstance(it.data)
                    binding.emptyView.visibility = View.GONE
                    binding.smartRefreshLayout.visibility = View.VISIBLE
                } else {
                    binding.emptyView.visibility = View.VISIBLE
                    binding.smartRefreshLayout.visibility = View.GONE
                }
            }
        })

        listAdapter.setOnItemClickListener { adapter, _, position ->
            val item = adapter.getItem(position) as ApplyListModel
            when (item.status) {
                ApplyType.APPLYING_PASS.type -> {
                    ARouter.getInstance().build("/app/home")
                        .withInt("memorialNo", item.memorialNo)
                        .withString("memorialName", item.memorialName)
                        .withString("memorialType", item.memorialType)
                        .navigation(activity)
                }
                else -> {}
            }
        }
    }

    private fun requestNetData() {
        viewModel.applyHistoryMemorialRequest(statusType)
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

    override fun onResume() {
        super.onResume()
        requestNetData()
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
         * @return A new instance of fragment VisitHistoryFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            VisitHistoryFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}