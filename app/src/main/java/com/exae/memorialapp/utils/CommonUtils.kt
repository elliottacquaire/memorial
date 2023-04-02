package com.exae.memorialapp.utils

import android.icu.text.SimpleDateFormat
import android.os.Build
import com.exae.memorialapp.CusApplication
import java.io.IOException
import java.io.InputStream
import java.math.BigDecimal
import java.sql.Timestamp
import java.util.*
import java.util.regex.Matcher
import java.util.regex.Pattern

object CommonUtils {

    //手机格式验证
    fun isMobileNO(mobiles: String): Boolean {
        val p: Pattern =
            Pattern.compile("^[1](([3|5|8][\\d])|([4][4,5,6,7,8,9])|([6][2,5,6,7])|([7][^9])|([9][1,8,9]))[\\d]{8}$")
        val m: Matcher = p.matcher(mobiles)
        return m.matches()
    }

    /**
     * 1，报价单；2，合同；3，报价单（无配额）；4，合同（无配额）；5，新款车合同
     * */
    fun showTitle(hasDown: Int, type: Int): String {   //hasDone  0 未处理， 1 已处理
        if (hasDown == 0) {
            return when (type) {
                0 -> ""
                1 -> "折扣申请审批"
                2 -> "订单审批"
                3 -> "折扣申请审批"
                4 -> "订单审批"
                5 -> "新款车合同审批"
                6 -> "车辆暂留申请"
                201 -> "订单变更申请"
                202 -> "订单取消申请"
                else -> ""
            }
        } else if (hasDown == 1) {
            return when (type) {
                0 -> ""
                1 -> "折扣申请详情"
                2 -> "订单详情"
                3 -> "折扣申请详情"
                4 -> "订单详情"
                5 -> "新款车合同详情"
                6 -> "车辆暂留详情"
                201 -> "订单变更详情"
                202 -> "订单取消详情"
                else -> ""
            }
        }
        return ""
    }

    /**
     * position --> tab num
     */
    fun productFlowType(position: Int): Int {
        return when (position) {
            0 -> 0 //全部
            1 -> 6 //暂留
            2 -> 1 //报价
            3 -> 2 //合同
            else -> 0
        }
    }

    //时间，价格排序
    fun getUpDown(position: Int): Boolean { //true 升序，false 降序
        return when (position) {
            0 -> true
            1 -> false
            2 -> true
            3 -> false
            4 -> true
            else -> false
        }
    }

    //时间，价格排序 历史
    fun getUpDownHistory(position: Int): Boolean { //true 升序，false 降序
        return when (position) {
            0 -> false
            1 -> false
            2 -> true
            3 -> false
            4 -> true
            else -> false
        }
    }

    //获取版本号
    fun getAppCode(): Long {
        var versionCode = -1L
        try {
            val pm = CusApplication.instance().packageManager
            val pi = pm.getPackageInfo(CusApplication.instance().packageName, 0);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                versionCode = pi.longVersionCode
            } else {
                versionCode = pi.versionCode.toLong()
            }
        } catch (e: Exception) {
        }

        return versionCode
    }

    fun getPrintSize(num: Double?): String {
        if (num == null) return ""
        return when {
            num < 1024 -> {
                "$num B"
            }
            num < 1024 * 1024 -> {
                BigDecimal(num / 1024).setScale(2, BigDecimal.ROUND_HALF_UP).toString() + " KB"
            }
            num < 1024 * 1024 * 1024 -> {
                BigDecimal(num / (1024 * 1024)).setScale(2, BigDecimal.ROUND_HALF_UP)
                    .toString() + " MB"
            }
            else -> {
                BigDecimal(num / (1024 * 1024 * 1024)).setScale(2, BigDecimal.ROUND_HALF_UP)
                    .toString() + " GB"
            }
        }
    }

    fun formatMiss(time: Int): String? {
        return (if (time / 3600 > 9) {
            (time / 3600).toString() + ""
        } else "0" + time / 3600) +
                ": ${
                    if (time % 3600 / 60 > 9) {
                        (time % 3600 / 60).toString() + ""
                    } else "0" + time % 3600 / 60
                }" +
                ": ${
                    if (time % 3600 % 60 > 9) {
                        (time % 3600 % 60).toString() + ""
                    } else "0" + time % 3600 % 60
                }"
    }

    fun formatMiss1(time: Int): String? {
        val hh =
            if (time / 3600 > 9) {
                (time / 3600).toString() + ""
            } else "0" + time / 3600
        val mm =
            if (time % 3600 / 60 > 9) {
                (time % 3600 / 60).toString() + ""
            } else "0" + time % 3600 / 60
        val ss =
            if (time % 3600 % 60 > 9) {
                (time % 3600 % 60).toString() + ""
            } else "0" + time % 3600 % 60
        return "${hh}: ${mm}: ${ss}"
    }

    fun dip2px(dpValue: Float): Int {
        val scale = CusApplication.instance().resources.displayMetrics.density
        return ((dpValue * scale + 0.5f).toInt())
    }

    fun formatTime(time: Int): String? {
        val hh =
            if (time / 3600 > 9) {
                (time / 3600).toString() + ""
            } else "0" + time / 3600
        val mm =
            if (time % 3600 / 60 > 9) {
                (time % 3600 / 60).toString() + ""
            } else "0" + time % 3600 / 60
        val ss =
            if (time % 3600 % 60 > 9) {
                (time % 3600 % 60).toString() + ""
            } else "0" + time % 3600 % 60
        return "${hh}h${mm}m${ss}s"
    }

    fun formatTime2(time: Int): String? {
        val hh =
            if (time / 3600 > 9) {
                (time / 3600).toString() + "时"
            } else if (time / 3600 == 0) {
                ""
            } else "0" + time / 3600 + "时"
        val mm =
            if (time % 3600 / 60 > 9) {
                (time % 3600 / 60).toString() + ""
            } else "0" + time % 3600 / 60
        val ss =
            if (time % 3600 % 60 > 9) {
                (time % 3600 % 60).toString() + ""
            } else "0" + time % 3600 % 60
        return "${hh}${mm}分${ss}秒"
    }

    fun getAssetsStyle(fileName: String): ByteArray? {
        var buffer1: ByteArray? = null
        var is1: InputStream? = null
        try {
            is1 = CusApplication.instance().resources.assets.open(fileName)
            val length1: Int = is1.available()
            buffer1 = ByteArray(length1)
            is1.read(buffer1)
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            try {
                is1?.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        return buffer1
    }

    //时间戳转化
    fun getCurrentTime(time: Long): String {
        val format = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA)
        return format.format(Date(time))
    }

    fun getTime(time: Long): String {
        val format = SimpleDateFormat("yyyy-MM-dd", Locale.CHINA)
        return format.format(Date(time))
    }

    fun getTime(time: String): String {
        val format1 = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA)
        val format = SimpleDateFormat("yyyy-MM-dd", Locale.CHINA)
        val timeS = Timestamp.valueOf(time)
        return format.format(timeS)
    }

    fun getSplitTime(time: String): String {
        if (time.isEmpty() || time.length < 10) return ""
        return time.substring(0, 10)
    }

}