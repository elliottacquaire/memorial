package com.exae.memorialapp.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.alibaba.android.arouter.launcher.ARouter
import com.exae.memorialapp.R
import com.exae.memorialapp.base.BaseFragment
import com.exae.memorialapp.databinding.FragmentMyProfileBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyProfileFragment : BaseFragment<FragmentMyProfileBinding>(), View.OnClickListener {


//    private var _binding: FragmentMyProfileBinding? = null
//    private val binding get() = _binding!!

//    override fun onCreateView(
//        inflater: LayoutInflater,
//        container: ViewGroup?,
//        savedInstanceState: Bundle?,
//    ): View? {
//        _binding = FragmentMyProfileBinding.inflate(inflater, container, false)
//        return binding.root
//    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        EventBus.getDefault().register(this)
        binding.accountMoney.setOnClickListener(this)
        binding.accountAdd.setOnClickListener(this)
        binding.hallManage.setOnClickListener(this)
        binding.helpCenter.setOnClickListener(this)
        binding.message.setOnClickListener(this)
        binding.mAvatar.setOnClickListener(this)
    }

    override fun onResume() {
        super.onResume()
//        viewModel.request()

    }

    override fun onDestroyView() {
        super.onDestroyView()
//        EventBus.getDefault().unregister(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.accountMoney -> {
                ARouter.getInstance().build("/pos/history/record")
                    .withInt("clickType", 1)  //
                    .navigation(activity)
            }
            R.id.accountAdd -> {
                ARouter.getInstance().build("/pos/history/record")
                    .withInt("clickType", 2) //
                    .navigation(activity)
            }
            R.id.hallManage -> {
                ARouter.getInstance().build("/app/manage/hall")
                    .navigation(activity)
            }
            R.id.helpCenter -> {
                ARouter.getInstance().build("/pos/history/record")
                    .withInt("clickType", 101) //
                    .navigation(activity)
            }
            R.id.message -> {
                ARouter.getInstance().build("/pos/drive/route")
                    .withInt("clickType", 101) //
                    .navigation(activity)
            }
            R.id.mAvatar -> {
                ARouter.getInstance().build("/app/modify/userinfo")
                    .withInt("clickType", 101) //
                    .navigation(activity)
            }
        }
    }

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentMyProfileBinding = FragmentMyProfileBinding.inflate(inflater, container, false)

}