package com.exae.memorialapp.eventbus;

import androidx.annotation.Keep;

@Keep
public class TraceMessageEvent {
    public String message;
    public TraceMessageEvent(String message) {
        this.message = message;
    }

}
