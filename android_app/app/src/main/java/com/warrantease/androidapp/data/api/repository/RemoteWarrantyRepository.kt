package com.warrantease.androidapp.data.api.repository

import com.warrantease.androidapp.data.api.WarrantyService
import com.warrantease.androidapp.data.api.mappers.WarrantyMapper.toDomain
import com.warrantease.androidapp.data.api.mappers.WarrantyMapper.toRequest
import com.warrantease.androidapp.domain.WarrantyRepository
import com.warrantease.androidapp.domain.model.Warranty
import org.koin.core.annotation.Factory

@Factory
class RemoteWarrantyRepository(
	private val warrantyService: WarrantyService,
) : WarrantyRepository {
	override suspend fun getAllWarranties(): List<Warranty> {
		return warrantyService.getAllWarranties().toDomain()
	}

	override suspend fun getAllWarranties(name: String): List<Warranty> {
		return warrantyService.getAllWarranties(name).toDomain()
	}

	override suspend fun getTop4Warranties(): List<Warranty> {
		return warrantyService.getTop4Warranties().toDomain()
	}

	override suspend fun saveWarranty(warranty: Warranty) {
		return warrantyService.saveWarranty(warranty.toRequest())
	}
}