package com.warrantease.androidapp.model

import com.google.gson.annotations.SerializedName

/**
 * Example data class
 */
data class Example(
    @SerializedName("title") val title: String,
    @SerializedName("price") val price: Double
)
