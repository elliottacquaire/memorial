package com.exae.memorialapp.home.artical

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.exae.memorialapp.R
import com.exae.memorialapp.bean.AlbumListModel
import javax.inject.Inject

class AlbumItemAdapter @Inject constructor() :
    BaseQuickAdapter<AlbumListModel, BaseViewHolder>(R.layout.item_name_album), LoadMoreModule {

    override fun convert(holder: BaseViewHolder, item: AlbumListModel) {
        holder.setText(R.id.name, item.name)
    }
}