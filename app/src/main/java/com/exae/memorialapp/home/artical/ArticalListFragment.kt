package com.exae.memorialapp.home.artical

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
import com.chad.library.adapter.base.listener.OnItemClickListener
import com.chad.library.adapter.base.listener.OnItemLongClickListener
import com.exae.memorialapp.R
import com.exae.memorialapp.adapter.MemorialCommentAdapter
import com.exae.memorialapp.base.CoreFragment
import com.exae.memorialapp.base.handleResponse
import com.exae.memorialapp.bean.ArticleListModel
import com.exae.memorialapp.bean.CommentListModel
import com.exae.memorialapp.bean.ManageMemorialModel
import com.exae.memorialapp.databinding.FragmentArticalListBinding
import com.exae.memorialapp.utils.ToastUtil
import com.exae.memorialapp.viewmodel.MemorialModel
import com.lxj.xpopup.XPopup
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
    private var articleId = -1

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
                ARouter.getInstance().build("/app/edit/article")
                    .withInt("memorialNo", memorialNo ?: -1)
                    .withInt("articleId", -1)
                    .withInt("type", 0)
                    .navigation(activity)
            }
        }

        viewModel.getArticleListResponse.observe(viewLifecycleOwner, Observer { resources ->
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

        listAdapter.loadMoreModule.setOnLoadMoreListener {
            requestNetData()
        }

        listAdapter.setOnItemClickListener { adapter, _, position ->
            val item = adapter.getItem(position) as ArticleListModel
            ARouter.getInstance().build("/app/edit/article")
                .withInt("memorialNo", memorialNo ?: -1)
                .withInt("articleId", item.ids)
                .withString("content", item.content)
                .withInt("type", 1)
                .navigation(activity)
        }

        listAdapter.setOnItemLongClickListener { adapter, _, position ->
            val data = adapter.getItem(position) as ArticleListModel
            XPopup.Builder(requireContext())
                .hasStatusBarShadow(false)
                .hasNavigationBar(false)
                .isDestroyOnDismiss(true)
                .isDarkTheme(true)
                .asConfirm("温馨提示", "确定要删除此文章吗？") {
                    deleteArticle(data.ids)
                }.show()
            true
        }

        viewModel.deleteArticleResponse.observe(this, Observer { resources ->
            handleResponse(resources, {
                ToastUtil.showCenter("操作成功")
                pageNum = 1
                requestNetData()
            },
                {
                    ToastUtil.showCenter("操作失败，请重试")
                }
            )
        })

    }

    private fun deleteArticle(article: Int) {
        viewModel.deleteArticleRequest(article)
    }

    override fun onResume() {
        super.onResume()
        pageNum = 1
        requestNetData()
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