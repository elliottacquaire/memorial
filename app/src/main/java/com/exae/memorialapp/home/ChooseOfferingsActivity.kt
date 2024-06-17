package com.exae.memorialapp.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.lifecycle.Observer
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.alibaba.android.arouter.facade.annotation.Route
import com.exae.memorialapp.R
import com.exae.memorialapp.base.PosBaseActivity
import com.exae.memorialapp.base.handleResponse
import com.exae.memorialapp.databinding.ActivityChooseOfferingsBinding
import com.exae.memorialapp.home.worship.FireFragment
import com.exae.memorialapp.home.worship.OfferingFragment
import com.exae.memorialapp.home.worship.SaluteFragment
import com.exae.memorialapp.home.worship.SetMealFragment
import com.exae.memorialapp.viewmodel.MemorialModel
import com.google.android.material.tabs.TabLayout
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
@Route(path = "/app/choose/offer")
class ChooseOfferingsActivity : PosBaseActivity<ActivityChooseOfferingsBinding>() {
    private val viewModel: MemorialModel by viewModels()
    private var memorialNo = -1
    private var memorialName = ""
    private var memorialType = ""
    private var positionInt = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setToolTitle("祭品列表")
        setBackState(true)
        setSettingImage(false)
        memorialNo = intent.getIntExtra("memorialNo", -1)
        memorialName = intent.getStringExtra("memorialName") ?: ""
        memorialType = intent.getStringExtra("memorialType") ?: ""
        positionInt = intent.getIntExtra("positionInt", 0)

        initRequest()

        binding.apply {
            mMainTabLayout.addTab(
                mMainTabLayout.newTab().setCustomView(
                    getTabView(
                        "行礼",
                        R.drawable.tab_icon_home
                    )
                )
            )
            mMainTabLayout.addTab(
                mMainTabLayout.newTab().setCustomView(
                    getTabView(
                        "供品",
                        R.drawable.tab_book_drawable
                    )
                )
            )
            mMainTabLayout.addTab(
                mMainTabLayout.newTab().setCustomView(
                    getTabView(
                        "火供",
                        R.drawable.tab_icon_photo_drawable
                    )
                )
            )
            mMainTabLayout.addTab(
                mMainTabLayout.newTab().setCustomView(
                    getTabView(
                        "套餐",
                        R.drawable.tab_icon_photo_drawable
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
                override fun onPageScrollStateChanged(state: Int) {}

                override fun onPageScrolled(
                    position: Int,
                    positionOffset: Float,
                    positionOffsetPixels: Int
                ) {
                }

                override fun onPageSelected(position: Int) {
                    mMainTabLayout.selectTab(mMainTabLayout.getTabAt(position))
                    setToolTitle(mMainViewPager.adapter?.getPageTitle(position).toString())
                    setSettingImage(false)
                }
            })

            mMainTabLayout.selectTab(mMainTabLayout.getTabAt(positionInt))
        }
    }

    private fun initPageAdapter(): PagerAdapter {
        return object : FragmentStatePagerAdapter(
            supportFragmentManager,
            BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
        ) {
            override fun getItem(position: Int): Fragment {
                return when (position) {
                    0 -> SaluteFragment.newInstance(memorialNo, "")
                    1 -> OfferingFragment.newInstance(memorialNo, "")
                    2 -> FireFragment.newInstance(memorialNo, "")
                    3 -> SetMealFragment.newInstance(memorialNo, "")
                    else -> SaluteFragment.newInstance(memorialNo, "")
                }
            }

            override fun getCount(): Int {
                return 4
            }

            override fun getPageTitle(position: Int): CharSequence {
                return when (position) {
                    3 -> "套餐"
                    else -> "祭品列表"
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
        imageView.visibility = View.GONE
        return v
    }

    private fun initRequest() {
        viewModel.allMaterialOfferResponse.observe(this, Observer { resources ->
            handleResponse(resources, {
                val result = it.data
                binding.apply {
//                    Glide.with(baseContext)
//                        .load(result?.picUrlPrefix + result?.hallPicUrl)
//                        .into(binding.memorialPic)

//                    Glide.with(baseContext)
//                        .load(result?.picUrlPrefix + result?.avatarPicUrl)
//                        .placeholder(R.mipmap.headdd)
//                        .error(R.mipmap.headdd)
//                        .into(binding.headImg)
                }
            },
                {
                }
            )
        })
    }

    override fun getViewBinding(): ActivityChooseOfferingsBinding {
        return ActivityChooseOfferingsBinding.inflate(layoutInflater)
    }
}