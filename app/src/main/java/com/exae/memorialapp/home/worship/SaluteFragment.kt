package com.exae.memorialapp.home.worship

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.chad.library.adapter.base.BaseQuickAdapter
import com.exae.memorialapp.R
import com.exae.memorialapp.adapter.LongLightStyleAdapter
import com.exae.memorialapp.adapter.SaluteItemAdapter
import com.exae.memorialapp.adapter.SaluteStyleAdapter
import com.exae.memorialapp.base.CoreFragment
import com.exae.memorialapp.base.handleResponse
import com.exae.memorialapp.bean.AllMaterialOfferItemModel
import com.exae.memorialapp.bean.CONTENT_MIDDLE
import com.exae.memorialapp.bean.SaluteMultData
import com.exae.memorialapp.bean.TITLE_TITLE
import com.exae.memorialapp.databinding.FragmentIntroduceBinding
import com.exae.memorialapp.databinding.FragmentSaluteBinding
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
 * Use the [SaluteFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint
class SaluteFragment : CoreFragment(R.layout.fragment_salute) {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var memorialNo: Int? = -1

    private var _binding: FragmentSaluteBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MemorialModel by viewModels()

    @Inject
    lateinit var listAdapter: SaluteItemAdapter

    private val dataList = ArrayList<SaluteMultData>()

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
        _binding = FragmentSaluteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getAllMaterialOfferRequest("2")
        binding.apply {
            mListView.layoutManager = LinearLayoutManager(requireContext())
            mListView.adapter = listAdapter
        }

        viewModel.allMaterialOfferResponse.observe(viewLifecycleOwner, Observer { resources ->
            handleResponse(resources) {
                if (!it.data.isNullOrEmpty()) {
                    dataList.clear()
                    /*for (item in it.data) {
                        val dataParent = SaluteMultData(TITLE_TITLE, item.name, null, item.children)
                        dataList.add(dataParent)
                        Log.i("sss","-----${dataParent.all.size}---------")
                        if (item.children.size > 0){
                            for (data in item.children){
                                val dataChildren = SaluteMultData(CONTENT_MIDDLE,item.name,data)
                                dataList.add(dataChildren)
                            }
                        }
                    }*/

                    listAdapter.setNewInstance(it.data)
                    listAdapter.setAnimationWithDefault(BaseQuickAdapter.AnimationType.SlideInBottom)
//                    binding.mListView.visibility = View.VISIBLE
                } else {
//                    binding.mListView.visibility = View.GONE
                }
            }
        })

        listAdapter.initClick(object: AdapterItemClick{
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
         * @return A new instance of fragment SaluteFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(memorialNo: Int, param2: String) =
            SaluteFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_PARAM1, memorialNo)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}