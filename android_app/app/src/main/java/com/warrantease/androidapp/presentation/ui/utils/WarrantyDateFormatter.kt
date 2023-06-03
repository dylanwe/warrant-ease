package com.warrantease.androidapp.presentation.ui.utils

import java.time.format.DateTimeFormatter

object WarrantyDateFormatter {
	val dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")!!
}