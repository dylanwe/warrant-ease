package com.warrantease.androidapp.presentation.ui.utils

import com.github.marlonlom.utilities.timeago.TimeAgo
import java.time.LocalDate
import java.time.ZoneId
import java.time.ZonedDateTime

object RelativeTime {
	fun getRelativeTime(date: LocalDate): String {
		return if (date == LocalDate.now()) {
			"Today"
		} else {
			TimeAgo.using(
				ZonedDateTime.of(
					date.atStartOfDay(),
					ZoneId.systemDefault()
				).toInstant().toEpochMilli(),
			)
		}
	}
}