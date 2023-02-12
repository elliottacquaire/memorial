package com.exae.memorialapp.api

import com.exae.memorialapp.bean.BannerResponse
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

}