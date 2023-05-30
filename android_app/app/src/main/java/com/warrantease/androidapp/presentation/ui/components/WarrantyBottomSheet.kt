package com.warrantease.androidapp.presentation.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import com.warrantease.androidapp.R
import com.warrantease.androidapp.domain.model.Warranty
import com.warrantease.androidapp.presentation.ui.theme.AppTheme
import com.warrantease.androidapp.presentation.viewmodel.HomeViewModel
import com.warrantease.androidapp.presentation.viewmodel.WarrantyViewModel
import org.koin.androidx.compose.koinViewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WarrantyBottomSheet(
	warranty: Warranty,
	changeOpenSheetState: (Boolean) -> Unit,
	warrantyViewModel: WarrantyViewModel = koinViewModel(),
	homeViewModel: HomeViewModel = koinViewModel(),
) {
	val skipPartiallyExpanded by remember { mutableStateOf(false) }
	val bottomSheetState = rememberModalBottomSheetState(
		skipPartiallyExpanded = skipPartiallyExpanded
	)
	val dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")
	val daysLeft = ChronoUnit.DAYS.between(LocalDate.now(), warranty.expirationDate)
	var expanded by remember { mutableStateOf(false) }
	var isDialogOpen by remember { mutableStateOf(false) }

	// Sheet content
	ModalBottomSheet(
		onDismissRequest = { changeOpenSheetState(false) },
		sheetState = bottomSheetState,
		modifier = Modifier.padding(horizontal = 6.dp),
		containerColor = AppTheme.neutral50
	) {
		if (isDialogOpen) {
			DeleteDialog(
				setIsDialogOpen = { isDialogOpen = it },
				onConfirm = {
					warrantyViewModel.deleteWarranty(warranty.id, onComplete = {
						warrantyViewModel.getWarranties()
						homeViewModel.getTopWarranties()
					})
					changeOpenSheetState(false)
				}
			)
		}

		Column(
			modifier = Modifier
				.fillMaxWidth()
		) {
			Row(
				modifier = Modifier
					.fillMaxWidth()
					.padding(horizontal = 21.dp),
				horizontalArrangement = Arrangement.SpaceBetween,
				verticalAlignment = Alignment.CenterVertically
			) {
				Text(
					text = warranty.name,
					style = MaterialTheme.typography.headlineMedium,
					fontWeight = FontWeight.Bold,
					maxLines = 1,
					overflow = TextOverflow.Ellipsis
				)
				Box(
					contentAlignment = Alignment.Center
				) {
					IconButton(onClick = {
						expanded = true
					}) {
						Icon(
							imageVector = Icons.Default.MoreVert,
							contentDescription = "Open Options"
						)
					}
					DropdownMenu(
						expanded = expanded,
						onDismissRequest = {
							expanded = false
						},
						offset = DpOffset(x = 20.dp, y = 70.dp)
					) {
						DropdownMenuItem(
							text = { Text("Edit") },
							onClick = { /* Handle edit! */ },
							leadingIcon = {
								Icon(
									Icons.Outlined.Edit,
									contentDescription = null
								)
							}
						)
						DropdownMenuItem(
							text = { Text("Delete") },
							onClick = { isDialogOpen = true },
							leadingIcon = {
								Icon(
									Icons.Outlined.Delete,
									contentDescription = null
								)
							}
						)
					}
				}
			}

			Spacer(modifier = Modifier.height(24.dp))

			RowSection(
				name = stringResource(id = R.string.warranty_detail_buy_date),
				value = warranty.buyDate.format(dateFormatter),
				icon = painterResource(id = R.drawable.baseline_calendar_month_24),
				backgroundColor = AppTheme.neutral100
			)
			RowSection(
				name = stringResource(id = R.string.warranty_detail_expiration_date),
				value = warranty.expirationDate.format(dateFormatter),
				icon = painterResource(id = R.drawable.baseline_edit_calendar_24),
			)
			RowSection(
				name = stringResource(id = R.string.warranty_detail_days_left),
				value = daysLeft.toString(),
				icon = painterResource(id = R.drawable.baseline_timelapse_24),
				backgroundColor = AppTheme.neutral100
			)
			RowSection(
				name = stringResource(id = R.string.warranty_detail_store),
				value = warranty.store,
				icon = painterResource(id = R.drawable.baseline_storefront_24),
			)

			Spacer(modifier = Modifier.height(24.dp))

			Column(
				modifier = Modifier
					.padding(horizontal = 21.dp)
					.fillMaxWidth()
					.defaultMinSize(minHeight = 260.dp),
				verticalArrangement = Arrangement.spacedBy(12.dp)
			) {
				Text(
					text = stringResource(id = R.string.warranty_detail_notes),
					style = MaterialTheme.typography.titleLarge,
					fontWeight = FontWeight.Bold
				)
				Text(
					text = warranty.notes,
					color = AppTheme.neutral500
				)
			}
		}

		Spacer(modifier = Modifier.height(64.dp))
	}
}

@Composable
private fun RowSection(
	name: String,
	value: String,
	icon: Painter,
	backgroundColor: Color = AppTheme.neutral50,
) {
	Row(
		modifier = Modifier
			.fillMaxWidth()
			.background(backgroundColor)
			.padding(vertical = 8.dp, horizontal = 21.dp),
		verticalAlignment = Alignment.CenterVertically,
	) {
		Row(
			horizontalArrangement = Arrangement.spacedBy(8.dp),
			modifier = Modifier.weight(0.5f)
		) {
			Icon(
				painter = icon,
				contentDescription = name,
				tint = AppTheme.neutral500
			)
			Text(
				text = name,
				color = AppTheme.neutral500
			)
		}
		Text(
			text = value,
			fontWeight = FontWeight.Medium,
			modifier = Modifier.weight(0.5f),
			overflow = TextOverflow.Ellipsis
		)
	}
}