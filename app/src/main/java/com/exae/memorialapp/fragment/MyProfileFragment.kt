package com.exae.memorialapp.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.alibaba.android.arouter.launcher.ARouter
import com.exae.memorialapp.R
import com.exae.memorialapp.base.BaseFragment
import com.exae.memorialapp.base.CoreFragment
import com.exae.memorialapp.databinding.FragmentMyProfileBinding
import com.exae.memorialapp.databinding.FragmentTestDriveBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyProfileFragment : BaseFragment<FragmentMyProfileBinding>() ,View.OnClickListener{


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
        binding.apply {
//            myApprovalPrice.setOnClickListener(context)
//            myApprovalContract.setOnClickListener(this)
//            linPersistence.setOnClickListener(this)
//            linTestDrive.setOnClickListener(this)
//            linTestRecord.setOnClickListener(this)
        }

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
            R.id.myApprovalPrice -> {
                ARouter.getInstance().build("/pos/history/record")
                    .withInt("clickType", 1)  //报价单
                    .navigation(activity)
            }
            R.id.myApprovalContract -> {
                ARouter.getInstance().build("/pos/history/record")
                    .withInt("clickType", 2) //合同
                    .navigation(activity)
            }
            R.id.lin_persistence -> {
                ARouter.getInstance().build("/pos/history/record")
                    .withInt("clickType", 6) //暂留
                    .navigation(activity)
            }
            R.id.lin_test_drive -> {
                ARouter.getInstance().build("/pos/history/record")
                    .withInt("clickType",101) //试驾历史
                    .navigation(activity)
            }
            R.id.lin_test_record -> {
                ARouter.getInstance().build("/pos/drive/route")
                    .withInt("clickType",101) //试驾路线录制
                    .navigation(activity)
            }
        }
    }

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentMyProfileBinding = FragmentMyProfileBinding.inflate(inflater, container, false)

}