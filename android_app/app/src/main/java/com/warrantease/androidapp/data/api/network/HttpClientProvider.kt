package com.warrantease.androidapp.data.api.network

import android.util.Log
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import io.ktor.client.HttpClient
import io.ktor.client.HttpClientConfig
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.engine.okhttp.OkHttpConfig
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.request.header
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.tasks.await
import kotlinx.serialization.json.Json
import org.koin.core.annotation.Single

@Single
class HttpClientProvider {

    val client: HttpClient by lazy {
        HttpClient(OkHttp) {
            installDefaults()
        }
    }

    private fun HttpClientConfig<OkHttpConfig>.installDefaults() {
        installBaseUrl()
        install(ContentNegotiation) {
            json(Json {
                prettyPrint = true
                isLenient = true
                ignoreUnknownKeys = true
            })
        }
    }

    private fun HttpClientConfig<*>.installBaseUrl() = defaultRequest {
        url {
            host = BASE_URL
        }

        // intercept and add JWT
        if (!headers.contains("No-Authentication")) {
            val tokenTask = runBlocking {
                val user = Firebase.auth.currentUser!!
                return@runBlocking user.getIdToken(true).await()
            }

            Log.i("token", tokenTask.token.toString())

            header("Authorization", "Bearer ${tokenTask.token}")
        }
    }

    companion object {
        private const val BASE_URL = "10.0.2.2:8080"
    }
}
