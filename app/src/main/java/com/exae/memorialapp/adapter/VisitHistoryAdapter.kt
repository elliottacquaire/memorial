package com.exae.memorialapp.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.exae.memorialapp.R
import com.exae.memorialapp.bean.ApplyListModel
import com.exae.memorialapp.requestData.ApplyType
import com.exae.memorialapp.utils.CommonUtils.getSplitTime
import javax.inject.Inject

class VisitHistoryAdapter @Inject constructor() :
    BaseQuickAdapter<ApplyListModel, BaseViewHolder>(R.layout.item_visit_memorial) {

    override fun convert(holder: BaseViewHolder, item: ApplyListModel) {
        holder.setText(R.id.memorialNo, "纪念馆馆号：" + item.memorialNo.toString())
            .setText(R.id.applyTime, "申请时间：" + getSplitTime(item.createTime))
            .setText(R.id.hallLevel, "Lv" + item.status)
            .setText(R.id.hallName, item.memorialName)
            .setText(R.id.reason, "理由：" + item.notes)
        val typeText = when (item.memorialType) {
            "0" -> "个"
            "1" -> "家"
            "2" -> "双"
            else -> ""
        }
        holder.setText(R.id.hallType, typeText)
        val tips = when (item.status) {
            ApplyType.APPLYING.type -> ApplyType.APPLYING.tips
            ApplyType.APPLYING_PASS.type -> ApplyType.APPLYING_PASS.tips
            ApplyType.APPLYING_REJECT.type -> ApplyType.APPLYING_REJECT.tips
            else -> ""
        }
        holder.setText(R.id.status, tips)
    }
}