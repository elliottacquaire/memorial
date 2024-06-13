package com.exae.memorialapp.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDialogFragment
import clickWithTrigger
import com.exae.memorialapp.databinding.DialogTestCancelBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CancelDialog(
    val name: String?,
    val deleteClick: () -> Unit = {},
    val modifyClick: (String) -> Unit = {}
) : AppCompatDialogFragment() {

    private var _binding: DialogTestCancelBinding? = null
    private val binding get() = _binding!!

    private var editName = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        editName = arguments?.getString("name", "") ?: ""
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DialogTestCancelBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.mMessage.setText(editName)
        binding.close.setOnClickListener {
            dismiss()
        }
        binding.delete.clickWithTrigger {
            dismiss()
            deleteClick()
        }

        binding.modify.clickWithTrigger {
            dismiss()
            modifyClick(binding.mMessage.text.toString().trim())
        }
    }

//    var block: (DialogFragment) -> Unit = {}
//    var blockModify: (DialogFragment) -> Unit = {}

    /*fun show(
        fragmentManager: FragmentManager,
        block: (DialogFragment) -> Unit,
        blockModify: (DialogFragment) -> Unit
    ) {
        this.block = block
        this.blockModify = blockModify
        show(fragmentManager, "test_cancel")
    }*/

    companion object {
        fun getInstants(
            editName: String?,
            deleteClick: () -> Unit = {},
            modifyClick: (String) -> Unit = {},
        ): CancelDialog {
            val fragment = CancelDialog(editName, deleteClick, modifyClick)
            val args = Bundle();
            args.putString("name", editName);
            fragment.arguments = args;
            return fragment;
        }
    }
}