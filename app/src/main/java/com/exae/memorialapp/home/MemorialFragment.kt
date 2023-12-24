package com.exae.memorialapp.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.exae.memorialapp.R
import com.exae.memorialapp.base.CoreFragment
import com.exae.memorialapp.databinding.FragmentMemorialBinding
import com.exae.memorialapp.fragment.MessageFragment
import com.exae.memorialapp.home.artical.AlbumFragment
import com.exae.memorialapp.home.artical.ArticalListFragment
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "memorialNo"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [MemorialFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint
class MemorialFragment : CoreFragment(R.layout.fragment_memorial) {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private var memorialNo: Int? = -1

    private var _binding: FragmentMemorialBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            memorialNo = it.getInt(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentMemorialBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            mViewPager.adapter = initPageAdapter()
            mViewPager.offscreenPageLimit = 2
            for (i in 0..1) {
                mTabLayout.addTab(mTabLayout.newTab())
            }
            mTabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                override fun onTabReselected(tab: TabLayout.Tab?) {
                }

                override fun onTabUnselected(tab: TabLayout.Tab?) {
                }

                override fun onTabSelected(tab: TabLayout.Tab?) {
                    mViewPager.currentItem = mTabLayout.selectedTabPosition
                }

            })
//            mViewPager.addPageChangeListener(object : ViewPager.OnPageChangeListener {
//                override fun onPageScrollStateChanged(state: Int) {
//                }
//
//                override fun onPageScrolled(
//                    position: Int,
//                    positionOffset: Float,
//                    positionOffsetPixels: Int
//                ) {
//                }
//
//                override fun onPageSelected(position: Int) {
//                    mTabLayout.selectTab(mTabLayout.getTabAt(position))
//                }
//            })

            mTabLayout.selectTab(mTabLayout.getTabAt(0))
            val tabMediator =
                TabLayoutMediator(mTabLayout, mViewPager) { tab: TabLayout.Tab, position: Int ->
                    tab.text = when (position) {
                        0 -> "相册"
                        1 -> "文选"
                        else -> ""
                    }
                }
            tabMediator.attach()
        }
    }

    private fun initPageAdapter(): FragmentStateAdapter {
        return object : FragmentStateAdapter(childFragmentManager, lifecycle) {
            override fun getItemCount(): Int {
                return 2
            }

            override fun createFragment(position: Int): Fragment {
                return when (position) {
                    0 -> AlbumFragment.newInstance(memorialNo ?: -1, "")
                    1 -> ArticalListFragment.newInstance(memorialNo ?: -1, "")
                    else -> MessageFragment()
                }
            }
        }
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
         * @return A new instance of fragment MemorialFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(memorialNo: Int, param2: String) =
            MemorialFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_PARAM1, memorialNo)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}