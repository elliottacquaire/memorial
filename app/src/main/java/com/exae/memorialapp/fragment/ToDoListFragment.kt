package com.exae.memorialapp.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import com.alibaba.android.arouter.launcher.ARouter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.exae.memorialapp.base.BaseFragment
import com.exae.memorialapp.base.handleResponse
import com.exae.memorialapp.common.ShareUtil
import com.exae.memorialapp.databinding.FragmentIndexBinding
import com.exae.memorialapp.utils.ToastUtil
import com.exae.memorialapp.viewmodel.MemorialModel
import com.exae.memorialapp.viewmodel.PosLoginModel
import com.google.android.material.tabs.TabLayout
import com.orhanobut.logger.Logger
import com.youth.banner.adapter.BannerImageAdapter
import com.youth.banner.holder.BannerImageHolder
import com.youth.banner.indicator.CircleIndicator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint //1
class ToDoListFragment : BaseFragment<FragmentIndexBinding>() {

    //    private var _binding: FragmentIndexBinding? = null
//    private val binding get() = _binding!!
    private val viewModel: MemorialModel by viewModels()
//    override fun onCreateView(
//        inflater: LayoutInflater,
//        container: ViewGroup?,
//        savedInstanceState: Bundle?,
//    ): View? {
//        _binding = FragmentIndexBinding.inflate(inflater, container, false)
//        return binding.root
//    }


    val bannerList = mutableListOf<String>()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        bannerList.add("https://ss0.baidu.com/94o3dSag_xI4khGko9WTAnF6hhy/zhidao/pic/item/a6efce1b9d16fdfabf36882ab08f8c5495ee7b9f.jpg")
//        bannerList.add("https://ss3.baidu.com/9fo3dSag_xI4khGko9WTAnF6hhy/zhidao/pic/item/0824ab18972bd40797d8db1179899e510fb3093a.jpg")
//        bannerList.add("https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=2647888545,3751969263&fm=224&gp=0.jpg")
        viewModel.bannerRequest()

        viewModel.bannerResponse.observe(this, Observer { resources ->
            handleResponse(resources, {
//                val roles = it.data?.roles
//                if (!roles.isNullOrEmpty()) {
//                    userRole = Gson().toJson(roles)
//                }
//                longViewModel.setUserRole(userRole)
//                dismissLoading()
//                ToastUtil.showCenter("成功")
                it.data?.forEach { item ->
                    bannerList.clear()
                    bannerList.add(item.picUrl ?: "")
                }
                binding.banner.adapter.setDatas(bannerList)
//                viewModel.handleLoginResult(it.data)
//                ShareUtil.putToken("token-sssss")
//                ARouter.getInstance()
//                    .build("/app/main")
//                    .navigation()

//                finish()
//                judgeRole(userRole)
//                userPermissionJump()
            },
                {
//                    dismissLoading()
                }
            )
        })

        with(binding) {
            banner.setAdapter(object : BannerImageAdapter<String>(bannerList) {
                override fun onBindView(
                    holder: BannerImageHolder?,
                    data: String?,
                    position: Int,
                    size: Int
                ) {
                    if (holder != null) {
                        Glide.with(holder.itemView)
                            .load(bannerList[position])
                            .apply(RequestOptions.bitmapTransform(RoundedCorners(30)))
                            .into(holder.imageView)
                    }
                }
            })
                .addBannerLifecycleObserver(requireActivity())
                .indicator = CircleIndicator(requireContext())

            mViewPager.adapter = initPageAdapter()
            mViewPager.offscreenPageLimit = 2
            mTabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                override fun onTabReselected(tab: TabLayout.Tab?) {
                }

                override fun onTabUnselected(tab: TabLayout.Tab?) {
                }

                override fun onTabSelected(tab: TabLayout.Tab?) {
                    mViewPager.currentItem = mTabLayout.selectedTabPosition
                }

            })
            mViewPager.addOnPageChangeListener(object : OnPageChangeListener {
                override fun onPageScrollStateChanged(state: Int) {
                }

                override fun onPageScrolled(
                    position: Int,
                    positionOffset: Float,
                    positionOffsetPixels: Int
                ) {
                }

                override fun onPageSelected(position: Int) {
                    mTabLayout.selectTab(mTabLayout.getTabAt(position))
                }
            })

            mTabLayout.selectTab(mTabLayout.getTabAt(0))
        }

    }

    private fun initPageAdapter(): PagerAdapter {
        return object :
            FragmentStatePagerAdapter(childFragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
            override fun getItem(position: Int): Fragment {
                return MessageFragment().apply {
//                        arguments = Bundle().apply {
//                            putInt("flowType",productFlowType(position))
//                            putInt("isHistory", HISTORY_ZERO)
//                            putInt("currentStepStatus",FLOW_STATUS_IN_PROCESS)
//                        }
                }

            }

            override fun getCount(): Int {
                return 4
            }
        }
    }

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentIndexBinding {
        return FragmentIndexBinding.inflate(inflater, container, false)
    }

    override fun onStart() {
        super.onStart()
        binding.banner.start()
        Logger.i("onStart")
    }

    override fun onStop() {
        super.onStop()
        binding.banner.stop()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.banner.destroy()
    }
}