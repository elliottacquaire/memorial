package com.exae.memorialapp.widget

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.viewpager.widget.ViewPager

class CustomViewPager: ViewPager {

//    constructor(mContext: Context) : this(mContext, null)
//
//    constructor(mContext: Context, attrs: AttributeSet?) : this(mContext, attrs!!, 0)
//
//    constructor(mContext: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
//        mContext,
//        attrs,
//        defStyleAttr
//    )

    constructor(context: Context) : super(context)

    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet)

//    constructor(context: Context, attributeSet: AttributeSet, defStyleAttr: Int) : super(context, attributeSet, defStyleAttr)

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        var height = 0
        for (i in 0 until childCount) {
            val child: View = getChildAt(i)
            child.measure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED))
            val h: Int = child.measuredHeight
            if (h > height) height = h
        }

        val heightMeasureSpecC = MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY)

        super.onMeasure(widthMeasureSpec, heightMeasureSpecC)
    }
}