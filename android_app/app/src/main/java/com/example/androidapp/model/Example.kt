package com.example.androidapp.model

import com.google.gson.annotations.SerializedName

/**
 * Example data class
 */
data class Example(
    @SerializedName("title") val title: String,
    @SerializedName("price") val price: Double
)
