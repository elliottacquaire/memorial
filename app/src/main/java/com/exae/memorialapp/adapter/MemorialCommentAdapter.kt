package com.exae.memorialapp.adapter

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.exae.memorialapp.R
import com.exae.memorialapp.bean.CommentLisModel
import com.exae.memorialapp.utils.CommonUtils.getSplitTime
import javax.inject.Inject

class MemorialCommentAdapter @Inject constructor() :
    BaseQuickAdapter<CommentLisModel, BaseViewHolder>(R.layout.item_comment) {

    override fun convert(holder: BaseViewHolder, item: CommentLisModel) {
        holder.setText(R.id.nickName, item.name)
            .setText(R.id.createTime, getSplitTime(item.createTime))
            .setText(R.id.content, item.content)

        val img = holder.getView<ImageView>(R.id.img_head)
        Glide.with(holder.itemView)
            .load(item.picUrl)
            .placeholder(R.mipmap.headdd)
            .error(R.mipmap.headdd)
            .apply(RequestOptions.bitmapTransform(RoundedCorners(30)))
            .into(img)
    }
}