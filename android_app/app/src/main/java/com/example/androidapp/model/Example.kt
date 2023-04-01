package com.example.androidapp.model

import com.google.gson.annotations.SerializedName

data class Example(
    @SerializedName("title") val title: String,
    @SerializedName("price") val price: Double
)
