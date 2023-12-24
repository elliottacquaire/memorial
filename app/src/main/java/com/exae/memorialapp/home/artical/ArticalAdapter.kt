package com.exae.memorialapp.home.artical

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.exae.memorialapp.R
import com.exae.memorialapp.bean.CommentListModel
import com.exae.memorialapp.utils.CommonUtils.getSplitTime
import javax.inject.Inject

class ArticalAdapter @Inject constructor() :
    BaseQuickAdapter<CommentListModel, BaseViewHolder>(R.layout.item_comment), LoadMoreModule {

    override fun convert(holder: BaseViewHolder, item: CommentListModel) {
        holder.setText(R.id.nickName, item.name)
            .setText(R.id.createTime, getSplitTime(item.createTime))
            .setText(R.id.content, item.comment)

        val img = holder.getView<ImageView>(R.id.img_head)
        Glide.with(holder.itemView)
            .load(item.picUrl)
            .placeholder(R.mipmap.headdd)
            .error(R.mipmap.headdd)
            .apply(RequestOptions.bitmapTransform(RoundedCorners(30)))
            .into(img)
    }
}