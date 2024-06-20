package com.exae.memorialapp.eventbus

import androidx.annotation.Keep

@Keep
data class AttentionEvent(var flag : Boolean = false,var edit : Boolean = false)
