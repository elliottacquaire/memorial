package com.exae.memorialapp.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.exae.memorialapp.R
import com.exae.memorialapp.base.CoreFragment
import com.exae.memorialapp.databinding.FragmentTwoHallBinding
import com.exae.memorialapp.databinding.FragmentWorshipBinding
import dagger.hilt.android.AndroidEntryPoint

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [WorshipFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint
class WorshipFragment : CoreFragment(R.layout.fragment_worship) {
    // TODO: Rename and change types of parameters
    private var memorialNo: Int? = null
    private var param2: String? = null

    private var _binding: FragmentWorshipBinding? = null
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
        _binding = FragmentWorshipBinding.inflate(inflater, container, false)
        return binding.root
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
         * @return A new instance of fragment WorshipFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(memorialNo: Int, param2: String) =
            WorshipFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_PARAM1, memorialNo)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}