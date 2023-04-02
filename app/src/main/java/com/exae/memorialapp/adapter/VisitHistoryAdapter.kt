package com.exae.memorialapp.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.exae.memorialapp.R
import com.exae.memorialapp.bean.ApplyListModel
import com.exae.memorialapp.utils.CommonUtils.getSplitTime
import javax.inject.Inject

class VisitHistoryAdapter @Inject constructor() :
    BaseQuickAdapter<ApplyListModel, BaseViewHolder>(R.layout.item_manage_memorial) {

    override fun convert(holder: BaseViewHolder, item: ApplyListModel) {
        holder.setText(R.id.hallNum, "馆号：" + item.memorialNo.toString())
            .setText(R.id.hallTime, "建馆时间：" + getSplitTime(item.createTime))
            .setText(R.id.hallLevel, "Lv" + item.status)

        holder.setText(R.id.hallType, item.applyUserId.toString())
    }
}