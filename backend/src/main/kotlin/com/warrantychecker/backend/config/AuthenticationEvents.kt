package com.warrantychecker.backend.config

import org.springframework.context.event.EventListener
import org.springframework.security.authentication.event.AuthenticationSuccessEvent
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken
import org.springframework.stereotype.Component

@Component
class AuthenticationEvents {
	@EventListener
	fun onSuccess(success: AuthenticationSuccessEvent?) {
		// get user info from oauth
		if (success != null && success.authentication is JwtAuthenticationToken) {
			val jwt = success.authentication.principal as Jwt
			val userId = jwt.claims["sub"]
			val email = jwt.claims["email"]
			println(userId)
			println(email)
			// TODO: Create user in db if it doesn't exist yet
		}
	}
}
