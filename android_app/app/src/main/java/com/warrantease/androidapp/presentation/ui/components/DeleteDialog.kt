package com.warrantease.androidapp.presentation.ui.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import com.warrantease.androidapp.presentation.ui.theme.AppTheme

@Composable
fun DeleteDialog(setIsDialogOpen: (Boolean) -> Unit, onConfirm: () -> Unit) {
	AlertDialog(
		onDismissRequest = {
			setIsDialogOpen(false)
		},
		title = {
			Text(text = "Delete warranty")
		},
		text = {
			Text(text = "Are you sure you want to delete this warranty?")
		},
		confirmButton = {
			TextButton(
				onClick = {
					onConfirm()
					setIsDialogOpen(false)
				}
			) {
				Text(
					text = "Confirm",
					color = AppTheme.primary600
				)
			}
		},
		dismissButton = {
			TextButton(
				onClick = {
					setIsDialogOpen(false)
				}
			) {
				Text(
					text = "Dismiss",
					color = AppTheme.neutral600
				)
			}
		}

	)
}