package com.exae.memorialapp.home.artical

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.exae.memorialapp.R
import com.exae.memorialapp.bean.AlbumPicListModel
import javax.inject.Inject

class AlbumAdapter @Inject constructor() :
    BaseQuickAdapter<AlbumPicListModel, BaseViewHolder>(R.layout.item_album), LoadMoreModule {

    override fun convert(holder: BaseViewHolder, item: AlbumPicListModel) {
        holder.setText(R.id.desc, item.picDesc)

        val img = holder.getView<ImageView>(R.id.headerPic)
        Glide.with(holder.itemView)
            .load(item.picUrl)
            .apply(RequestOptions.bitmapTransform(RoundedCorners(30)))
            .into(img)
    }
}