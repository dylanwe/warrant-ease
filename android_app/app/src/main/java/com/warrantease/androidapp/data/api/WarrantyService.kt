package com.warrantease.androidapp.data.api

import com.warrantease.androidapp.data.api.network.HttpClientProvider
import com.warrantease.androidapp.data.api.request.WarrantyPostRequest
import com.warrantease.androidapp.data.api.response.WarrantyGetResponse
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import org.koin.core.annotation.Factory

@Factory
class WarrantyService(private val httpClientProvider: HttpClientProvider) {
	suspend fun getAllWarranties(): List<WarrantyGetResponse> {
		return httpClientProvider.client.get("/api/v1/warranty").body()
	}

	suspend fun getAllWarranties(name: String): List<WarrantyGetResponse> {
		return httpClientProvider.client.get("/api/v1/warranty") {
			url {
				parameters.append("name", name)
			}
		}.body()
	}

	suspend fun getTop4Warranties(): List<WarrantyGetResponse> {
		return httpClientProvider.client.get("/api/v1/warranty/top").body()
	}

	suspend fun saveWarranty(warrantyPostRequest: WarrantyPostRequest) {
		httpClientProvider.client.post("/api/v1/warranty") {
			contentType(ContentType.Application.Json)
			setBody(warrantyPostRequest)
		}
	}
}
