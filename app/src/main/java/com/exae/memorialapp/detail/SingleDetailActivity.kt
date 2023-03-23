package com.exae.memorialapp.detail

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.exae.memorialapp.R
import com.exae.memorialapp.base.PosBaseActivity
import com.exae.memorialapp.base.handleResponse
import com.exae.memorialapp.databinding.ActivitySingleDetailBinding
import com.exae.memorialapp.requestData.SexType
import com.exae.memorialapp.requestData.SingleMemorialRequest
import com.exae.memorialapp.requestData.requestCodeHallStyle
import com.exae.memorialapp.requestData.requestCodeHallStyleDouble
import com.exae.memorialapp.requestData.requestCodeHallStyleOne
import com.exae.memorialapp.requestData.requestCodeMemorialStyle
import com.exae.memorialapp.requestData.requestCodeMemorialStyleDouble
import com.exae.memorialapp.requestData.requestCodeMemorialStyleOne
import com.exae.memorialapp.requestData.requestCodeTableStyle
import com.exae.memorialapp.requestData.requestCodeTableStyleDouble
import com.exae.memorialapp.requestData.requestCodeTableStyleDouble1
import com.exae.memorialapp.requestData.requestCodeTableStyleOne
import com.exae.memorialapp.utils.CommonUtils
import com.exae.memorialapp.viewmodel.MemorialModel
import com.loper7.date_time_picker.DateTimePicker
import com.loper7.date_time_picker.dialog.CardDatePickerDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
@Route(path = "/app/single/detail")
class SingleDetailActivity : PosBaseActivity<ActivitySingleDetailBinding>() {

//    @JvmField
//    @Autowired(name = "memorialNo")
//    var memorialNo = "ss"

    private var memorialNo = -1
    private val viewModel: MemorialModel by viewModels()
    private var requestOne = SingleMemorialRequest()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        ARouter.getInstance().inject(this)
        setToolTitle("大厅风格选择")
        setBackState(true)
        memorialNo = intent.getIntExtra("memorialNo", -1)
        viewModel.getSingleMemorialDetailRequest(memorialNo)
        showLoading()

        viewModel.singleMemorialDetailResponse.observe(this, Observer { resources ->
            handleResponse(resources, {
                val result = it.data
                dismissLoading()
                binding.apply {
                    tvBrithData.text = CommonUtils.getSplitTime(result?.birthDate ?: "")
                    tvDeathData.text = CommonUtils.getSplitTime(result?.leaveDate ?: "")
                    edtPersonName.setText(result?.name ?: "")
                    tvMemorialStyle.text = result?.ememorialName
                    tvHallStyle.text = result?.hallName
                    tvTableStyle.text = result?.tabletName
                    when(result?.sex){
                        "男" -> man.isChecked = true
                        "女" -> woman.isChecked = true
                        else -> secret.isChecked = true
                    }
                    tvNation.text = result?.nation ?: ""
                    tvEpitaph.text = result?.epitaph ?: ""
                }
            },
                {
                    dismissLoading()
                }
            )
        })

        initOneCreate()
    }

    private fun initOneCreate() {
        binding.radioGroup.setOnCheckedChangeListener { _, id ->
            when (id) {
                R.id.man -> {
                    requestOne.sex = "男"
                }
                R.id.woman -> {
                    requestOne.sex = "女"
                }
                R.id.secret -> {
                    requestOne.sex = "保密"
                }
            }
        }

        binding.tvBrithData.setOnClickListener {
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
                        binding.tvBrithData.text = CommonUtils.getTime(millisecond)
                    }
                }).build().show()
        }

        binding.tvDeathData.setOnClickListener {
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
                        binding.tvDeathData.text = CommonUtils.getTime(millisecond)
                    }

                }).build().show()
        }
        binding.tvMemorialStyle.setOnClickListener {
            ARouter.getInstance().build("/app/choose/memorial")
                .navigation(this, requestCodeMemorialStyleOne)
        }
        binding.tvHallStyle.setOnClickListener {
            ARouter.getInstance().build("/app/choose/hall")
                .navigation(this, requestCodeHallStyleOne)
        }
        binding.tvTableStyle.setOnClickListener {
            ARouter.getInstance().build("/app/choose/table")
                .navigation(this, requestCodeTableStyleOne)
        }
        binding.butCreateOne.setOnClickListener {
            requestOne.name = binding.edtPersonName.text.trim().toString()
            requestOne.birthDate = binding.tvBrithData.text.trim().toString()
            requestOne.leaveDate = binding.tvDeathData.text.trim().toString()
            requestOne.epitaph = binding.tvEpitaph.text.trim().toString()
            requestOne.nation = binding.tvNation.text.trim().toString()
            requestOne.relationship = binding.tvRelation.text.trim().toString()
            viewModel.singleMemorialModifyRequest(requestOne)
            showLoading()
        }

        viewModel.singleMemorialModifyResponse.observe(this, Observer { resources ->
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
        if (requestCode == requestCodeMemorialStyleOne && resultCode == 1) {
            val name = data?.getStringExtra("name") ?: ""
            val ids = data?.getIntExtra("ids", -1) ?: -1
            binding.tvMemorialStyle.text = name
            requestOne.ememorialId = ids
        } else if (requestCode == requestCodeHallStyleOne && resultCode == 1) {
            val name = data?.getStringExtra("name") ?: ""
            val ids = data?.getIntExtra("ids", -1) ?: -1
            binding.tvHallStyle.text = name
            requestOne.hallId = ids
        } else if (requestCode == requestCodeTableStyleOne && resultCode == 1) {
            val name = data?.getStringExtra("name") ?: ""
            val ids = data?.getIntExtra("ids", -1) ?: -1
            binding.tvTableStyle.text = name
            requestOne.tabletId = ids
        }
    }

    override fun getViewBinding(): ActivitySingleDetailBinding {
        return ActivitySingleDetailBinding.inflate(layoutInflater)
    }
}