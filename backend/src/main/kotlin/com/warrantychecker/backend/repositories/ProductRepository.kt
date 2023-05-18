package com.warrantychecker.backend.repositories

import com.warrantychecker.backend.models.Product
import com.warrantychecker.backend.models.User
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface ProductRepository : JpaRepository<Product, Long> {
    fun findAllByUserOrderByExpirationDateDesc(user: User): List<Product>

    fun findTopByUserOrderByExpirationDateDesc(user: User, top: Int): List<Product>

    fun findAllByUserAndNameOrderByExpirationDateDesc(user: User, name: String): List<Product>

    fun findByIdAndUser(id: Long, user: User): Optional<Product>
}