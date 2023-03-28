package com.warrantychecker.backend.rest

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RequestMapping
@RestController
class RootController {
	@GetMapping("/")
	fun publicPage(): String {
		return "Hello, World!"
	}

	@GetMapping("/private")
	fun privatePage(): String {
		return "Welcome to the VIP room ${"eyo"}"
	}
}
