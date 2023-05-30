package com.warrantease.androidapp.domain

import com.warrantease.androidapp.domain.model.Warranty

interface WarrantyRepository {
	suspend fun getAllWarranties(): List<Warranty>
	suspend fun getAllWarranties(name: String): List<Warranty>
	suspend fun getTop4Warranties(): List<Warranty>
	suspend fun deleteWarrantyById(warrantyId: Long)
	suspend fun getWarrantyById(warrantyId: Long): Warranty
	suspend fun updateWarranty(warranty: Warranty)
	suspend fun saveWarranty(warranty: Warranty)
}