package com.exae.memorialapp.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.exae.memorialapp.R
import com.exae.memorialapp.bean.TransferListModel
import javax.inject.Inject

class PosTransferAdapter @Inject constructor() :
    BaseQuickAdapter<TransferListModel, BaseViewHolder>(R.layout.item_car) {

    override fun convert(holder: BaseViewHolder, item: TransferListModel) {
        holder.setText(R.id.carType, item.carType)
            .setText(R.id.city, item.city+"-"+item.district)
            .setText(R.id.local_title, item.userName+" | "+item.carHao)

//        helper.getView<ImageView>(R.id.img_car).setImageResource(R.mipmap.car)

    }

}