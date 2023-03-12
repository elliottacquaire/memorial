package com.exae.memorialapp.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.exae.memorialapp.R
import com.exae.memorialapp.bean.ManageMemorialModel
import javax.inject.Inject

class ManageMemorialAdapter @Inject constructor() :
    BaseQuickAdapter<ManageMemorialModel, BaseViewHolder>(R.layout.item_manage_memorial)  {

    override fun convert(holder: BaseViewHolder, item: ManageMemorialModel) {
        holder.setText(R.id.hallNum, "馆号："+item.ememorialNo.toString())
            .setText(R.id.hallName, item.name)
            .setText(R.id.hallTime, item.createTime)
            .setText(R.id.hallLevel, item.type)
    }
}