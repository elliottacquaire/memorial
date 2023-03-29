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
import com.alibaba.android.arouter.launcher.ARouter
import com.exae.memorialapp.base.PosBaseActivity
import com.exae.memorialapp.databinding.ActivityMainBinding
import com.exae.memorialapp.fragment.MyProfileFragment
import com.exae.memorialapp.fragment.VisitFragment
import com.exae.memorialapp.fragment.ToDoListFragment
import com.google.android.material.tabs.TabLayout
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint ////1
@Route(path = "/app/main")
class MainActivity : PosBaseActivity<ActivityMainBinding>() {
//    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        binding = ActivityMainBinding.inflate(layoutInflater)
//        setContentView(binding.root)
        setToolTitle(getString(R.string.title_name))
        setBackState(false)
        binding.apply {
            mMainTabLayout.addTab(
                mMainTabLayout.newTab().setCustomView(
                    getTabView(
                        getString(R.string.tab_to_do),
                        R.drawable.tab_icon_home
                    )
                )
            )
            mMainTabLayout.addTab(
                mMainTabLayout.newTab().setCustomView(
                    getTabView(
                        getString(R.string.tab_drive),
                        R.drawable.tab_icon_message_drawable
                    )
                )
            )
            mMainTabLayout.addTab(
                mMainTabLayout.newTab().setCustomView(
                    getTabView(
                        getString(R.string.tab_my_profile),
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
        }

    }

    override fun rightImageClick() {
        super.rightImageClick()
        ARouter.getInstance().build("/pos/setting").navigation(this)
    }

    private fun initPageAdapter(): PagerAdapter {
        return object : FragmentStatePagerAdapter(
            supportFragmentManager,
            BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
        ) {
            override fun getItem(position: Int): Fragment {
                return when (position) {
                    0 -> ToDoListFragment()
                    1 -> VisitFragment().apply {
//                        arguments = Bundle().apply {
//                            putInt("isHistory", HISTORY_ZERO)
//                        }
                    }
                    else -> MyProfileFragment()
                }
            }

            override fun getCount(): Int {
                return 3
            }

            override fun getPageTitle(position: Int): CharSequence {
                return when (position) {
                    0 -> resources.getString(R.string.tab_to_do)
                    1 -> resources.getString(R.string.title_to_test_drive)
                    else -> ""
                }

            }

            override fun finishUpdate(container: ViewGroup) {
                try {
                    super.finishUpdate(container)
                }catch (e : NullPointerException){}
            }
        }
    }

    // Tab自定义view
    private fun getTabView(title: String?, image_src: Int): View? {
        val v: View =
            LayoutInflater.from(this).inflate(R.layout.tab_item_view, null)
        val textView = v.findViewById(R.id.tv_title) as TextView
        textView.text = title?:""
        val imageView: ImageView = v.findViewById(R.id.img_view) as ImageView
        imageView.setImageResource(image_src)
        return v
    }

    override fun getViewBinding(): ActivityMainBinding {
        return ActivityMainBinding.inflate(layoutInflater)
    }
}