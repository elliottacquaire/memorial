package com.exae.memorialapp.bean

import com.chad.library.adapter.base.entity.MultiItemEntity


const val TITLE_TITLE = 0
const val CONTENT_MIDDLE = 1
const val CONTENT_SUB = 2
const val CONTENT_LINE = 3

data class SaluteMultData(
    var fieldTypeOut: Int,
    var titleOut: String = "",
    var childrenOut: AllMaterialOfferItemModel? = null,
    var allOut: ArrayList<AllMaterialOfferItemModel> = ArrayList(),
) : MultiItemEntity {
    private var fieldType: Int = fieldTypeOut
    var title = titleOut
    var children = childrenOut
    var all = allOut

    override val itemType: Int
        get() = fieldType
}
