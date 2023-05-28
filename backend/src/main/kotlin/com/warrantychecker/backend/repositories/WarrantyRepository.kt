package com.warrantychecker.backend.repositories

import com.warrantychecker.backend.models.User
import com.warrantychecker.backend.models.Warranty
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface WarrantyRepository : JpaRepository<Warranty, Long> {
    fun findAllByUserOrderByExpirationDateAsc(user: User): List<Warranty>

    fun findTop4ByUserOrderByExpirationDateAsc(user: User): List<Warranty>

    fun findAllByUserAndNameContainingIgnoreCaseOrderByExpirationDateAsc(user: User, name: String): List<Warranty>

    fun findByIdAndUser(id: Long, user: User): Optional<Warranty>
}