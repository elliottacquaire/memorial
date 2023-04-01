package com.exae.memorialapp.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.exae.memorialapp.R
import com.exae.memorialapp.base.CoreFragment
import com.exae.memorialapp.databinding.FragmentTestDriveBinding
import com.exae.memorialapp.home.CommentFragment
import com.exae.memorialapp.home.HomeFragment
import com.exae.memorialapp.home.IntroduceFragment
import com.exae.memorialapp.home.MemorialFragment
import com.exae.memorialapp.home.WorshipFragment
import com.exae.memorialapp.viewmodel.PosTestDriveModel
import com.exae.memorialapp.visit.AgreeHistoryFragment
import com.exae.memorialapp.visit.GoVisitFragment
import com.exae.memorialapp.visit.VisitHistoryFragment
import com.google.android.material.tabs.TabLayout
import com.scwang.smart.refresh.header.BezierRadarHeader
import com.youth.banner.adapter.BannerImageAdapter
import com.youth.banner.holder.BannerImageHolder
import com.youth.banner.indicator.CircleIndicator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class VisitFragment : CoreFragment(R.layout.fragment_test_drive) {

    private val viewModel: PosTestDriveModel by viewModels()

    private var _binding: FragmentTestDriveBinding? = null
    private val binding get() = _binding!!

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

        with(binding) {
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
            mViewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
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
                return when (position) {
                    0 -> GoVisitFragment.newInstance("memorialNo", "")
                    1 -> VisitHistoryFragment.newInstance("memorialNo", "")
                    2 -> AgreeHistoryFragment.newInstance("memorialNo", "")
                    else -> MessageFragment()
                }
            }

            override fun getCount(): Int {
                return 3
            }
        }
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