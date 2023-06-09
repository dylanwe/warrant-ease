package com.warrantychecker.backend.rest

import com.warrantychecker.backend.models.Warranty
import com.warrantychecker.backend.repositories.UserRepository
import com.warrantychecker.backend.repositories.WarrantyRepository
import com.warrantychecker.backend.requests.WarrantyPostRequest
import com.warrantychecker.backend.requests.WarrantyPutRequest
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
            warrantyRepository.findAllByUserAndNameContainingIgnoreCaseOrderByExpirationDateAsc(
                user = user,
                name = name.get()
            )
        } else {
            warrantyRepository.findAllByUserOrderByExpirationDateAsc(user = user)
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

    @DeleteMapping("{id}")
    fun deleteWarrantyById(authentication: Authentication, @PathVariable id: Long): ResponseEntity<Warranty> {
        val user = userRepository
            .findById(authentication.name)
            .orElseThrow { ResponseStatusException(HttpStatus.NOT_FOUND, "Could not find the user") }

        val warranty = warrantyRepository
            .findByIdAndUser(id = id, user = user)
            .orElseThrow { ResponseStatusException(HttpStatus.NOT_FOUND, "Could not find the warranty with id: $id") }

        warrantyRepository.delete(warranty)

        return ResponseEntity
            .ok()
            .body(warranty)
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

    @PutMapping("{id}")
    fun updateWarranty(
        authentication: Authentication,
        @PathVariable id: Long,
        @RequestBody body: WarrantyPutRequest
    ): ResponseEntity<Warranty> {
        userRepository
            .findById(authentication.name)
            .orElseThrow { ResponseStatusException(HttpStatus.NOT_FOUND, "Could not find the user") }

        val oldWarranty = warrantyRepository
            .findById(id)
            .orElseThrow { ResponseStatusException(HttpStatus.NOT_FOUND, "Could not find odl warranty") }

        val updatedWarranty = oldWarranty.copy(
            name = body.name,
            store = body.store,
            buyDate = body.buyDate,
            notes = body.notes,
            expirationDate = body.expirationDate
        )

        val savedWarranty = warrantyRepository.save(updatedWarranty)

        val location = ServletUriComponentsBuilder
            .fromCurrentRequest()
            .path("/{id}")
            .build(savedWarranty.id)

        return ResponseEntity
            .created(location)
            .body(savedWarranty)
    }

    @GetMapping("/top")
    fun getTopExpiration(authentication: Authentication): ResponseEntity<List<Warranty>> {
        val user = userRepository
            .findById(authentication.name)
            .orElseThrow { ResponseStatusException(HttpStatus.NOT_FOUND, "Could not find the user") }

        val warranties = warrantyRepository
            .findTop4ByUserOrderByExpirationDateAsc(user = user)

        return ResponseEntity
            .ok()
            .body(warranties)
    }
}