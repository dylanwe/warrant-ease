package com.warrantease.androidapp.data.api.models

import com.google.gson.annotations.SerializedName
import java.util.Date

data class Product(
    @SerializedName("id") val id: Long,
    @SerializedName("name") val name: String,
    @SerializedName("store") val store: String,
    @SerializedName("notes") val notes: String,
    @SerializedName("buyDate") val buyDate: Date,
    @SerializedName("expirationDate") val expirationDate: Date,
    @SerializedName("reminderDate") val reminderDate: Date,
)
