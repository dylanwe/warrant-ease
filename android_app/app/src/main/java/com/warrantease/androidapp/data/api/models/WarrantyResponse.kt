package com.warrantease.androidapp.data.api.models

import com.google.gson.annotations.SerializedName
import java.time.LocalDate

data class WarrantyResponse(
    @SerializedName("id") val id: Long,
    @SerializedName("name") val name: String,
    @SerializedName("store") val store: String,
    @SerializedName("notes") val notes: String,
    @SerializedName("buyDate") val buyDate: LocalDate,
    @SerializedName("expirationDate") val expirationDate: LocalDate,
    @SerializedName("reminderDate") val reminderDate: LocalDate,
)
