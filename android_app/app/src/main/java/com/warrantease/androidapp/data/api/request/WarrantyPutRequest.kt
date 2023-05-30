package com.warrantease.androidapp.data.api.request

import com.warrantease.androidapp.data.api.utils.LocalDateSerializer
import kotlinx.serialization.Serializable
import java.time.LocalDate

@Serializable
data class WarrantyPutRequest(
	val id: Long,
	val name: String,
	val store: String,
	val notes: String,
	@Serializable(with = LocalDateSerializer::class)
	val buyDate: LocalDate,
	@Serializable(with = LocalDateSerializer::class)
	val expirationDate: LocalDate,
)
