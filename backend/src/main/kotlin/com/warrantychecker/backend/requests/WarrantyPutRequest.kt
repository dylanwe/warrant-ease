package com.warrantychecker.backend.requests

import java.time.LocalDate

data class WarrantyPutRequest(
    val id: Long,
    val name: String,
    val store: String,
    val notes: String,
    val buyDate: LocalDate,
    val expirationDate: LocalDate,
)
