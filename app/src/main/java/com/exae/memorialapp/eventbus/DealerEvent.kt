package com.exae.memorialapp.eventbus

import androidx.annotation.Keep

@Keep
data class DealerEvent(var name : String,var isExtend : Boolean = false)
