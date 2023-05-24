package com.warrantease.androidapp.presentation.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.warrantease.androidapp.R
import com.warrantease.androidapp.domain.model.Warranty
import com.warrantease.androidapp.presentation.ui.theme.AppTheme
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WarrantyBottomSheet(warranty: Warranty, changeOpenSheetState: (Boolean) -> Unit) {
	var skipPartiallyExpanded by remember { mutableStateOf(false) }
	val scope = rememberCoroutineScope()
	val bottomSheetState = rememberModalBottomSheetState(
		skipPartiallyExpanded = skipPartiallyExpanded
	)
	val dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")

	val daysLeft = ChronoUnit.DAYS.between(LocalDate.now(), warranty.expirationDate)

	// Sheet content
	ModalBottomSheet(
		onDismissRequest = { changeOpenSheetState(false) },
		sheetState = bottomSheetState,
		modifier = Modifier.padding(horizontal = 6.dp),
		containerColor = AppTheme.neutral50
	) {
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
				IconButton(
					// Note: If you provide logic outside of onDismissRequest to remove the sheet,
					// you must additionally handle intended state cleanup, if any.
					onClick = {
						scope.launch { bottomSheetState.hide() }.invokeOnCompletion {
							if (!bottomSheetState.isVisible) {
								changeOpenSheetState(false)
							}
						}
					}
				) {
					Icon(imageVector = Icons.Default.Close, "Close")
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
					.defaultMinSize(minHeight = 260.dp)
			) {
				Text(
					text = stringResource(id = R.string.warranty_detail_notes),
					style = MaterialTheme.typography.titleLarge,
					fontWeight = FontWeight.Bold
				)
				Text(
					text = warranty.notes,
					color = AppTheme.neutral500,
					maxLines = 1,
					overflow = TextOverflow.Ellipsis
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