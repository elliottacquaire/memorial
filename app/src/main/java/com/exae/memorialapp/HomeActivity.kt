package com.exae.memorialapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.exae.memorialapp.base.PosBaseActivity
import com.exae.memorialapp.base.handleResponse
import com.exae.memorialapp.databinding.ActivityHomeBinding
import com.exae.memorialapp.eventbus.AttentionEvent
import com.exae.memorialapp.home.CommentFragment
import com.exae.memorialapp.home.HomeFragment
import com.exae.memorialapp.home.IntroduceFragment
import com.exae.memorialapp.home.MemorialFragment
import com.exae.memorialapp.home.MoreFamilyFragment
import com.exae.memorialapp.home.TwoHallFragment
import com.exae.memorialapp.home.WorshipFragment
import com.exae.memorialapp.utils.EventBusManager
import com.exae.memorialapp.utils.ToastUtil
import com.exae.memorialapp.viewmodel.MemorialModel
import com.google.android.material.tabs.TabLayout
import dagger.hilt.android.AndroidEntryPoint
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

@AndroidEntryPoint
@Route(path = "/app/home")
class HomeActivity : PosBaseActivity<ActivityHomeBinding>() {
    private var memorialNo = -1
    private var memorialName = ""
    private var memorialType = ""
    private var positionInt = 0
    private var isAttention = false
    private val viewModel: MemorialModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        EventBus.getDefault().register(this)
        memorialNo = intent.getIntExtra("memorialNo", -1)
        memorialName = intent.getStringExtra("memorialName") ?: ""
        memorialType = intent.getStringExtra("memorialType") ?: ""
        positionInt = intent.getIntExtra("positionInt", 0)
//        isAttention = intent.getBooleanExtra("isAttention", false)

        setToolTitle(memorialName)
        setBackState(true)
        setSettingImage(true)

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
                        R.drawable.tab_book_drawable
                    )
                )
            )
            mMainTabLayout.addTab(
                mMainTabLayout.newTab().setCustomView(
                    getTabView(
                        getString(R.string.tab_memorial),
                        R.drawable.tab_icon_photo_drawable
                    )
                )
            )
            mMainTabLayout.addTab(
                mMainTabLayout.newTab().setCustomView(
                    getTabView(
                        getString(R.string.tab_to_comment),
                        R.drawable.tab_message_drawable
                    )
                )
            )
            mMainTabLayout.addTab(
                mMainTabLayout.newTab().setCustomView(
                    getTabView(
                        getString(R.string.tab_to_worship),
                        R.drawable.tab_icon_human_drawable
                    )
                )
            )

            mMainViewPager.adapter = initPageAdapter()
            mMainViewPager.offscreenPageLimit = 3
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
                    setSettingImage(false)
                    if (position == 0) {
                        setSettingImage(true)
                    } else {
                        setSettingImage(false)
                    }
                    if (position == 4) {
                        setRightTv(true)
                    } else {
                        setRightTv(false)
                    }
                }
            })

            mMainTabLayout.selectTab(mMainTabLayout.getTabAt(positionInt))
        }
        initResponse()
    }

    private fun initResponse() {
        viewModel.attentionResponse.observe(this, Observer { resources ->
            handleResponse(resources, {
                val result = it.data?.result
                setRightImg(R.drawable.icon_heart_select)
                ToastUtil.showCenter("已关注")
                isAttention = result ?: false
            },
                {
//                    ToastUtil.showCenter("关注失败，请重试")
                }
            )
        })

        viewModel.attentionCancelResponse.observe(this, Observer { resources ->
            handleResponse(resources, {
                val result = it.data
                setRightImg(R.drawable.icon_heart)
                ToastUtil.showCenter("已取消关注")
                isAttention = false
            },
                {
//                    ToastUtil.showCenter("取消关注失败，请重试")
                }
            )
        })
    }

    private fun initPageAdapter(): PagerAdapter {
        return object : FragmentStatePagerAdapter(
            supportFragmentManager,
            BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
        ) {
            override fun getItem(position: Int): Fragment {
                return when (position) {
                    0 -> {
                        when (memorialType) {
                            "0" -> HomeFragment.newInstance(memorialNo, "")
                            "1" -> MoreFamilyFragment.newInstance(memorialNo, "")
                            "2" -> TwoHallFragment.newInstance(memorialNo, "")
                            else -> {
                                HomeFragment.newInstance(memorialNo, "")
                            }
                        }
                    }

                    1 -> IntroduceFragment.newInstance(memorialNo, memorialType)
                    2 -> MemorialFragment.newInstance(memorialNo, "")
                    3 -> CommentFragment.newInstance(memorialNo, "")
                    else -> WorshipFragment.newInstance(memorialNo, memorialType)
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

    override fun rightImageClick() {
        super.rightImageClick()
        if (isAttention) {
            viewModel.attentionCancelRequest(memorialNo)
        } else {
            viewModel.attentionRequest(memorialNo)
        }
    }

    override fun rightTvClick() {
        super.rightTvClick()
        ARouter.getInstance().build("/app/worship/record")
            .withInt("memorialNo", memorialNo ?: -1)
            .navigation(this)
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

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun attentionStatus(event: AttentionEvent) {
        if (event.flag) {
            isAttention = true
            setRightImg(R.drawable.icon_heart_select)
        } else {
            setRightImg(R.drawable.icon_heart)
            isAttention = false
        }
    }
}