package com.exae.memorialapp.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.exae.memorialapp.R
import com.exae.memorialapp.bean.ManageMemorialModel
import javax.inject.Inject

class ManageMemorialAdapter @Inject constructor() :
    BaseQuickAdapter<ManageMemorialModel, BaseViewHolder>(R.layout.item_manage_memorial)  {

    override fun convert(holder: BaseViewHolder, item: ManageMemorialModel) {
        holder.setText(R.id.hallNum, item.hallNum)
            .setText(R.id.hallName, item.hallName)
            .setText(R.id.hallTime, item.hallCreateTime)
            .setText(R.id.hallLevel, item.hallLevel)
    }
}