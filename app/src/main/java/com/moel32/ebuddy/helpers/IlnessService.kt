package com.moel32.ebuddy.helpers

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface IllnessService {

    @GET("illnesses")
    fun getIllnesses(@Query("symptom") symptom: String): Call<List<Illness>>
}