package com.exae.memorialapp.requestData

enum class HallType(val type: Int) {
    ONE_HALL(1),
    MORE_HALL(3),
    TWO_HALL(2),
}

const val requestCodeMemorialStyle = 1000
const val requestCodeHallStyle = 1001
const val requestCodeTableStyle = 1002

const val requestCodeMemorialStyleOne = 10000
const val requestCodeHallStyleOne = 10010
const val requestCodeTableStyleOne = 10020

enum class SexType(val type: Int) {
    MAN(0),
    WOMAN(1),
    SECRET(2),
}