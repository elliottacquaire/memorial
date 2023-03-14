package com.exae.memorialapp.api

import com.exae.memorialapp.bean.ProtocolResponse
import com.exae.memorialapp.bean.LoginResultModel
import com.exae.memorialapp.bean.TransferListModel
import com.exae.memorialapp.requestData.PublicCarRequest
import retrofit2.http.*

interface NetwrokService {

    @GET("{path}")
    suspend fun codeLoginRequest(
        @Path(value = "path", encoded = true) url: String
    ): ProtocolResponse<LoginResultModel>


    @PUT("{path}")
    suspend fun publicCarRequest(
        @Path(value = "path", encoded = true) url: String,
        @Body request: PublicCarRequest

    ): ProtocolResponse<LoginResultModel>

    //查询可用车辆列表
    @GET("{path}")
    suspend fun searchCarRequest(
        @Path(value = "path", encoded = true) url: String,
        @Query("city") city: String,
        @Query("request_vehicle_model") carType: String,
        @Query("start_time") startTime: Long,
        @Query("end_time") endTime: Long
    ): ProtocolResponse<List<TransferListModel>>

    @POST("{path}")
    suspend fun sendForCarRequest(
        @Path(value = "path", encoded = true) url: String,
//        @Body request: SendForCarRequest

    ): ProtocolResponse<Any>

    //查询用车请求
    @GET("{path}")
    suspend fun findCarToUse(
        @Path(value = "path", encoded = true) url: String

    ): ProtocolResponse<List<TransferListModel>>


    @PUT("{path}")
    suspend fun acceptCarRequest(
        @Path(
            value = "path",
            encoded = true
        ) url: String
    ): ProtocolResponse<Any>


    //查询用车请求
    @GET("{path}")
    suspend fun findOrderToUse(
        @Path(value = "path", encoded = true) url: String

    ): ProtocolResponse<List<TransferListModel>>

    //查询用车请求
    @GET("{path}")
    suspend fun findOrdddderToUse(
        @Path(value = "path", encoded = true) url: String

    ): ProtocolResponse<List<TransferListModel>>
}