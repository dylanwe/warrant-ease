package com.warrantychecker.backend.rest

import com.warrantychecker.backend.models.Warranty
import com.warrantychecker.backend.repositories.UserRepository
import com.warrantychecker.backend.repositories.WarrantyRepository
import com.warrantychecker.backend.requests.WarrantyPostRequest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import java.util.*

@RequestMapping("api/v1/warranty")
@RestController
class WarrantyController(
    @Autowired val userRepository: UserRepository,
    @Autowired val warrantyRepository: WarrantyRepository
) {
    @GetMapping
    fun getAllProducts(
        authentication: Authentication,
        @RequestParam("name") name: Optional<String>
    ): ResponseEntity<List<Warranty>> {
        val user = userRepository
            .findById(authentication.name)
            .orElseThrow { ResponseStatusException(HttpStatus.NOT_FOUND, "Could not find the user") }

        val warranties = if (name.isPresent) {
            warrantyRepository.findAllByUserAndNameOrderByExpirationDateDesc(user = user, name = name.get())
        } else {
            warrantyRepository.findAllByUserOrderByExpirationDateDesc(user = user)
        }

        return ResponseEntity
            .ok()
            .body(warranties)
    }

    @PostMapping
    fun saveWarranty(
        authentication: Authentication,
        @RequestBody warrantyPostRequest: WarrantyPostRequest
    ): ResponseEntity<Any> {
        val user = userRepository
            .findById(authentication.name)
            .orElseThrow { ResponseStatusException(HttpStatus.NOT_FOUND, "Could not find the user") }

        val warranty = Warranty(
            name = warrantyPostRequest.name,
            store = warrantyPostRequest.store,
            notes = warrantyPostRequest.notes,
            buyDate = warrantyPostRequest.buyDate,
            expirationDate = warrantyPostRequest.expirationDate,
            reminderDate = warrantyPostRequest.reminderDate,
            user = user
        )

        val savedWarranty = warrantyRepository.save(warranty)

        val location = ServletUriComponentsBuilder
            .fromCurrentRequest()
            .path("/{id}")
            .build(savedWarranty.id)

        return ResponseEntity
            .created(location)
            .build()
    }

    @GetMapping("{id}")
    fun getWarrantyById(authentication: Authentication, @PathVariable id: Long): ResponseEntity<Warranty> {
        val user = userRepository
            .findById(authentication.name)
            .orElseThrow { ResponseStatusException(HttpStatus.NOT_FOUND, "Could not find the user") }

        val warranty = warrantyRepository
            .findByIdAndUser(id = id, user = user)
            .orElseThrow { ResponseStatusException(HttpStatus.NOT_FOUND, "Could not find the warranty with id: $id") }

        return ResponseEntity
            .ok()
            .body(warranty)
    }

    @GetMapping("/top")
    fun getTopExpiration(authentication: Authentication): ResponseEntity<List<Warranty>> {
        val user = userRepository
            .findById(authentication.name)
            .orElseThrow { ResponseStatusException(HttpStatus.NOT_FOUND, "Could not find the user") }

        val warranties = warrantyRepository
            .findTop4ByUserOrderByExpirationDateDesc(user = user)

        return ResponseEntity
            .ok()
            .body(warranties)
    }
}