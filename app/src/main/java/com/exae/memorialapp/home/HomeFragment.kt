package com.exae.memorialapp.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.exae.memorialapp.R
import com.exae.memorialapp.base.CoreFragment
import com.exae.memorialapp.base.handleResponse
import com.exae.memorialapp.databinding.FragmentHomeBinding
import com.exae.memorialapp.utils.CommonUtils
import com.exae.memorialapp.viewmodel.MemorialModel
import dagger.hilt.android.AndroidEntryPoint

private const val ARG_PARAM1 = "memorialNo"
private const val ARG_PARAM2 = "param2"

/**
 * home fragment
 */
@AndroidEntryPoint
class HomeFragment : CoreFragment(R.layout.fragment_home) {
    private var memorialNo: Int? = -1
    private var param2: String? = null

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MemorialModel by viewModels()

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
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getSingleMemorialDetailRequest(memorialNo ?: -1)

        viewModel.singleMemorialDetailResponse.observe(this, Observer { resources ->
            handleResponse(resources, {
                val result = it.data
                binding.apply {
                    if (!result?.birthDate.isNullOrEmpty() && !result?.leaveDate.isNullOrEmpty()) {
                        tvData.text = CommonUtils.getSplitTime(
                            result?.birthDate ?: ""
                        ) + " - " + CommonUtils.getSplitTime(result?.leaveDate ?: "")
                    }
                    tvName.text = result?.name ?: ""
                    tvEpitaph.text = result?.epitaph ?: ""
                    Glide.with(requireActivity())
                        .load(result?.picUrlPrefix + result?.memorialPicUrl)
                        .into(binding.memorialPic)

                    Glide.with(requireActivity())
                        .load(result?.picUrlPrefix + result?.avatarPicUrl)
                        .placeholder(R.mipmap.headdd)
                        .error(R.mipmap.headdd)
                        .into(binding.headImg)
                }
            },
                {
                }
            )
        })

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        @JvmStatic
        fun newInstance(memorialNo: Int, param2: String) =
            HomeFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_PARAM1, memorialNo)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}