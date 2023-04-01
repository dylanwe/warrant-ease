package com.warrantychecker.backend.rest

import com.warrantychecker.backend.repositories.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RequestMapping
@RestController
class RootController(
	@Autowired val userRepository: UserRepository
) {
	@GetMapping("/")
	fun publicPage(): String {
		return "Hello, World!"
	}

	@GetMapping("/private")
	fun privatePage(authentication: Authentication): ResponseEntity<Example> {
		val user = userRepository.findById(authentication.name).get()

		return ResponseEntity.ok(Example("test", 20.00))
	}
}

data class Example(
	val title: String,
	val price: Double
)