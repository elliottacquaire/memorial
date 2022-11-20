package com.exae.memorialapp.bean

import com.google.gson.annotations.SerializedName


data class TransferListModel(

    @SerializedName("user_id")
    val user_id: Int,
    @SerializedName("vehicle_plate")
    val carHao: String,
    @SerializedName("vehicle_model")
    val carType: String,
    @SerializedName("user_name")
    val userName: String,
    @SerializedName("city")
    val city : String,
    @SerializedName("longitude")
    var longitude : Double,
    @SerializedName("latitude")
    var latitude : Double,
    @SerializedName("district")
    val district: String,
    @SerializedName("start_time")
    var startTime : Long,
    @SerializedName("end_time")
    var endTime : Long,
    @SerializedName("message")
    val message: String,
    @SerializedName("request_vehicle_model")
    val carTypeR: String,
    @SerializedName("owner_id")
    val owner_id: Int,
    @SerializedName("applicant")
    val applicant: Int,
    @SerializedName("request_id")
    val request_id: Int
)