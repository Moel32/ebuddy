package com.moel32.ebuddy.helpers

import com.google.gson.annotations.SerializedName

data class Illness(
        @SerializedName("condition")
        val condition: String,

        @SerializedName("description")
        val description: String,

        @SerializedName("treatment")
        val treatment: String
    )