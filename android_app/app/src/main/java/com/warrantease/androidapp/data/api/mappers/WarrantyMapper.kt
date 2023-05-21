package com.warrantease.androidapp.data.api.mappers

import com.warrantease.androidapp.data.api.models.WarrantyResponse
import com.warrantease.androidapp.domain.model.Warranty

object WarrantyMapper {
    internal fun List<WarrantyResponse>.toDomain(): List<Warranty> {
        return map { it.toDomain() }
    }

    fun WarrantyResponse.toDomain(): Warranty {
        return Warranty(
            id = id,
            name = name,
            store = store,
            notes = notes,
            buyDate = buyDate,
            expirationDate = expirationDate,
            reminderDate = reminderDate
        )
    }
}