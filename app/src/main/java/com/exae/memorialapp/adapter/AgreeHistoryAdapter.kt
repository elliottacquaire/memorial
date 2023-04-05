package com.exae.memorialapp.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.exae.memorialapp.R
import com.exae.memorialapp.bean.ApplyListModel
import com.exae.memorialapp.requestData.ApplyType
import com.exae.memorialapp.utils.CommonUtils.getSplitTime
import javax.inject.Inject

class AgreeHistoryAdapter @Inject constructor() :
    BaseQuickAdapter<ApplyListModel, BaseViewHolder>(R.layout.item_agree_memorial) {

    override fun convert(holder: BaseViewHolder, item: ApplyListModel) {
        holder.setText(R.id.hallNum, "馆号：" + item.memorialNo.toString())
            .setText(R.id.hallTime, "建馆时间：" + getSplitTime(item.createTime))
            .setText(R.id.hallLevel, "Lv" + item.status)

        val statusText = when (item.status) {
            ApplyType.ELSE.type -> ApplyType.ELSE.tips
            ApplyType.APPLYING.type -> ApplyType.APPLYING.tips
            ApplyType.APPLYING_PASS.type -> ApplyType.APPLYING_PASS.tips
            ApplyType.APPLYING_REJECT.type -> ApplyType.APPLYING_REJECT.tips
            else -> ""
        }

        holder.setText(R.id.modify, statusText)
    }
}