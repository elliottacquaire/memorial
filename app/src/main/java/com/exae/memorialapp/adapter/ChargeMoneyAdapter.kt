package com.exae.memorialapp.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.exae.memorialapp.R
import com.exae.memorialapp.bean.ChargeMoneyModel
import javax.inject.Inject

class ChargeMoneyAdapter @Inject constructor() :
    BaseQuickAdapter<ChargeMoneyModel, BaseViewHolder>(R.layout.item_charge_money) {

    override fun convert(holder: BaseViewHolder, item: ChargeMoneyModel) {
        holder.setText(R.id.name, item.name)
            .setText(R.id.num, item.num)
            .setText(R.id.coinNum, item.coinNum)
            .setText(R.id.tips, item.tips)
            .setText(R.id.payMoney, "¥ " + item.price)
    }
}