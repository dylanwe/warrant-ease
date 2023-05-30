package com.warrantease.androidapp.presentation.ui.components

import android.annotation.SuppressLint
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.ZoneOffset

@SuppressLint("UnrememberedMutableState")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WarrantyDatePicker(
	isDialogOpen: Boolean,
	setDialogState: (Boolean) -> Unit,
	setDate: (LocalDate) -> Unit,
	initialDate: LocalDate = LocalDate.now(),
) {

// Decoupled snackbar host state from scaffold state for demo purposes.
	val snackState = remember { SnackbarHostState() }
	val snackScope = rememberCoroutineScope()
	SnackbarHost(hostState = snackState, Modifier)
// TODO demo how to read the selected date from the state.
	if (isDialogOpen) {
		val initDateEpoch = initialDate.atStartOfDay(ZoneOffset.UTC).toInstant().toEpochMilli()
		val datePickerState =
			rememberDatePickerState(initialSelectedDateMillis = initDateEpoch)
		val confirmEnabled =
			derivedStateOf { datePickerState.selectedDateMillis != null }
		DatePickerDialog(
			onDismissRequest = {
				// Dismiss the dialog when the user clicks outside the dialog or on the back
				// button. If you want to disable that functionality, simply use an empty
				// onDismissRequest.
				setDialogState(false)
			},
			confirmButton = {
				TextButton(
					onClick = {
						val date =
							Instant.ofEpochMilli(datePickerState.selectedDateMillis!!).atZone(
								ZoneId.of("UTC")
							).toLocalDate()
						setDate(date)
						setDialogState(false)
					},
					enabled = confirmEnabled.value
				) {
					Text("OK")
				}
			},
			dismissButton = {
				TextButton(
					onClick = {
						setDialogState(false)
					}
				) {
					Text("Cancel")
				}
			}
		) {
			DatePicker(state = datePickerState)
		}
	}
}