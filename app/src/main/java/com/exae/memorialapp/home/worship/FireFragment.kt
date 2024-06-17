package com.exae.memorialapp.home.worship

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.chad.library.adapter.base.BaseQuickAdapter
import com.exae.memorialapp.R
import com.exae.memorialapp.adapter.SaluteItemAdapter
import com.exae.memorialapp.base.CoreFragment
import com.exae.memorialapp.base.handleResponse
import com.exae.memorialapp.bean.AllMaterialOfferItemModel
import com.exae.memorialapp.databinding.FragmentFireBinding
import com.exae.memorialapp.databinding.FragmentIntroduceBinding
import com.exae.memorialapp.dialog.AdapterItemClick
import com.exae.memorialapp.utils.ToastUtil
import com.exae.memorialapp.viewmodel.MemorialModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [FireFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint
class FireFragment : CoreFragment(R.layout.fragment_fire) {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var memorialNo: Int? = -1

    private var _binding: FragmentFireBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MemorialModel by viewModels()

    @Inject
    lateinit var listAdapter: SaluteItemAdapter

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
        _binding = FragmentFireBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getAllMaterialOfferRequest("4")
        binding.apply {
            mListView.layoutManager = LinearLayoutManager(requireContext())
            mListView.adapter = listAdapter
        }
        viewModel.allMaterialOfferResponse.observe(viewLifecycleOwner, Observer { resources ->
            handleResponse(resources) {
                if (!it.data.isNullOrEmpty()) {
                    listAdapter.setNewInstance(it.data)
                    listAdapter.setAnimationWithDefault(BaseQuickAdapter.AnimationType.SlideInBottom)
                }
            }
        })

        listAdapter.initClick(object : AdapterItemClick {
            override fun itemClick(data: AllMaterialOfferItemModel) {
                ToastUtil.showCenter(data.name)
            }
        })
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment FireFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(memorialNo: Int, param2: String) =
            FireFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_PARAM1, memorialNo)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}