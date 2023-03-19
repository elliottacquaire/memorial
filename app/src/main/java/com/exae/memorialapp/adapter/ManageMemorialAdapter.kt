package com.exae.memorialapp.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.exae.memorialapp.R
import com.exae.memorialapp.bean.ManageMemorialModel
import com.exae.memorialapp.requestData.HallType
import com.exae.memorialapp.utils.CommonUtils.getSplitTime
import com.exae.memorialapp.utils.CommonUtils.getTime
import javax.inject.Inject

class ManageMemorialAdapter @Inject constructor() :
    BaseQuickAdapter<ManageMemorialModel, BaseViewHolder>(R.layout.item_manage_memorial)  {

    override fun convert(holder: BaseViewHolder, item: ManageMemorialModel) {
        holder.setText(R.id.hallNum, "馆号：" + item.ememorialNo.toString())
            .setText(R.id.hallName, item.name)
            .setText(R.id.hallTime, "建馆时间：" + getSplitTime(item.createTime))
            .setText(R.id.hallLevel, item.type)
        val typeText = when (item.type) {
            "0" -> "个"
            "1" -> "家"
            "2" -> "双"
            else -> ""
        }
        holder.setText(R.id.hallType, typeText)
    }
}