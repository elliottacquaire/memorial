package com.exae.memorialapp.user

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.alipay.sdk.app.PayTask
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.OnItemChildClickListener
import com.exae.memorialapp.R
import com.exae.memorialapp.adapter.ChargeMoneyAdapter
import com.exae.memorialapp.base.PosBaseActivity
import com.exae.memorialapp.bean.ChargeMoneyModel
import com.exae.memorialapp.databinding.ActivityChargeMoneyBinding
import com.exae.memorialapp.requestData.HandleApplyType
import com.exae.memorialapp.requestData.handleStatusList
import com.exae.memorialapp.utils.OrderInfoUtil2_0
import com.exae.memorialapp.viewmodel.MemorialModel
import com.lxj.xpopup.XPopup
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@AndroidEntryPoint
@Route(path = "/app/charge/money")
class ChargeMoneyActivity : PosBaseActivity<ActivityChargeMoneyBinding>(),
    OnItemChildClickListener {

    @Inject
    lateinit var listAdapter: ChargeMoneyAdapter

    val bannerList = mutableListOf<ChargeMoneyModel>()
    private val viewModel: MemorialModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setToolTitle("纪念币充值")
        setBackState(true)
        bannerList.add(
            ChargeMoneyModel(
                11,
                "套餐1：",
                "500",
                "（+100）",
                "充值优惠100",
                "11",
            )
        )
        bannerList.add(
            ChargeMoneyModel(
                12,
                "套餐2：",
                "800",
                "（+200）",
                "充值优惠200",
                "66",
            )
        )
        binding.apply {
            tvAccount.text = "账号：3456789"
            tvAccountCoin.text = "100"
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
                choosePay()
            }
        }
    }
    private fun choosePay() {
        XPopup.Builder(this)
            .isDarkTheme(false)
            .hasShadowBg(false)
            .isViewMode(true)
            .isDestroyOnDismiss(true)
            .asBottomList("请选择支付方式", arrayOf("支付宝"), null, -1) { _, text ->
                //服务端实现
//                viewModel.handleApplyMemorialRequest(id, type)
//                alipay()
            }.show()
    }

    val APPID = "2015052600090779"
    val RSA2_PRIVATE = "abc"
    val RSA_PRIVATE = "123"
    val coroutineScope = CoroutineScope(Job() + Dispatchers.Main)
    private fun alipay() {
//        val rsa2 = (RSA2_PRIVATE.isNotEmpty())
//        val params = OrderInfoUtil2_0.buildOrderParamMap(APPID, rsa2)
//        val orderParam = OrderInfoUtil2_0.buildOrderParam(params)
//        val privateKey = if (rsa2) RSA2_PRIVATE else RSA_PRIVATE
//        val sign = OrderInfoUtil2_0.getSign(params, privateKey, rsa2)
//        val orderInfo = orderParam + "&" + sign

        coroutineScope.launch { // 在主线程启动一个协程
            val result = withContext(Dispatchers.Default) { // 切换到子线程执行
                val orderInfo = "app_id=2015052600090779&biz_content=%7B%22timeout_express%22%3A%2230m%22%2C%22product_code%22%3A%22QUICK_MSECURITY_PAY%22%2C%22total_amount%22%3A%220.01%22%2C%22subject%22%3A%221%22%2C%22body%22%3A%22%E6%88%91%E6%98%AF%E6%B5%8B%E8%AF%95%E6%95%B0%E6%8D%AE%22%2C%22out_trade_no%22%3A%22IQJZSRC1YMQB5HU%22%7D&charset=utf-8&format=json&method=alipay.trade.app.pay&notify_url=http%3A%2F%2Fdomain.merchant.com%2Fpayment_notify&sign_type=RSA2&timestamp=2016-08-25%2020%3A26%3A31&version=1.0&sign=cYmuUnKi5QdBsoZEAbMXVMmRWjsuUj%2By48A2DvWAVVBuYkiBj13CFDHu2vZQvmOfkjE0YqCUQE04kqm9Xg3tIX8tPeIGIFtsIyp%2FM45w1ZsDOiduBbduGfRo1XRsvAyVAv2hCrBLLrDI5Vi7uZZ77Lo5J0PpUUWwyQGt0M4cj8g%3D"
                val resultPay = PayTask(this@ChargeMoneyActivity).payV2(orderInfo, true)
                Log.i("sss", "--2-----$resultPay-------")
            }
            Log.i("sss", "--2-----$result-------")
        }



        doTask(0){s,i ->

        }

        doTask1(1,{a,b ->

        },{
            var mub = 0
             mub ++
        })

    }

    override fun onDestroy() {
        super.onDestroy()
        coroutineScope.cancel()
    }

    private fun doTask(type:Int,task:(String,Int) -> Unit){
        if (type == 0){
            task("1",1)
        }else{

        }
    }
    private fun doTask1(type:Int,task:(String,Int) -> Unit,task1:(String) -> Int):String{
        if (type == 0){
            task("1",1)
        }else{

        }
        return task1("1").toString()
    }
}