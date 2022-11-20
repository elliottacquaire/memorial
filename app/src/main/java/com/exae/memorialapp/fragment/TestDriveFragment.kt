package com.exae.memorialapp.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.exae.memorialapp.R
import com.exae.memorialapp.base.CoreFragment
import com.exae.memorialapp.databinding.FragmentTestDriveBinding
import com.exae.memorialapp.viewmodel.PosTestDriveModel
import com.scwang.smart.refresh.header.BezierRadarHeader
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TestDriveFragment : CoreFragment(R.layout.fragment_test_drive) {

    private val viewModel: PosTestDriveModel by viewModels()

    private var _binding: FragmentTestDriveBinding? = null
    private val binding get() = _binding!!

//    @Inject
//    lateinit var listAdapter: PosTestDriveAdapter

    private var isHistory = 0 //历史
    private var status = -1

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentTestDriveBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        isHistory = arguments?.getInt("isHistory", 0) ?: 0
        status = arguments?.getInt("status") ?: -1

//        EventBus.getDefault().register(this)
        binding.apply {
            smartRefreshLayout.setRefreshHeader(BezierRadarHeader(context))

            mListView.layoutManager = LinearLayoutManager(context)

//        mListView.adapter = listAdapter
            //下拉刷新
            smartRefreshLayout.setOnRefreshListener {
                requestNetData()
                smartRefreshLayout.finishRefresh(true)
            }
            emptyView.setOnClickListener {
                requestNetData()
            }
        }

//        viewModel.listResponse.observe(this, Observer { resources ->
//            handleResponse(resources) {
//                if (it.data != null && !it.data.isNullOrEmpty()) {
//                    listAdapter.data.clear()
//                    listAdapter.data.addAll(listNode)
//                    listAdapter.notifyDataSetChanged()
//                    listAdapter.setAnimationWithDefault(BaseQuickAdapter.AnimationType.SlideInBottom)
//                    emptyView.visibility = View.GONE
//                    smartRefreshLayout.visibility = View.VISIBLE
//                } else {
//                    emptyView.visibility = View.VISIBLE
//                    smartRefreshLayout.visibility = View.GONE
//                }
//            }
//
//        })

//        when (viewModel.getRolePermission()) {
//            0 -> viewModel.requestDriveRequest()
//        }

    }


    override fun onResume() {
        super.onResume()
        requestNetData()
    }

    //刷新data
    private fun requestNetData() {
//        when(isHistory){
//            1 -> {
//                viewModel.state.requestHistory.status = status
//                viewModel.requestTestDriveHistoryList()
//            }
//            else ->  viewModel.requestTestDriveList()
//        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
//        EventBus.getDefault().unregister(this)
        _binding = null
    }

}