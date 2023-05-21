package com.warrantychecker.backend.models

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.*
import org.hibernate.Hibernate
import java.time.LocalDate

@Entity
data class Warranty(
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
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as Warranty

        return id != null && id == other.id
    }

    override fun hashCode(): Int = javaClass.hashCode()

    @Override
    override fun toString(): String {
        return this::class.simpleName + "(id = $id )"
    }
}