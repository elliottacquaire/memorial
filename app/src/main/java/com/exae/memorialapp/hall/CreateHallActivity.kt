package com.exae.memorialapp.hall

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.chad.library.adapter.base.BaseQuickAdapter
import com.exae.memorialapp.R
import com.exae.memorialapp.base.PosBaseActivity
import com.exae.memorialapp.base.handleResponse
import com.exae.memorialapp.databinding.ActivityCreateHallBinding
import com.exae.memorialapp.requestData.HallType
import com.exae.memorialapp.requestData.*
import com.exae.memorialapp.utils.CommonUtils.getTime
import com.exae.memorialapp.viewmodel.MemorialModel
import com.loper7.date_time_picker.DateTimePicker
import com.loper7.date_time_picker.dialog.CardDatePickerDialog
import com.luck.picture.lib.utils.ToastUtils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
@Route(path = "/app/create/hall")
class CreateHallActivity : PosBaseActivity<ActivityCreateHallBinding>() {

    private val viewModel: MemorialModel by viewModels()
    private var chooseType = HallType.ONE_HALL.type
    private val requestOne = SingleMemorialRequest()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setToolTitle("快速建馆")
        setBackState(true)
        binding.radioGroup.setOnCheckedChangeListener { _, id ->
            when (id) {
                R.id.oneHall -> {
                    chooseType = HallType.ONE_HALL.type
                    binding.layoutMoreView.viewMore.visibility = View.GONE
                    binding.layoutOneView.viewOne.visibility = View.VISIBLE
                    binding.layoutTwoView.viewTwo.visibility = View.GONE
                }
                R.id.moreHall -> {
                    chooseType = HallType.MORE_HALL.type
                    binding.layoutMoreView.viewMore.visibility = View.VISIBLE
                    binding.layoutOneView.viewOne.visibility = View.GONE
                    binding.layoutTwoView.viewTwo.visibility = View.GONE
                }
                R.id.twoHall -> {
                    chooseType = HallType.TWO_HALL.type
                    binding.layoutMoreView.viewMore.visibility = View.GONE
                    binding.layoutOneView.viewOne.visibility = View.GONE
                    binding.layoutTwoView.viewTwo.visibility = View.VISIBLE
                }
            }
        }
        initOneCreate()
        initMoreCreate()
        initTwoCreate()
    }

    private fun initTwoCreate() {

    }

    private fun initMoreCreate() {
        binding.layoutMoreView.tvMemorialStyle.setOnClickListener {
            ARouter.getInstance().build("/app/choose/memorial")
                .navigation(this, requestCodeMemorialStyle)
        }
        binding.layoutMoreView.tvHallStyle.setOnClickListener {
            ARouter.getInstance().build("/app/choose/hall").navigation(this, requestCodeHallStyle)
        }
        binding.layoutMoreView.tvTableStyle.setOnClickListener {
            ARouter.getInstance().build("/app/choose/table").navigation(this, requestCodeTableStyle)
        }
        binding.layoutMoreView.butCreateOne.setOnClickListener {

        }
    }

    private fun initOneCreate() {
        binding.layoutOneView.tvBrithData.setOnClickListener {
            CardDatePickerDialog.builder(this).setTitle("请选择日期")
                .setLabelText("年", "月", "日")
                .setDisplayType(
                    mutableListOf(
                        DateTimePicker.YEAR,
                        DateTimePicker.MONTH,
                        DateTimePicker.DAY
                    )
                )
                .setOnChoose(listener = object : CardDatePickerDialog.OnChooseListener {
                    override fun onChoose(millisecond: Long) {
                        binding.layoutOneView.tvBrithData.text = getTime(millisecond)
                    }
                }).build().show()
        }

        binding.layoutOneView.tvDeathData.setOnClickListener {
            CardDatePickerDialog.builder(this).setTitle("请选择日期")
                .setLabelText("年", "月", "日")
                .setDisplayType(
                    mutableListOf(
                        DateTimePicker.YEAR,
                        DateTimePicker.MONTH,
                        DateTimePicker.DAY
                    )
                )
                .setOnChoose(listener = object : CardDatePickerDialog.OnChooseListener {
                    override fun onChoose(millisecond: Long) {
                        binding.layoutOneView.tvDeathData.text = getTime(millisecond)
                    }

                }).build().show()
        }
        binding.layoutOneView.tvMemorialStyle.setOnClickListener {
            ARouter.getInstance().build("/app/choose/memorial")
                .navigation(this, requestCodeMemorialStyleOne)
        }
        binding.layoutOneView.tvHallStyle.setOnClickListener {
            ARouter.getInstance().build("/app/choose/hall")
                .navigation(this, requestCodeHallStyleOne)
        }
        binding.layoutOneView.tvTableStyle.setOnClickListener {
            ARouter.getInstance().build("/app/choose/table")
                .navigation(this, requestCodeTableStyleOne)
        }
        binding.layoutOneView.butCreateOne.setOnClickListener {
            requestOne.name = binding.layoutOneView.edtPersonName.text.trim().toString()
            requestOne.birthDate = binding.layoutOneView.tvBrithData.text.trim().toString()
            requestOne.leaveDate = binding.layoutOneView.tvDeathData.text.trim().toString()
            viewModel.singleMemorialRequest(requestOne)
            showLoading()
        }

        viewModel.singleMemorialResponse.observe(this, Observer { resources ->
            handleResponse(resources, {

                dismissLoading()
            },
                {
                    dismissLoading()
                }
            )
        })

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == requestCodeMemorialStyle && resultCode == 1) {
            val name = data?.getStringExtra("name") ?: ""
            val ids = data?.getIntExtra("ids",-1) ?: -1
            binding.layoutMoreView.tvMemorialStyle.text = name
        } else if (requestCode == requestCodeHallStyle && resultCode == 1) {
            val name = data?.getStringExtra("name") ?: ""
            val ids = data?.getIntExtra("ids",-1) ?: -1
            binding.layoutMoreView.tvHallStyle.text = name
        } else if (requestCode == requestCodeTableStyle && resultCode == 1) {
            val name = data?.getStringExtra("name") ?: ""
            val ids = data?.getIntExtra("ids",-1) ?: -1
            binding.layoutMoreView.tvTableStyle.text = name
        } else if (requestCode == requestCodeMemorialStyleOne && resultCode == 1) {
            val name = data?.getStringExtra("name") ?: ""
            val ids = data?.getIntExtra("ids",-1) ?: -1
            binding.layoutOneView.tvMemorialStyle.text = name
            requestOne.ememorialId = ids
        } else if (requestCode == requestCodeHallStyleOne && resultCode == 1) {
            val name = data?.getStringExtra("name") ?: ""
            val ids = data?.getIntExtra("ids",-1) ?: -1
            binding.layoutOneView.tvHallStyle.text = name
            requestOne.hallId = ids
        } else if (requestCode == requestCodeTableStyleOne && resultCode == 1) {
            val name = data?.getStringExtra("name") ?: ""
            val ids = data?.getIntExtra("ids",-1) ?: -1
            binding.layoutOneView.tvTableStyle.text = name
            requestOne.tabletId = ids
        }
    }

    override fun getViewBinding(): ActivityCreateHallBinding {
        return ActivityCreateHallBinding.inflate(layoutInflater)
    }

}