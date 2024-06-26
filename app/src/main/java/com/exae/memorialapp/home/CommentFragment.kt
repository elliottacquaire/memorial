package com.exae.memorialapp.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.OnItemLongClickListener
import com.exae.memorialapp.R
import com.exae.memorialapp.adapter.MemorialCommentAdapter
import com.exae.memorialapp.animation.UserPreference
import com.exae.memorialapp.base.CoreFragment
import com.exae.memorialapp.base.handleResponse
import com.exae.memorialapp.bean.CommentListModel
import com.exae.memorialapp.databinding.FragmentCommentBinding
import com.exae.memorialapp.eventbus.AttentionEvent
import com.exae.memorialapp.utils.StringPreferenceType
import com.exae.memorialapp.utils.ToastUtil
import com.exae.memorialapp.view.CustomEditTextBottomPopup
import com.exae.memorialapp.viewmodel.MemorialModel
import com.lxj.xpopup.XPopup
import com.lxj.xpopup.core.BasePopupView
import com.lxj.xpopup.interfaces.SimpleCallback
import com.scwang.smart.refresh.header.BezierRadarHeader
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "memorialNo"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [CommentFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint
class CommentFragment : CoreFragment(R.layout.fragment_comment), OnItemLongClickListener {
    // TODO: Rename and change types of parameters
    private var memorialNo: Int? = -1
    private var param2: String? = null
    private var checkPosition = -1
    private var isEditable = false
    private var commentTips: String? = ""

    @Inject
    @UserPreference
    lateinit var users: StringPreferenceType

    private var _binding: FragmentCommentBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var listAdapter: MemorialCommentAdapter

    private val viewModel: MemorialModel by viewModels()
    private val list = ArrayList<CommentListModel>()
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
        EventBus.getDefault().register(this)
        _binding = FragmentCommentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requestNetData()
        list.clear()
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
                val textBottomPopup = CustomEditTextBottomPopup(requireContext())
                textBottomPopup.comment = commentTips
                val pop = XPopup.Builder(context)
                    .autoOpenSoftInput(true)
                    .autoFocusEditText(true)
                    .setPopupCallback(object : SimpleCallback() {
                        override fun onShow(popupView: BasePopupView?) {
                            super.onShow(popupView)
                        }

                        override fun onDismiss(popupView: BasePopupView?) {
                            super.onDismiss(popupView)
                            commentTips = textBottomPopup.comment
                            if (!commentTips.isNullOrBlank()) {
                                addComment(commentTips!!)
                            }
                        }
                    })
                    .asCustom(textBottomPopup)
                    .show()
            }
        }

        viewModel.getCommentListResponse.observe(viewLifecycleOwner, Observer { resources ->
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

        viewModel.addCommenResponse.observe(viewLifecycleOwner, Observer { resources ->
            handleResponse(resources, {
                val result = it.data
                pageNum = 1
                requestNetData()
                ToastUtil.showCenter("发表成功")
            },
                {
//                    ToastUtil.showCenter("留言发表失败，请重试")
                }
            )
        })

        viewModel.deleteCommenResponse.observe(viewLifecycleOwner, Observer { resources ->
            handleResponse(resources, {
                val result = it.data
                if (result == true) {
                    pageNum = 1
                    requestNetData()
                    ToastUtil.showCenter("删除成功")
                }
            },
                {
//                    ToastUtil.showCenter("留言删除失败，请重试")
                }
            )
        })

        listAdapter.setOnItemLongClickListener(this)
        listAdapter.loadMoreModule.setOnLoadMoreListener {
            requestNetData()
        }
    }

    private fun requestNetData() {
        if ((memorialNo ?: -1) == -1) return
        memorialNo?.let { viewModel.getCommentListRequest(it, pageNum) }
    }

    private fun addComment(content: String) {
        if ((memorialNo ?: -1) == -1) return
        memorialNo?.let { viewModel.addCommentRequest(it, content) }
    }

    private fun deleteComment(commentId: Int) {
        if ((memorialNo ?: -1) == -1) return
        memorialNo?.let { viewModel.deleteCommentRequest(it, commentId) }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        EventBus.getDefault().unregister(this)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun attentionStatus(event: AttentionEvent) {
        isEditable = event.edit
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment CommentFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(memorialNo: Int, param2: String) =
            CommentFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_PARAM1, memorialNo)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onItemLongClick(
        adapter: BaseQuickAdapter<*, *>,
        view: View,
        position: Int
    ): Boolean {
        val data = listAdapter.data.get(position)
//        if (!((users.get().trim() == data.createBy) || isEditable)) return true
        if ((users.get().trim() != data.createBy)) return true
        XPopup.Builder(requireContext())
            .hasStatusBarShadow(false)
            .hasNavigationBar(false)
            .isDestroyOnDismiss(true)
            .isDarkTheme(true)
            .asConfirm("温馨提示", "确定要删除此留言吗？") {
                deleteComment(data.ids)
                checkPosition = position
            }.show()

        return true
    }
}