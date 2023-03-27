package com.exae.memorialapp.detail

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.request.RequestOptions
import com.exae.memorialapp.R
import com.exae.memorialapp.base.PosBaseActivity
import com.exae.memorialapp.base.handleResponse
import com.exae.memorialapp.databinding.ActivitySingleDetailBinding
import com.exae.memorialapp.requestData.HallType
import com.exae.memorialapp.requestData.SexType
import com.exae.memorialapp.requestData.SingleMemorialRequest
import com.exae.memorialapp.requestData.nationList
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
import com.exae.memorialapp.requestData.shipList
import com.exae.memorialapp.utils.CommonUtils
import com.exae.memorialapp.utils.ToastUtil
import com.exae.memorialapp.viewmodel.MemorialModel
import com.loper7.date_time_picker.DateTimePicker
import com.loper7.date_time_picker.dialog.CardDatePickerDialog
import com.lxj.xpopup.XPopup
import com.lxj.xpopup.interfaces.OnSelectListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
@Route(path = "/app/single/detail")
class SingleDetailActivity : PosBaseActivity<ActivitySingleDetailBinding>() {

//    @JvmField
//    @Autowired(name = "memorialNo")
//    var memorialNo = "ss"
    private var chooseType = HallType.ONE_HALL.type
    private var memorialNo = -1
    private val viewModel: MemorialModel by viewModels()
    private var requestOne = SingleMemorialRequest()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        ARouter.getInstance().inject(this)
        setToolTitle("纪念馆资料")
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
                    tvRelation.text = result?.relationship ?: ""
                    tvEpitaph.setText(result?.epitaph ?: "")
                    tvAddress.setText(result?.address ?: "")

                    requestOne.ememorialNo = result?.ememorialNo

                    Glide.with(this@SingleDetailActivity)
                        .load(result?.picUrlPrefix + result?.avatarPicUrl)
                        .placeholder(R.mipmap.headdd)
                        .error(R.mipmap.headdd)
                        .apply(RequestOptions.bitmapTransform(CircleCrop()))
                        .into(binding.headImg)
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

        binding.headImg.setOnClickListener {

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
                .withInt("clickType", chooseType)
                .navigation(this, requestCodeMemorialStyleOne)
        }
        binding.tvHallStyle.setOnClickListener {
            ARouter.getInstance().build("/app/choose/hall")
                .withInt("clickType", chooseType)
                .navigation(this, requestCodeHallStyleOne)
        }
        binding.tvTableStyle.setOnClickListener {
            ARouter.getInstance().build("/app/choose/table")
                .withInt("clickType", chooseType)
                .navigation(this, requestCodeTableStyleOne)
        }
        binding.butCreateOne.setOnClickListener {
            requestOne.name = binding.edtPersonName.text.trim().toString()
            requestOne.birthDate = binding.tvBrithData.text.trim().toString()
            requestOne.leaveDate = binding.tvDeathData.text.trim().toString()
            requestOne.epitaph = binding.tvEpitaph.text.trim().toString()
            requestOne.nation = binding.tvNation.text.trim().toString()
            requestOne.relationship = binding.tvRelation.text.trim().toString()
            requestOne.address = binding.tvAddress.text.trim().toString()
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
        chooseNation()
        chooseRelationShip()
    }

    private fun chooseNation(){
        binding.tvNation.setOnClickListener {
            var position = -1
            nationList.forEachIndexed { index, s ->
                if (s == binding.tvNation.text) {
                    position = index
                    return@forEachIndexed
                }
            }
            val pop = XPopup.Builder(this)
                .isDarkTheme(true)
                .hasShadowBg(false)
                .popupHeight(1200)
                .isViewMode(true)
                .isDestroyOnDismiss(true)
                .asBottomList("请选择一项", nationList, null, position) { _, text ->
                    binding.tvNation.text = text
                }.show()
        }
    }

    private fun chooseRelationShip(){
        binding.tvRelation.setOnClickListener {
            var position = -1
            shipList.forEachIndexed { index, s ->
                if (s == binding.tvRelation.text) {
                    position = index
                    return@forEachIndexed
                }
            }
            val pop = XPopup.Builder(this)
                .isDarkTheme(true)
                .hasShadowBg(true)
                .popupHeight(1200)
                .isViewMode(true)
                .isDestroyOnDismiss(true)
                .asBottomList("请选择一项",shipList,null,position) { _, text ->
                    binding.tvRelation.text = text
                }.show()
        }
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