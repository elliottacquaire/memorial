package com.exae.memorialapp.visit

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.alibaba.android.arouter.launcher.ARouter
import com.exae.memorialapp.R
import com.exae.memorialapp.base.CoreFragment
import com.exae.memorialapp.base.handleResponse
import com.exae.memorialapp.databinding.FragmentGoVisitBinding
import com.exae.memorialapp.eventbus.ClickEvent
import com.exae.memorialapp.utils.ToastUtil
import com.exae.memorialapp.viewmodel.MemorialModel
import com.lxj.xpopup.XPopup
import dagger.hilt.android.AndroidEntryPoint
import org.greenrobot.eventbus.EventBus

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [GoVisitFragment.newInstance] factory method to
 * create an instance of this fragment.
 */

@AndroidEntryPoint
class GoVisitFragment : CoreFragment(R.layout.fragment_go_visit) {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private var _binding: FragmentGoVisitBinding? = null
    private val binding get() = _binding!!
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
        _binding = FragmentGoVisitBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            tvCode.setSingleLine()
            butCreateOne.setOnClickListener {
                val code = tvCode.text.trim().toString()
                if (code.isNotEmpty()) {
                    viewModel.applyMemorialRequest(code, tvNotes.text.trim().toString())
                    showLoading()
                } else {
                    ToastUtil.showCenter("请输入邀请码")
                }
            }
        }
        initResponse()
    }

    private fun initResponse() {
        viewModel.applyMemorialResponse.observe(this, Observer { resources ->
            handleResponse(resources, {
                dismissLoading()
                if (it.data?.approved == true) {
                    confirmDialog(it.data.memorialNo ?: -1, it.data.memorialType)
                } else {
                    ToastUtil.showCenter("已申请，请等候结果")
                    EventBus.getDefault().post(ClickEvent())
                }
            },
                {
                    dismissLoading()
                }
            )
        })
    }

    private fun confirmDialog(memorialNo: Int, memorialType: String?) {
        XPopup.Builder(requireContext())
            .hasStatusBarShadow(false)
            .hasNavigationBar(false)
            .isDestroyOnDismiss(true)
            .isDarkTheme(true)
            .asConfirm("温馨提示", "您已申请成功，要去看看吗？") {
                when (memorialType) {
                    "0" -> {
                        ARouter.getInstance().build("/app/single/detail")
                            .withInt("memorialNo", memorialNo)
                            .navigation()
                    }

                    "1" -> {
                        ARouter.getInstance().build("/app/more/detail")
                            .withInt("memorialNo", memorialNo)
                            .navigation()
                    }

                    "2" -> {
                        ARouter.getInstance().build("/app/two/detail")
                            .withInt("memorialNo", memorialNo)
                            .navigation()
                    }

                    else -> ""
                }
            }.show()
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
         * @return A new instance of fragment GoVisitFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            GoVisitFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}