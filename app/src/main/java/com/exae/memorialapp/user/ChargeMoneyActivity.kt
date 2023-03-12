package com.exae.memorialapp.user

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.OnItemChildClickListener
import com.exae.memorialapp.R
import com.exae.memorialapp.adapter.ChargeMoneyAdapter
import com.exae.memorialapp.base.PosBaseActivity
import com.exae.memorialapp.bean.ChargeMoneyModel
import com.exae.memorialapp.databinding.ActivityChargeMoneyBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
@Route(path = "/app/charge/money")
class ChargeMoneyActivity : PosBaseActivity<ActivityChargeMoneyBinding>(),
    OnItemChildClickListener {

    @Inject
    lateinit var listAdapter: ChargeMoneyAdapter

    val bannerList = mutableListOf<ChargeMoneyModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setToolTitle("纪念币充值")
        setBackState(true)
        bannerList.add(
            ChargeMoneyModel(
                11,
                "套餐1：",
                "500",
                "（+1000）",
                "充值优惠100",
                "11",
            )
        )
        bannerList.add(
            ChargeMoneyModel(
                12,
                "套餐2：",
                "800",
                "（+2000）",
                "充值优惠200",
                "66",
            )
        )
        binding.apply {
            tvAccount.text = "账号：3456789"
            tvAccountCoin.text = "1000"
            mListView.layoutManager = LinearLayoutManager(this@ChargeMoneyActivity)
            mListView.adapter = listAdapter
        }

        listAdapter.data.clear()
        listAdapter.data.addAll(bannerList)

        listAdapter.setOnItemChildClickListener(this)
        listAdapter.addChildClickViewIds(R.id.payMoney)
    }

    override fun getViewBinding(): ActivityChargeMoneyBinding {
        return ActivityChargeMoneyBinding.inflate(layoutInflater)
    }

    override fun onItemChildClick(adapter: BaseQuickAdapter<*, *>, view: View, position: Int) {
        when(view.id){
            R.id.payMoney -> {
//                ARouter.getInstance().build("/app/choose/hall").navigation(this,101)
            }
        }
    }
}