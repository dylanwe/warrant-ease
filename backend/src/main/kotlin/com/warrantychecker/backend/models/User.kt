package com.warrantychecker.backend.models

import jakarta.persistence.Entity
import jakarta.persistence.Id

@Entity
data class Users(
	@Id
	val id: String? = null
)
