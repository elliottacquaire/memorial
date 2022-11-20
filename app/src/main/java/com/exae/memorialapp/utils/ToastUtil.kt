package com.exae.memorialapp.utils

import android.content.Context
import android.view.Gravity
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import com.exae.memorialapp.CusApplication
import com.exae.memorialapp.R

/**
 * Toast统一管理类
 */

class ToastUtil private constructor() {
    /**
     * 取消Toast显示
     */
    fun cancelToast() {
        if (isShow && mToast != null) {
            mToast!!.cancel()
        }
    }

    companion object {
        private var isShow = true //默认显示
        private var mToast: Toast? = null //全局唯一的Toast

        /**
         * 全局控制是否显示Toast
         * @param isShowToast
         */
        fun controlShow(isShowToast: Boolean) {
            isShow = isShowToast
        }

        /**
         * 短时间显示Toast
         *
         * @param context
         * @param message
         */
        fun showShort(
            context: Context?,
            message: CharSequence?
        ) {
            if (isShow) {
                if (mToast == null) {
                    mToast =
                        Toast.makeText(context, message, Toast.LENGTH_SHORT)
                } else {
                    mToast!!.setText(message)
                }
                mToast!!.show()
            }
        }

        fun showCenter(
            message: CharSequence?
        ) {
            if (isShow) {
                val view = View.inflate(
                    CusApplication.instance(),
                    R.layout.common_toast,
                    null
                )
                val tvMsg =
                    view.findViewById<View>(R.id.common_toast) as TextView
                if (mToast == null) {
                    mToast = Toast.makeText(CusApplication.instance(), message, Toast.LENGTH_SHORT)
                }
                tvMsg.text = message
                mToast?.view = view
                mToast?.setGravity(Gravity.CENTER, 0, 0)
                mToast?.show()
            }
        }

        /**
         * 短时间显示Toast
         *
         * @param context
         * @param resId 资源ID:getResources().getString(R.string.xxxxxx);
         */
        fun showShort(context: Context?, resId: Int) {
            if (isShow) {
                if (mToast == null) {
                    mToast = Toast.makeText(context, resId, Toast.LENGTH_SHORT)
                } else {
                    mToast!!.setText(resId)
                }
                mToast!!.show()
            }
        }

        /**
         * 长时间显示Toast
         *
         * @param context
         * @param message
         */
        fun showLong(
            context: Context?,
            message: CharSequence?
        ) {
            if (isShow) {
                if (mToast == null) {
                    mToast = Toast.makeText(context, message, Toast.LENGTH_LONG)
                } else {
                    mToast!!.setText(message)
                }
                mToast!!.show()
            }
        }

        /**
         * 长时间显示Toast
         *
         * @param context
         * @param resId 资源ID:getResources().getString(R.string.xxxxxx);
         */
        fun showLong(context: Context?, resId: Int) {
            if (isShow) {
                if (mToast == null) {
                    mToast = Toast.makeText(context, resId, Toast.LENGTH_LONG)
                } else {
                    mToast!!.setText(resId)
                }
                mToast!!.show()
            }
        }

        /**
         * 自定义显示Toast时间
         *
         * @param context
         * @param message
         * @param duration 单位:毫秒
         */
        fun show(
            context: Context?,
            message: CharSequence?,
            duration: Int
        ) {
            if (isShow) {
                if (mToast == null) {
                    mToast = Toast.makeText(context, message, duration)
                } else {
                    mToast!!.setText(message)
                }
                mToast!!.show()
            }
        }

        /**
         * 自定义显示Toast时间
         *
         * @param context
         * @param resId 资源ID:getResources().getString(R.string.xxxxxx);
         * @param duration 单位:毫秒
         */
        fun show(context: Context?, resId: Int, duration: Int) {
            if (isShow) {
                if (mToast == null) {
                    mToast = Toast.makeText(context, resId, duration)
                } else {
                    mToast!!.setText(resId)
                }
                mToast!!.show()
            }
        }

        /**
         * 自定义Toast的View
         * @param context
         * @param message
         * @param duration 单位:毫秒
         * @param view 显示自己的View
         */
        fun customToastView(
            context: Context?,
            message: CharSequence?,
            duration: Int,
            view: View?
        ) {
            if (isShow) {
                if (mToast == null) {
                    mToast = Toast.makeText(context, message, duration)
                } else {
                    mToast!!.setText(message)
                }
                if (view != null) {
                    mToast!!.view = view
                }
                mToast!!.show()
            }
        }

        /**
         * 自定义Toast的位置
         * @param context
         * @param message
         * @param duration 单位:毫秒
         * @param gravity
         * @param xOffset
         * @param yOffset
         */
        fun customToastGravity(
            context: Context?,
            message: CharSequence?,
            duration: Int,
            gravity: Int,
            xOffset: Int,
            yOffset: Int
        ) {
            if (isShow) {
                if (mToast == null) {
                    mToast = Toast.makeText(context, message, duration)
                } else {
                    mToast!!.setText(message)
                }
                mToast!!.setGravity(gravity, xOffset, yOffset)
                mToast!!.show()
            }
        }

        /**
         * 自定义带图片和文字的Toast，最终的效果就是上面是图片，下面是文字
         * @param context
         * @param message
         * @param iconResId 图片的资源id,如:R.drawable.icon
         * @param duration
         * @param gravity
         * @param xOffset
         * @param yOffset
         */
        fun showToastWithImageAndText(
            context: Context?,
            message: CharSequence?,
            iconResId: Int,
            duration: Int,
            gravity: Int,
            xOffset: Int,
            yOffset: Int
        ) {
            if (isShow) {
                if (mToast == null) {
                    mToast = Toast.makeText(context, message, duration)
                } else {
                    mToast!!.setText(message)
                }
                mToast!!.setGravity(gravity, xOffset, yOffset)
                val toastView = mToast!!.view as LinearLayout
                val imageView = ImageView(context)
                imageView.setImageResource(iconResId)
                toastView.addView(imageView, 0)
                mToast!!.show()
            }
        }

        /**
         * 自定义Toast,针对类型CharSequence
         * @param context
         * @param message
         * @param duration
         * @param view
         * @param isGravity true,表示后面的三个布局参数生效,false,表示不生效
         * @param gravity
         * @param xOffset
         * @param yOffset
         * @param isMargin true,表示后面的两个参数生效，false,表示不生效
         * @param horizontalMargin
         * @param verticalMargin
         */
        fun customToastAll(
            context: Context?,
            message: CharSequence?,
            duration: Int,
            view: View?,
            isGravity: Boolean,
            gravity: Int,
            xOffset: Int,
            yOffset: Int,
            isMargin: Boolean,
            horizontalMargin: Float,
            verticalMargin: Float
        ) {
            if (isShow) {
                if (mToast == null) {
                    mToast = Toast.makeText(context, message, duration)
                } else {
                    mToast!!.setText(message)
                }
                if (view != null) {
                    mToast!!.view = view
                }
                if (isMargin) {
                    mToast!!.setMargin(horizontalMargin, verticalMargin)
                }
                if (isGravity) {
                    mToast!!.setGravity(gravity, xOffset, yOffset)
                }
                mToast!!.show()
            }
        }

        /**
         * 自定义Toast,针对类型resId
         * @param context
         * @param resId
         * @param duration
         * @param view :应该是一个布局，布局中包含了自己设置好的内容
         * @param isGravity true,表示后面的三个布局参数生效,false,表示不生效
         * @param gravity
         * @param xOffset
         * @param yOffset
         * @param isMargin true,表示后面的两个参数生效，false,表示不生效
         * @param horizontalMargin
         * @param verticalMargin
         */
        fun customToastAll(
            context: Context?,
            resId: Int,
            duration: Int,
            view: View?,
            isGravity: Boolean,
            gravity: Int,
            xOffset: Int,
            yOffset: Int,
            isMargin: Boolean,
            horizontalMargin: Float,
            verticalMargin: Float
        ) {
            if (isShow) {
                if (mToast == null) {
                    mToast = Toast.makeText(context, resId, duration)
                } else {
                    mToast!!.setText(resId)
                }
                if (view != null) {
                    mToast!!.view = view
                }
                if (isMargin) {
                    mToast!!.setMargin(horizontalMargin, verticalMargin)
                }
                if (isGravity) {
                    mToast!!.setGravity(gravity, xOffset, yOffset)
                }
                mToast!!.show()
            }
        }
    }

    /**
     * private控制不应该被实例化 */
    init {
        throw UnsupportedOperationException("不能被实例化")
    }
}