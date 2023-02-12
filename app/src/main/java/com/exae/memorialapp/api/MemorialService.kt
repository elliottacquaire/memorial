package com.exae.memorialapp.api

import com.exae.memorialapp.bean.BannerResponse
import com.exae.memorialapp.bean.ManageMemorialResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface MemorialService {

    @GET("{path}")
    suspend fun bannerRequest(
        @Path(
            value = "path",
            encoded = true
        ) url: String
    ): BannerResponse

    @GET("{path}")
    suspend fun manageMerioRequest(
        @Path(
            value = "path",
            encoded = true
        ) url: String
    ): ManageMemorialResponse

    @GET("{path}")
    suspend fun chooseHallRequestRequest(
        @Path(
            value = "path",
            encoded = true
        ) url: String
    ): BannerResponse

    @GET("{path}")
    suspend fun chooseTableRequestRequest(
        @Path(
            value = "path",
            encoded = true
        ) url: String
    ): BannerResponse

    @GET("{path}")
    suspend fun chooseMemorialRequestRequest(
        @Path(
            value = "path",
            encoded = true
        ) url: String
    ): BannerResponse
}