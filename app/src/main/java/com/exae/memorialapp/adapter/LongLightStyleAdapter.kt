package com.exae.memorialapp.adapter

import android.widget.ImageView
import android.widget.RadioButton
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.exae.memorialapp.R
import com.exae.memorialapp.bean.StyleLongLightModel
import com.exae.memorialapp.bean.StyleMemorialModel
import javax.inject.Inject

class LongLightStyleAdapter @Inject constructor() :
    BaseQuickAdapter<StyleLongLightModel, BaseViewHolder>(R.layout.item_long_light_style) {
    var selectPos = 0

    override fun convert(holder: BaseViewHolder, item: StyleLongLightModel) {
//        holder.setText(R.id.userNum, item.repeatUse.toString())
//            .setText(R.id.name, item.name)

        holder.getView<RadioButton>(R.id.choose).isChecked = item.isChoose
        val img = holder.getView<ImageView>(R.id.typePic)
        Glide.with(holder.itemView)
            .load(item.picUrl)
            .apply(RequestOptions.bitmapTransform(RoundedCorners(10)))
            .into(img)
    }
}