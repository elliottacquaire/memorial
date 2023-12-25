package com.exae.memorialapp.home.artical

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.chad.library.adapter.base.BaseQuickAdapter
import com.exae.memorialapp.R
import com.exae.memorialapp.adapter.MemorialCommentAdapter
import com.exae.memorialapp.base.CoreFragment
import com.exae.memorialapp.base.handleResponse
import com.exae.memorialapp.databinding.FragmentArticalListBinding
import com.exae.memorialapp.viewmodel.MemorialModel
import com.scwang.smart.refresh.header.BezierRadarHeader
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "memorialNo"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ArticalListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint
class ArticalListFragment : CoreFragment(R.layout.fragment_artical_list) {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private var _binding: FragmentArticalListBinding? = null
    private val binding get() = _binding!!

    private var memorialNo: Int? = -1

    @Inject
    lateinit var listAdapter: ArticalAdapter

    private val viewModel: MemorialModel by viewModels()
    private var pageNum: Int = 1

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
        _binding = FragmentArticalListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requestNetData()
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

            }
        }

        viewModel.getArticleListResponse.observe(viewLifecycleOwner, Observer { resources ->
            handleResponse(resources, {
                if (!it.data.isNullOrEmpty()) {
                    if (pageNum == 1){
                        listAdapter.data.clear()
                        binding.smartRefreshLayout.finishRefresh(true)
                    }else{

                    }
                    listAdapter.data.addAll(it.data)
                    if (it.data.size < 20){
                        listAdapter.loadMoreModule.loadMoreEnd()
                    }else{
                        listAdapter.loadMoreModule.loadMoreComplete()
                    }
                    pageNum++
//                    listAdapter.data.clear()
//                    listAdapter.data.addAll(it.data)
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

        listAdapter.loadMoreModule.setOnLoadMoreListener {
            requestNetData()
        }

    }

    private fun requestNetData() {
        if ((memorialNo ?: -1) == -1) return
        memorialNo?.let { viewModel.getArticleListRequest(it, pageNum) }
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
         * @return A new instance of fragment ArticalListFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(memorialNo: Int, param2: String) =
            ArticalListFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_PARAM1, memorialNo)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}