package com.moel32.ebuddy.helpers

import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object APIUtils {

    private const val API_BASE_URL = "https://api-inference.huggingface.co/models/"

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(API_BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val illnessService: IllnessService = retrofit.create(IllnessService::class.java)

    fun getIllnesses(symptom: String, callback: Callback<List<Illness>>) {
        illnessService.getIllnesses(symptom).enqueue(callback)
    }
}