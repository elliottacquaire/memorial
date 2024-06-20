package com.exae.memorialapp.adapter

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.exae.memorialapp.R
import com.exae.memorialapp.bean.AttentionListModel
import com.exae.memorialapp.bean.ManageMemorialModel
import com.exae.memorialapp.requestData.HallType
import com.exae.memorialapp.utils.CommonUtils.getSplitTime
import com.exae.memorialapp.utils.CommonUtils.getTime
import javax.inject.Inject

class AttentionMemorialAdapter @Inject constructor() :
    BaseQuickAdapter<AttentionListModel, BaseViewHolder>(R.layout.item_attention_memorial) {

    override fun convert(holder: BaseViewHolder, item: AttentionListModel) {
        holder.setText(R.id.hallNum, item.memorialNo.toString())
            .setText(R.id.hallName, item.name)
            .setText(R.id.hallTime, getSplitTime(item.createTime))
            .setText(R.id.hallLevel, "Lv" + item.type)
        val typeText = when (item.type) {
            "0" -> "个"
            "1" -> "家"
            "2" -> "双"
            else -> ""
        }
        holder.setText(R.id.hallType, typeText)
        val img = holder.getView<ImageView>(R.id.headerPic)
        Glide.with(holder.itemView)
            .load(item.picUrlPrefix + item.thumbPicUrl)
            .placeholder(R.mipmap.headdd)
            .error(R.mipmap.headdd)
            .apply(RequestOptions.bitmapTransform(RoundedCorners(30)))
            .into(img)

    }
}