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
import com.exae.memorialapp.utils.ToastUtil
import com.exae.memorialapp.viewmodel.MemorialModel
import com.loper7.date_time_picker.DateTimePicker
import com.loper7.date_time_picker.dialog.CardDatePickerDialog
import com.luck.picture.lib.utils.ToastUtils
import com.lxj.xpopup.XPopup
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
@Route(path = "/app/create/hall")
class CreateHallActivity : PosBaseActivity<ActivityCreateHallBinding>() {

    private val viewModel: MemorialModel by viewModels()
    private var chooseType = HallType.ONE_HALL.type
    private val requestOne = SingleMemorialRequest()
    private val requestMore = MoreMemorialRequest()
    private val requestDouble = DoubleMemorialRequest()
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
        binding.layoutTwoView.tvMemorialStyle.setOnClickListener {
            ARouter.getInstance().build("/app/choose/memorial")
                .withInt("clickType", chooseType)
                .navigation(this, requestCodeMemorialStyleDouble)
        }
        binding.layoutTwoView.tvHallStyle.setOnClickListener {
            ARouter.getInstance().build("/app/choose/hall")
                .withInt("clickType", chooseType)
                .navigation(this, requestCodeHallStyleDouble)
        }
        binding.layoutTwoView.tvTableStyle.setOnClickListener {
            ARouter.getInstance().build("/app/choose/table")
                .withInt("clickType", chooseType)
                .navigation(this, requestCodeTableStyleDouble)
        }
        binding.layoutTwoView.tvTableStyle1.setOnClickListener {
            ARouter.getInstance().build("/app/choose/table")
                .withInt("clickType", chooseType)
                .navigation(this, requestCodeTableStyleDouble1)
        }
        binding.layoutTwoView.butCreateOne.setOnClickListener {
            requestDouble.name = binding.layoutTwoView.edtMemorialName.text.trim().toString()
            requestDouble.relationship = binding.layoutTwoView.edtMember.text.trim().toString()
            requestDouble.description = binding.layoutTwoView.edtBirthInfo.text.trim().toString()

            requestDouble.name1 = binding.layoutTwoView.edtPersonName.text.trim().toString()
            requestDouble.name2 = binding.layoutTwoView.edtPersonName1.text.trim().toString()

            requestDouble.birthDate1 = binding.layoutTwoView.tvBrithData.text.trim().toString()
            requestDouble.birthDate2 = binding.layoutTwoView.tvBrithData1.text.trim().toString()
            requestDouble.leaveDate1 = binding.layoutTwoView.tvDeathData.text.trim().toString()
            requestDouble.leaveDate2 = binding.layoutTwoView.tvDeathData1.text.trim().toString()

            requestDouble.sex1 = binding.layoutTwoView.tvGender.text.trim().toString()
            requestDouble.sex2 = binding.layoutTwoView.tvGender1.text.trim().toString()

            viewModel.twoMemorialRequest(requestDouble)
            showLoading()
        }
        binding.layoutTwoView.tvGender.setOnClickListener {
            val pop = XPopup.Builder(this)
                .asBottomList("请选择一项", arrayOf("男", "女", "保密")) { position, text ->
                    when (position) {
                        0 -> binding.layoutTwoView.tvGender.text = "男"
                        1 -> binding.layoutTwoView.tvGender.text = "女"
                        2 -> binding.layoutTwoView.tvGender.text = "保密"
                    }
                }.show()
        }
        binding.layoutTwoView.tvGender1.setOnClickListener {
            val pop = XPopup.Builder(this)
                .asBottomList("请选择一项", arrayOf("男", "女", "保密")) { position, text ->
                    when (position) {
                        0 -> binding.layoutTwoView.tvGender1.text = "男"
                        1 -> binding.layoutTwoView.tvGender1.text = "女"
                        2 -> binding.layoutTwoView.tvGender1.text = "保密"
                    }
                }.show()
        }

        binding.layoutTwoView.tvBrithData.setOnClickListener {
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
                        binding.layoutTwoView.tvBrithData.text = getTime(millisecond)
                    }
                }).build().show()
        }

        binding.layoutTwoView.tvDeathData.setOnClickListener {
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
                        binding.layoutTwoView.tvDeathData.text = getTime(millisecond)
                    }

                }).build().show()
        }

        binding.layoutTwoView.tvBrithData1.setOnClickListener {
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
                        binding.layoutTwoView.tvBrithData1.text = getTime(millisecond)
                    }
                }).build().show()
        }

        binding.layoutTwoView.tvDeathData1.setOnClickListener {
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
                        binding.layoutTwoView.tvDeathData1.text = getTime(millisecond)
                    }

                }).build().show()
        }

        viewModel.twoMemorialResponse.observe(this, Observer { resources ->
            handleResponse(resources, {
                dismissLoading()
            },
                {
                    dismissLoading()
                }
            )
        })
    }

    private fun initMoreCreate() {
        binding.layoutMoreView.tvMemorialStyle.setOnClickListener {
            ARouter.getInstance().build("/app/choose/memorial")
                .withInt("clickType", chooseType)
                .navigation(this, requestCodeMemorialStyle)
        }
        binding.layoutMoreView.tvHallStyle.setOnClickListener {
            ARouter.getInstance().build("/app/choose/hall")
                .withInt("clickType", chooseType)
                .navigation(this, requestCodeHallStyle)
        }
        binding.layoutMoreView.tvTableStyle.setOnClickListener {
            ARouter.getInstance().build("/app/choose/table")
                .withInt("clickType", chooseType)
                .navigation(this, requestCodeTableStyle)
        }
        binding.layoutMoreView.butCreateOne.setOnClickListener {
            requestMore.name = binding.layoutMoreView.edtMemorialName.text.trim().toString()
            requestMore.theme = binding.layoutMoreView.edtMemorialTheme.text.trim().toString()
            requestMore.monumentMaker = binding.layoutMoreView.edtMemorialCreateName.text.trim().toString()
            requestMore.ancestralHome = binding.layoutMoreView.edtMemorialLocal.text.trim().toString()
            viewModel.moreMemorialRequest(requestMore)
            showLoading()
        }
        viewModel.moreMemorialResponse.observe(this, Observer { resources ->
            handleResponse(resources, {
                dismissLoading()
            },
                {
                    dismissLoading()
                }
            )
        })
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
                .withInt("clickType", chooseType)
                .navigation(this, requestCodeMemorialStyleOne)
        }
        binding.layoutOneView.tvHallStyle.setOnClickListener {
            ARouter.getInstance().build("/app/choose/hall")
                .withInt("clickType", chooseType)
                .navigation(this, requestCodeHallStyleOne)
        }
        binding.layoutOneView.tvTableStyle.setOnClickListener {
            ARouter.getInstance().build("/app/choose/table")
                .withInt("clickType", chooseType)
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
            requestMore.memorialId = ids
        } else if (requestCode == requestCodeHallStyle && resultCode == 1) {
            val name = data?.getStringExtra("name") ?: ""
            val ids = data?.getIntExtra("ids",-1) ?: -1
            binding.layoutMoreView.tvHallStyle.text = name
            requestMore.hallId = ids
        } else if (requestCode == requestCodeTableStyle && resultCode == 1) {
            val name = data?.getStringExtra("name") ?: ""
            val ids = data?.getIntExtra("ids",-1) ?: -1
            binding.layoutMoreView.tvTableStyle.text = name
            requestMore.tabletId = ids
        } else if (requestCode == requestCodeMemorialStyleOne && resultCode == 1) {
            val name = data?.getStringExtra("name") ?: ""
            val ids = data?.getIntExtra("ids",-1) ?: -1
            binding.layoutOneView.tvMemorialStyle.text = name
            requestOne.memorialId = ids
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
        }else if (requestCode == requestCodeMemorialStyleDouble && resultCode == 1) {
            val name = data?.getStringExtra("name") ?: ""
            val ids = data?.getIntExtra("ids",-1) ?: -1
            binding.layoutTwoView.tvMemorialStyle.text = name
            requestDouble.memorialId = ids
        } else if (requestCode == requestCodeHallStyleDouble && resultCode == 1) {
            val name = data?.getStringExtra("name") ?: ""
            val ids = data?.getIntExtra("ids",-1) ?: -1
            binding.layoutTwoView.tvHallStyle.text = name
            requestDouble.hallId = ids
        } else if (requestCode == requestCodeTableStyleDouble && resultCode == 1) {
            val name = data?.getStringExtra("name") ?: ""
            val ids = data?.getIntExtra("ids",-1) ?: -1
            binding.layoutTwoView.tvTableStyle.text = name
            requestDouble.tabletId1 = ids
        }else if (requestCode == requestCodeTableStyleDouble1 && resultCode == 1) {
            val name = data?.getStringExtra("name") ?: ""
            val ids = data?.getIntExtra("ids",-1) ?: -1
            binding.layoutTwoView.tvTableStyle1.text = name
            requestDouble.tabletId2 = ids
        }
    }

    override fun getViewBinding(): ActivityCreateHallBinding {
        return ActivityCreateHallBinding.inflate(layoutInflater)
    }

}