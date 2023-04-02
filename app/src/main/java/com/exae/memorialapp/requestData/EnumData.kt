package com.exae.memorialapp.requestData

enum class HallType(val type: Int) {
    ONE_HALL(0),
    MORE_HALL(1),
    TWO_HALL(2),
}

enum class ApplyType(val type: Int) {
    APPLYING(0),
    APPLYING_PASS(1),
    APPLYING_REJECT(2),
}

const val requestCodeMemorialStyle = 1000
const val requestCodeHallStyle = 1001
const val requestCodeTableStyle = 1002

const val requestCodeMemorialStyleOne = 10000
const val requestCodeHallStyleOne = 10010
const val requestCodeTableStyleOne = 10020

const val requestCodeMemorialStyleDouble = 100001
const val requestCodeHallStyleDouble = 100101
const val requestCodeTableStyleDouble = 100201
const val requestCodeTableStyleDouble1 = 100202

enum class SexType(val type: Int) {
    MAN(0),
    WOMAN(1),
    SECRET(2),
}

val nationList = arrayOf(
    "汉族",
    "壮族",
    "满族",
    "回族",
    "苗族",
    "维吾尔族",
    "土家族",
    "彝族",
    "蒙古族",
    "藏族",
    "布依族",
    "侗族",
    "瑶族",
    "朝鲜族",
    "白族",
    "哈尼族",
    "哈萨克族",
    "黎族",
    "傣族",
    "畲族",
    "傈僳族",
    "仡佬族",
    "东乡族",
    "高山族",
    "拉祜族",
    "水族",
    "佤族",
    "纳西族",
    "羌族",
    "土族",
    "仫佬族",
    "锡伯族",
    "柯尔克孜族",
    "达斡尔族",
    "景颇族",
    "毛南族",
    "撒拉族",
    "布朗族",
    "塔吉克族",
    "阿昌族",
    "普米族",
    "鄂温克族",
    "怒族",
    "京族",
    "基诺族",
    "德昂族",
    "保安族",
    "俄罗斯族",
    "裕固族",
    "乌孜别克族",
    "门巴族",
    "鄂伦春族",
    "独龙族",
    "塔塔尔族",
    "赫哲族",
    "珞巴族"
)

val shipList = arrayOf(
    "纪念兄弟",
    "纪念姐妹",
    "纪念妻子",
    "纪念祖母",
    "纪念祖父",
    "纪念外公",
    "纪念外婆",
    "纪念先祖",
    "纪念父亲",
    "纪念母亲",
    "纪念亲属",
    "纪念友人",
    "纪念同学",
    "纪念老师",
    "纪念同事",
    "纪念战友",
    "纪念儿子",
    "纪念女儿",
    "纪念丈夫",
    "纪念岳父",
    "纪念岳母",
    "纪念幼儿",
    "纪念市民",
    "纪念伯父",
    "纪念伯母",
    "纪念叔叔",
    "纪念婶婶",
    "纪念姨夫",
    "纪念姨妈",
    "纪念烈士",
    "纪念姑妈",
    "纪念姑夫",
    "纪念嫂子",
    "纪念弟媳",
    "纪念婆婆",
    "纪念公公"
)

