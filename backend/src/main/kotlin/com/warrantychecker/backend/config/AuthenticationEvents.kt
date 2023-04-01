package com.warrantychecker.backend.config

import com.warrantychecker.backend.models.User
import com.warrantychecker.backend.repositories.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.event.EventListener
import org.springframework.security.authentication.event.AuthenticationSuccessEvent
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken
import org.springframework.stereotype.Component

@Component
class AuthenticationEvents(
	@Autowired val userRepository: UserRepository
) {
	@EventListener
	fun onSuccess(success: AuthenticationSuccessEvent?) {
		// get user info from oauth
		if (success != null && success.authentication is JwtAuthenticationToken) {
			val jwt = success.authentication.principal as Jwt
			val userId = jwt.claims["sub"] as String

			// Create user in db if it doesn't exist yet
			if (!userRepository.existsById(userId)) {
				userRepository.save(User(userId))
			}
		}
	}
}
