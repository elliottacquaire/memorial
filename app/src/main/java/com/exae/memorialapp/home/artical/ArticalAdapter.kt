package com.exae.memorialapp.home.artical

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.exae.memorialapp.R
import com.exae.memorialapp.bean.ArticleListModel
import com.exae.memorialapp.utils.CommonUtils.getSplitTime
import javax.inject.Inject

class ArticalAdapter @Inject constructor() :
    BaseQuickAdapter<ArticleListModel, BaseViewHolder>(R.layout.item_article), LoadMoreModule {

    override fun convert(holder: BaseViewHolder, item: ArticleListModel) {
        holder.setText(R.id.nickName, item.title)
            .setText(R.id.createTime, getSplitTime(item.createTime))
            .setText(R.id.content, item.content)

    }
}