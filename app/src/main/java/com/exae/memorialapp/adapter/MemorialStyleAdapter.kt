package com.exae.memorialapp.adapter

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.exae.memorialapp.R
import com.exae.memorialapp.bean.StyleMemorialModel
import javax.inject.Inject

class MemorialStyleAdapter @Inject constructor() :
    BaseQuickAdapter<StyleMemorialModel, BaseViewHolder>(R.layout.item_memorial_style) {

    override fun convert(holder: BaseViewHolder, item: StyleMemorialModel) {
        holder.setText(R.id.userNum, "使用人数：" + item.repeatUse.toString())
            .setText(R.id.name, item.name)

        val img = holder.getView<ImageView>(R.id.headerPic)
        Glide.with(holder.itemView)
            .load(item.picUrl)
            .apply(RequestOptions.bitmapTransform(RoundedCorners(30)))
            .into(img)
    }
}