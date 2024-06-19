package com.exae.memorialapp.common

import android.content.Context
import com.exae.memorialapp.CusApplication


object ShareUtil {


    fun removeKey(key : String){
        if (key.isNullOrEmpty())return
        val mSharedPreferences = CusApplication.instance().getSharedPreferences(
            "TRACK_RECORD",
            Context.MODE_PRIVATE)
        mSharedPreferences.edit().remove(key).apply()
    }

    fun getKey(key : String) : String?{
        if (key.isEmpty() || key == "0")return ""
        val mSharedPreferences = CusApplication.instance().getSharedPreferences(
            "TRACK_RECORD",
            Context.MODE_PRIVATE)
        return mSharedPreferences.getString(key,"")
    }

    fun putKey(key: String,token:String){
        val mSharedPreferences = CusApplication.instance().getSharedPreferences("TRACK_RECORD", Context.MODE_PRIVATE)
        mSharedPreferences?.edit()?.putString(key, token)?.apply()
    }

    fun getToken(): String?{
        val mSharedPreferences = CusApplication.instance().getSharedPreferences(
            "USER_TOKEN",
            Context.MODE_PRIVATE)
        return mSharedPreferences.getString("token","")
    }

    fun putToken(token:String){
        val mSharedPreferences = CusApplication.instance().getSharedPreferences("USER_TOKEN", Context.MODE_PRIVATE)
        mSharedPreferences?.edit()?.putString("token", token)?.apply()
    }


}