package com.warrantease.androidapp.domain

import com.warrantease.androidapp.domain.model.Warranty

interface WarrantyRepository {
	suspend fun getAllWarranties(): List<Warranty>
	suspend fun getTop4Warranties(): List<Warranty>
}