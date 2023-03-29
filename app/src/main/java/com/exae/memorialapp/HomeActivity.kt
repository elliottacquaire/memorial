package com.exae.memorialapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.alibaba.android.arouter.facade.annotation.Route
import com.exae.memorialapp.base.PosBaseActivity
import com.exae.memorialapp.databinding.ActivityHomeBinding
import com.exae.memorialapp.home.CommentFragment
import com.exae.memorialapp.home.HomeFragment
import com.exae.memorialapp.home.IntroduceFragment
import com.exae.memorialapp.home.MemorialFragment
import com.exae.memorialapp.home.WorshipFragment
import com.google.android.material.tabs.TabLayout
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
@Route(path = "/app/home")
class HomeActivity : PosBaseActivity<ActivityHomeBinding>() {
    private var memorialNo = -1
    private var memorialName = ""
    private var memorialType = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        memorialNo = intent.getIntExtra("memorialNo", -1)
        memorialName = intent.getStringExtra("memorialName") ?: ""
        memorialType = intent.getStringExtra("memorialType") ?: ""
        setToolTitle(memorialName)
        setBackState(true)
        binding.apply {
            mMainTabLayout.addTab(
                mMainTabLayout.newTab().setCustomView(
                    getTabView(
                        getString(R.string.tab_to_home),
                        R.drawable.tab_icon_home
                    )
                )
            )
            mMainTabLayout.addTab(
                mMainTabLayout.newTab().setCustomView(
                    getTabView(
                        getString(R.string.tab_introduce),
                        R.drawable.tab_icon_message_drawable
                    )
                )
            )
            mMainTabLayout.addTab(
                mMainTabLayout.newTab().setCustomView(
                    getTabView(
                        getString(R.string.tab_memorial),
                        R.drawable.tab_icon_message_drawable
                    )
                )
            )
            mMainTabLayout.addTab(
                mMainTabLayout.newTab().setCustomView(
                    getTabView(
                        getString(R.string.tab_to_comment),
                        R.drawable.tab_icon_message_drawable
                    )
                )
            )
            mMainTabLayout.addTab(
                mMainTabLayout.newTab().setCustomView(
                    getTabView(
                        getString(R.string.tab_to_worship),
                        R.drawable.tab_icon_profile_drawable
                    )
                )
            )

            mMainViewPager.adapter = initPageAdapter()
            mMainViewPager.offscreenPageLimit = 2

            mMainTabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                override fun onTabReselected(tab: TabLayout.Tab?) {

                }

                override fun onTabUnselected(tab: TabLayout.Tab?) {
                }

                override fun onTabSelected(tab: TabLayout.Tab?) {
                    mMainViewPager.currentItem = mMainTabLayout.selectedTabPosition
                }

            })

            mMainViewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
                override fun onPageScrollStateChanged(state: Int) {
                }

                override fun onPageScrolled(
                    position: Int,
                    positionOffset: Float,
                    positionOffsetPixels: Int
                ) {
                }

                override fun onPageSelected(position: Int) {
                    mMainTabLayout.selectTab(mMainTabLayout.getTabAt(position))
                    setToolTitle(mMainViewPager.adapter?.getPageTitle(position).toString())
                    if (position == 2) {
                        setSettingImage(true)
                    } else {
                        setSettingImage(false)
                    }
                }
            })

            mMainTabLayout.selectTab(mMainTabLayout.getTabAt(0))
        }
    }

    private fun initPageAdapter(): PagerAdapter {
        return object : FragmentStatePagerAdapter(
            supportFragmentManager,
            BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
        ) {
            override fun getItem(position: Int): Fragment {
                return when (position) {
                    0 -> HomeFragment.newInstance(memorialNo, "")
                    1 -> IntroduceFragment().apply {
//                        arguments = Bundle().apply {
//                            putInt("isHistory", HISTORY_ZERO)
//                        }
                    }
                    2 -> MemorialFragment()
                    3 -> CommentFragment()
                    else -> WorshipFragment()
                }
            }

            override fun getCount(): Int {
                return 5
            }

            override fun getPageTitle(position: Int): CharSequence {
                return when (position) {
                    0 -> memorialName
                    1 -> memorialName
                    2 -> memorialName
                    3 -> "留言"
                    4 -> memorialName
                    else -> ""
                }

            }

            override fun finishUpdate(container: ViewGroup) {
                try {
                    super.finishUpdate(container)
                } catch (e: NullPointerException) {
                }
            }
        }
    }

    private fun getTabView(title: String?, image_src: Int): View? {
        val v: View =
            LayoutInflater.from(this).inflate(R.layout.tab_item_view, null)
        val textView = v.findViewById(R.id.tv_title) as TextView
        textView.text = title ?: ""
        val imageView: ImageView = v.findViewById(R.id.img_view) as ImageView
        imageView.setImageResource(image_src)
        return v
    }

    override fun getViewBinding(): ActivityHomeBinding {
        return ActivityHomeBinding.inflate(layoutInflater)
    }
}