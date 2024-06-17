package com.exae.memorialapp.adapter

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.exae.memorialapp.R
import com.exae.memorialapp.bean.AllMaterialOfferItemModel
import com.exae.memorialapp.bean.AllMaterialOfferModel
import com.exae.memorialapp.dialog.AdapterItemClick
import javax.inject.Inject

class SaluteItemAdapter @Inject constructor() :
    BaseQuickAdapter<AllMaterialOfferModel, BaseViewHolder>(R.layout.content_salute_item) {
    private var onItemClick: AdapterItemClick? = null

    override fun convert(holder: BaseViewHolder, item: AllMaterialOfferModel) {
        holder.setText(R.id.titleName1, item.name)
        val recycle = holder.getView<RecyclerView>(R.id.recycle1)
        recycle.layoutManager = LinearLayoutManager(
            recycle.context,
            LinearLayoutManager.HORIZONTAL, false
        )
        val listAdapter = SaluteRecycleAdapter()
        recycle.adapter = listAdapter
        listAdapter.setNewInstance(item.children)
        listAdapter.setOnItemClickListener { adapter, _, position ->
            val item = adapter.getItem(position) as AllMaterialOfferItemModel
            onItemClick?.itemClick(item)
        }
    }

    fun initClick(onItemClickOut: AdapterItemClick) {
        this.onItemClick = onItemClickOut
    }
}
