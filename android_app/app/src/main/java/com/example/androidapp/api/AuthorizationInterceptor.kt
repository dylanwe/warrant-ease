package com.example.androidapp.api

import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.tasks.await
import okhttp3.Interceptor
import okhttp3.Response

class AuthorizationInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        // block thread until token task is resolved
        val tokenTask = runBlocking {
            val user = Firebase.auth.currentUser!!
            return@runBlocking user.getIdToken(true).await()
        }

        // create a new request with the JWT
        val authorizationRequest = chain
            .request()
            .newBuilder()
            .addHeader("Authorization", "Bearer ${tokenTask.token}")
            .build()

        // continue with the request
        return chain.proceed(authorizationRequest)
    }
}