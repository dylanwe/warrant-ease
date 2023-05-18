package com.warrantychecker.backend.rest

import com.warrantychecker.backend.models.Product
import com.warrantychecker.backend.repositories.ProductRepository
import com.warrantychecker.backend.repositories.UserRepository
import com.warrantychecker.backend.requests.ProductRequest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import java.util.*

@RequestMapping("api/v1/product")
@RestController
class ProductController(
    @Autowired val userRepository: UserRepository,
    @Autowired val productRepository: ProductRepository
) {
    @GetMapping
    fun getAllProducts(authentication: Authentication): ResponseEntity<List<Product>> {
        val user = userRepository
            .findById(authentication.name)
            .orElseThrow { ResponseStatusException(HttpStatus.NOT_FOUND, "Could not find the user") }

        val products = productRepository
            .findAllByUserOrderByExpirationDateDesc(user = user)

        return ResponseEntity
            .ok()
            .body(products)
    }

    @PostMapping
    fun saveProduct(authentication: Authentication, @RequestBody productRequest: ProductRequest): ResponseEntity<Any> {
        val user = userRepository
            .findById(authentication.name)
            .orElseThrow { ResponseStatusException(HttpStatus.NOT_FOUND, "Could not find the user") }

        val product = Product(
            name = productRequest.name,
            store = productRequest.store,
            notes = productRequest.notes,
            buyDate = productRequest.buyDate,
            expirationDate = productRequest.expirationDate,
            reminderDate = productRequest.reminderDate,
            user = user
        )

        val savedProduct = productRepository.save(product)

        val location = ServletUriComponentsBuilder
            .fromCurrentRequest()
            .path("/{id}")
            .build(savedProduct.id)

        return ResponseEntity
            .created(location)
            .build()
    }

    @GetMapping("{id}")
    fun publicPage(authentication: Authentication, @PathVariable id: Long): ResponseEntity<Product> {
        val user = userRepository
            .findById(authentication.name)
            .orElseThrow { ResponseStatusException(HttpStatus.NOT_FOUND, "Could not find the user") }

        val product = productRepository
            .findByIdAndUser(id = id, user = user)
            .orElseThrow { ResponseStatusException(HttpStatus.NOT_FOUND, "Could not find the product with id: $id") }

        return ResponseEntity
            .ok()
            .body(product)
    }

    @GetMapping
    fun searchByName(authentication: Authentication, @RequestParam name: String): ResponseEntity<List<Product>> {
        val user = userRepository
            .findById(authentication.name)
            .orElseThrow { ResponseStatusException(HttpStatus.NOT_FOUND, "Could not find the user") }

        val products = productRepository
            .findAllByUserAndNameOrderByExpirationDateDesc(user = user, name = name)

        return ResponseEntity
            .ok()
            .body(products)
    }

    @GetMapping
    fun getTopExpiration(authentication: Authentication, @RequestParam top: Int): ResponseEntity<List<Product>> {
        val user = userRepository
            .findById(authentication.name)
            .orElseThrow { ResponseStatusException(HttpStatus.NOT_FOUND, "Could not find the user") }

        val products = productRepository
            .findTopByUserOrderByExpirationDateDesc(user = user, top = top)

        return ResponseEntity
            .ok()
            .body(products)
    }
}