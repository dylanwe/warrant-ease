package com.warrantease.androidapp.data.api.mappers

import com.warrantease.androidapp.data.api.response.WarrantyGetResponse
import com.warrantease.androidapp.domain.model.Warranty

object WarrantyMapper {
	internal fun List<WarrantyGetResponse>.toDomain(): List<Warranty> {
		return map { it.toDomain() }
	}

	fun WarrantyGetResponse.toDomain(): Warranty {
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