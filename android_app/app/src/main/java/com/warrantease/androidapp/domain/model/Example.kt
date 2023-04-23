package com.warrantease.androidapp.domain.model

import com.google.gson.annotations.SerializedName

/**
 * Example data class
 */
data class Example(
    @SerializedName("title") val title: String,
    @SerializedName("price") val price: Double
)
