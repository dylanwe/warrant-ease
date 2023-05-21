package com.warrantease.androidapp.domain.model

import java.time.LocalDate

data class Warranty(
    val id: Long,
    val name: String,
    val store: String,
    val notes: String,
    val buyDate: LocalDate,
    val expirationDate: LocalDate,
    val reminderDate: LocalDate,
)
