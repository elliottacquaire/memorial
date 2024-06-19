package com.exae.memorialapp.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.request.RequestOptions
import com.exae.memorialapp.R
import com.exae.memorialapp.base.CoreFragment
import com.exae.memorialapp.base.handleResponse
import com.exae.memorialapp.databinding.FragmentMoreFamilyBinding
import com.exae.memorialapp.databinding.FragmentTwoHallBinding
import com.exae.memorialapp.utils.CommonUtils
import com.exae.memorialapp.viewmodel.MemorialModel
import dagger.hilt.android.AndroidEntryPoint

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "memorialNo"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [TwoHallFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint
class TwoHallFragment : CoreFragment(R.layout.fragment_two_hall) {
    // TODO: Rename and change types of parameters
    private var memorialNo: Int? = -1
    private var param2: String? = null

    private var _binding: FragmentTwoHallBinding? = null
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
        _binding = FragmentTwoHallBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getTwoMemorialDetailRequest(memorialNo ?: -1)

        viewModel.twoMemorialDetailResponse.observe(viewLifecycleOwner, Observer { resources ->
            handleResponse(resources, {
                val result = it.data
                binding.apply {
                    if (!result?.birthDate1.isNullOrEmpty() && !result?.leaveDate1.isNullOrEmpty()) {
                        tvData0.text = CommonUtils.getSplitTime(
                            result?.birthDate1 ?: ""
                        ) + " - " + CommonUtils.getSplitTime(result?.leaveDate1 ?: "")
                    }
                    tvName0.text = result?.name1 ?: ""
                    tvSex0.text = when (result?.sex1) {
                        "0" -> {
                            "男"
                        }

                        "1" -> {
                            "女"
                        }

                        else -> {
                            "保密"
                        }
                    }

                    Glide.with(requireActivity())
                        .load(result?.picUrlPrefix + result?.avatarPicUrl1)
                        .apply(RequestOptions.bitmapTransform(CircleCrop()))
                        .placeholder(R.mipmap.headdd)
                        .error(R.mipmap.headdd)
                        .into(binding.headImg0)

                    Glide.with(requireActivity())
                        .load(result?.picUrlPrefix + result?.memorialPicUrl)
                        .into(binding.memorialPic)


                    if (!result?.birthDate2.isNullOrEmpty() && !result?.leaveDate2.isNullOrEmpty()) {
                        tvData1.text = CommonUtils.getSplitTime(
                            result?.birthDate2 ?: ""
                        ) + " - " + CommonUtils.getSplitTime(result?.leaveDate2 ?: "")
                    }
                    tvName1.text = result?.name2 ?: ""
                    tvSex1.text = when (result?.sex2) {
                        "0" -> {
                            "男"
                        }

                        "1" -> {
                            "女"
                        }

                        else -> {
                            "保密"
                        }
                    }
                    Glide.with(requireActivity())
                        .load(result?.picUrlPrefix + result?.avatarPicUrl2)
                        .apply(RequestOptions.bitmapTransform(CircleCrop()))
                        .placeholder(R.mipmap.headdd)
                        .error(R.mipmap.headdd)
                        .into(binding.headImg1)
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
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment TwoHallFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(memorialNo: Int, param2: String) =
            TwoHallFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_PARAM1, memorialNo)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}