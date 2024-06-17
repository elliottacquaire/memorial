package com.exae.memorialapp.adapter

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.exae.memorialapp.R
import com.exae.memorialapp.bean.AllMaterialOfferItemModel
import javax.inject.Inject

class SetMealItemAdapter @Inject constructor() :
    BaseQuickAdapter<AllMaterialOfferItemModel, BaseViewHolder>(R.layout.item_content_setmeal) {

    override fun convert(holder: BaseViewHolder, item: AllMaterialOfferItemModel) {
        holder.setText(R.id.saluteName, item.name)
        val img = holder.getView<ImageView>(R.id.salutePic)
        Glide.with(holder.itemView)
            .load(item.picUrl)
            .apply(RequestOptions.bitmapTransform(RoundedCorners(10)))
            .into(img)
    }
}