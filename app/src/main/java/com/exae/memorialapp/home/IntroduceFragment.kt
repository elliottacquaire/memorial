package com.exae.memorialapp.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.alibaba.android.arouter.launcher.ARouter
import com.bumptech.glide.Glide
import com.ddz.floatingactionbutton.FloatingActionMenu
import com.exae.memorialapp.R
import com.exae.memorialapp.base.CoreFragment
import com.exae.memorialapp.base.handleResponse
import com.exae.memorialapp.databinding.FragmentIntroduceBinding
import com.exae.memorialapp.utils.ToastUtil
import com.exae.memorialapp.viewmodel.MemorialModel
import com.lxj.xpopup.XPopup
import dagger.hilt.android.AndroidEntryPoint

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [IntroduceFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint
class IntroduceFragment : CoreFragment(R.layout.fragment_introduce) {
    // TODO: Rename and change types of parameters
    private var memorialNo: Int? = -1
    private var param2: String? = null
    private var introduceId = -1
    private var introduceText: String? = ""
    private var memorialType: String? = null

    private var _binding: FragmentIntroduceBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MemorialModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            memorialNo = it.getInt(ARG_PARAM1)
            memorialType = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentIntroduceBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        when (memorialType) {
            "1" -> {}
            else -> {}
        }
        binding.fabMenu.setOnFloatingActionsMenuUpdateListener(object :
            FloatingActionMenu.OnFloatingActionsMenuUpdateListener {

            override fun onMenuExpanded() {}

            override fun onMenuCollapsed() {}

        })

//        binding.add.setOnClickListener {
//            ARouter.getInstance().build("/app/edit/introduce")
//                .withInt("memorialNo", memorialNo ?: -1)
//                .withInt("introduceId", introduceId)
//                .withString("introduceText",introduceText)
//                .withInt("type", 0)
//                .navigation(activity)
//            binding.fabMenu.collapse()
//        }

        binding.modify.setOnClickListener {
            ARouter.getInstance().build("/app/edit/introduce")
                .withInt("memorialNo", memorialNo ?: -1)
                .withInt("introduceId", introduceId)
                .withString("introduceText", introduceText)
//                .withInt("type", 1)
                .navigation(activity)
            binding.fabMenu.collapse()
        }

        binding.delete.setOnClickListener {
            binding.fabMenu.collapse()
            deleteDialog()
        }

        viewModel.getMemorialIntroduceResponse.observe(viewLifecycleOwner, Observer { resources ->
            handleResponse(resources, {
                val result = it.data
                binding.apply {
                    introduceId = result?.ids ?: -1
                    introduce.text = result?.introduction ?: ""
                    introduceText = result?.introduction ?: ""

                    if (result?.introduction.isNullOrBlank()) {
                        emptyView.visibility = View.VISIBLE
                    } else {
                        emptyView.visibility = View.GONE
                    }

                }
            },
                {
                }
            )
        })

        viewModel.deleteMemorialIntroduceResponse.observe(
            viewLifecycleOwner,
            Observer { resources ->
                handleResponse(resources, {
                    val result = it.data
                    viewModel.getMemorialIntroduceRequest(memorialNo ?: -1)
                    ToastUtil.showCenter(it.message)
                },
                    {
                    }
                )
            })

    }

    override fun onResume() {
        super.onResume()
        viewModel.getMemorialIntroduceRequest(memorialNo ?: -1)
    }

    private fun deleteIntroduce() {
        viewModel.deleteMemorialIntroduceRequest(introduceId)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun deleteDialog() {
        XPopup.Builder(context)
            .hasStatusBarShadow(false)
            .hasNavigationBar(false)
            .isDestroyOnDismiss(true)
            .isDarkTheme(true)
            .asConfirm("温馨提示", "确定删除生平描述吗？") {
                deleteIntroduce()
            }.show()
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment IntroduceFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(memorialNo: Int, memorialType: String) =
            IntroduceFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_PARAM1, memorialNo)
                    putString(ARG_PARAM2, memorialType)
                }
            }
    }
}