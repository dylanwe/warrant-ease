package com.warrantease.androidapp.data.repository

import com.warrantease.androidapp.data.api.WarrantyApi
import com.warrantease.androidapp.data.api.WarrantyApiService
import com.warrantease.androidapp.data.api.mappers.WarrantyMapper.toDomain
import com.warrantease.androidapp.domain.model.Warranty

/**
 * Example repository
 */
class WarrantyRepository {
    private val warrantyApiService: WarrantyApiService = WarrantyApi.createApi()

    suspend fun getWarranties(): List<Warranty> {
        return warrantyApiService
            .getWarranties()
            .toDomain()
    }
}