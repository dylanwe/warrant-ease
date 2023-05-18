package com.warrantychecker.backend.models

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.*
import java.time.LocalDate

@Entity
data class Product(
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    val id: Long? = null,
    val name: String,
    val store: String,
    val notes: String,
    val buyDate: LocalDate,
    val expirationDate: LocalDate,
    val reminderDate: LocalDate,
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    val user: User
)