package com.warrantychecker.backend.models

import jakarta.persistence.Entity
import jakarta.persistence.Id
import java.util.Date

@Entity
data class Product(
	@Id
	val id: Long,
	val name: String,
	val store: String,
	val notes: String,
	val buyDate: Date,
	val expirationDate: Date,
	val reminderDate: Date
)