package com.exae.memorialapp.requestData

enum class HallType(val type: Int) {
    ONE_HALL(0),
    MORE_HALL(1),
    TWO_HALL(2),
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