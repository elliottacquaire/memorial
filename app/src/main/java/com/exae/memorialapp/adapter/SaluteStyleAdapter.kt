package com.exae.memorialapp.adapter

import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.exae.memorialapp.R
import com.exae.memorialapp.bean.CONTENT_MIDDLE
import com.exae.memorialapp.bean.SaluteMultData
import com.exae.memorialapp.bean.TITLE_TITLE
import javax.inject.Inject

class SaluteStyleAdapter @Inject constructor() :
    BaseMultiItemQuickAdapter<SaluteMultData, BaseViewHolder>() {

    init {
        addItemType(TITLE_TITLE, R.layout.content_title)
        addItemType(CONTENT_MIDDLE, R.layout.item_content_salute_recycle)
//        addItemType(CONTENT_SUB, R.layout.contract_content_item_sub)
//        addItemType(CONTENT_LINE, R.layout.contract_item_line)
    }
    override fun convert(holder: BaseViewHolder, item: SaluteMultData) {
//        holder.setText(R.id.userNum, item.repeatUse.toString())
//            .setText(R.id.name, item.name)

        when (holder.itemViewType) {
            TITLE_TITLE -> {
                holder.setText(R.id.titleName, item.title)
            }
            CONTENT_MIDDLE -> {
                val recycle = holder.getView<RecyclerView>(R.id.recycle)
                recycle.layoutManager = LinearLayoutManager(recycle.context,LinearLayoutManager.HORIZONTAL,false)
                val listAdapter = SaluteRecycleAdapter()
                recycle.adapter = listAdapter
                listAdapter.setNewInstance(item.all)
                Log.i("sss","-----${item.all.size}-----adapter----")
            }
        }
    }
}