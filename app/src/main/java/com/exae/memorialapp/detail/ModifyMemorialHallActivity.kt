package com.exae.memorialapp.detail

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.exae.memorialapp.R
import com.exae.memorialapp.base.PosBaseActivity
import com.exae.memorialapp.base.handleResponse
import com.exae.memorialapp.databinding.ActivityModifyMemorialHallBinding
import com.exae.memorialapp.utils.ToastUtil
import com.exae.memorialapp.viewmodel.MemorialModel
import com.lxj.xpopup.XPopup
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
@Route(path = "/app/modify/hall")
class ModifyMemorialHallActivity : PosBaseActivity<ActivityModifyMemorialHallBinding>(), View.OnClickListener {
    private var memorialNo = -1
    private var memorialName = ""
    private var memorialType = ""
//    private var positionInt = 0
    private val viewModel: MemorialModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        memorialNo = intent.getIntExtra("memorialNo", -1)
        memorialName = intent.getStringExtra("memorialName") ?: ""
        memorialType = intent.getStringExtra("memorialType") ?: ""
//        positionInt = intent.getIntExtra("positionInt", 0)

        setToolTitle("编辑${memorialName}纪念馆")
        setBackState(true)

        binding.modifyInfo.setOnClickListener(this)
        binding.introduceFrag.setOnClickListener(this)
        binding.memorialFrag.setOnClickListener(this)
        binding.commentFrag.setOnClickListener(this)
        binding.delete.setOnClickListener(this)

        initResponse()
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
            R.id.introduceFrag -> {
                ARouter.getInstance().build("/app/home")
                    .withInt("memorialNo", memorialNo)
                    .withString("memorialName", memorialName)
                    .withString("memorialType", memorialType)
                    .withInt("positionInt", 1)
                    .navigation(this)
            }
            R.id.memorialFrag -> {
                ARouter.getInstance().build("/app/home")
                    .withInt("memorialNo", memorialNo)
                    .withString("memorialName", memorialName)
                    .withString("memorialType", memorialType)
                    .withInt("positionInt", 2)
                    .navigation(this)
            }
            R.id.commentFrag -> {
                ARouter.getInstance().build("/app/home")
                    .withInt("memorialNo", memorialNo)
                    .withString("memorialName", memorialName)
                    .withString("memorialType", memorialType)
                    .withInt("positionInt", 3)
                    .navigation(this)
            }
            R.id.delete -> {
                confirmDialog()
            }
        }
    }

    private fun initResponse(){
        viewModel.deleteMemorialResponse.observe(this, Observer { resources ->
            handleResponse(resources, {
                dismissLoading()
                ToastUtil.showCenter("删除成功")
                finish()
            },
                {
                    dismissLoading()
                }
            )
        })
    }

    private fun confirmDialog() {
        XPopup.Builder(this)
            .hasStatusBarShadow(false)
            .hasNavigationBar(false)
            .isDestroyOnDismiss(true)
            .isDarkTheme(true)
            .asConfirm("温馨提示", "确定删除该纪念馆吗？") {
                viewModel.deleteMemorialRequest(memorialNo)
            }.show()
    }
}