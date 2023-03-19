package com.exae.memorialapp.detail

import android.os.Bundle
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.exae.memorialapp.R
import com.exae.memorialapp.base.PosBaseActivity
import com.exae.memorialapp.databinding.ActivityModifyMemorialHallBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
@Route(path = "/app/modify/hall")
class ModifyMemorialHallActivity : PosBaseActivity<ActivityModifyMemorialHallBinding>(), View.OnClickListener {
    private var memorialNo = -1
    private var memorialName = ""
    private var memorialType = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        memorialNo = intent.getIntExtra("memorialNo", -1)
        memorialName = intent.getStringExtra("memorialName") ?: ""
        memorialType = intent.getStringExtra("memorialType") ?: ""

        setToolTitle("编辑${memorialName}纪念馆")
        setBackState(true)

        binding.modifyInfo.setOnClickListener(this)
        binding.accountAdd.setOnClickListener(this)
        binding.hallManage.setOnClickListener(this)
        binding.helpCenter.setOnClickListener(this)
        binding.message.setOnClickListener(this)
    }

    override fun getViewBinding(): ActivityModifyMemorialHallBinding {
        return ActivityModifyMemorialHallBinding.inflate(layoutInflater)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.modifyInfo -> {
                when (memorialType) {
                    "0" -> {
                        ARouter.getInstance().build("/app/single/detail")
                            .withInt("memorialNo", memorialNo)
                            .navigation(this)
                    }
                    "1" -> {
                        ARouter.getInstance().build("/app/more/detail")
                            .withInt("memorialNo", memorialNo)
                            .navigation(this)
                    }
                    "2" -> {
                        ARouter.getInstance().build("/app/two/detail")
                            .withInt("memorialNo", memorialNo)
                            .navigation(this)
                    }
                    else -> ""
                }
            }
            R.id.accountAdd -> {
                ARouter.getInstance().build("/app/charge/money")
                    .withInt("clickType", 2) //
                    .navigation(this)
            }
            R.id.hallManage -> {
                ARouter.getInstance().build("/app/manage/hall")
                    .navigation(this)
            }
            R.id.helpCenter -> {
                ARouter.getInstance().build("/pos/history/record")
                    .withInt("clickType", 101) //
                    .navigation(this)
            }
            R.id.message -> {
                ARouter.getInstance().build("/pos/drive/route")
                    .withInt("clickType", 101) //
                    .navigation(this)
            }
        }
    }
}