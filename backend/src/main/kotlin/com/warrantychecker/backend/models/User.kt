package com.warrantychecker.backend.models

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "users")
data class User(
	@Id
	val id: String? = null
)
