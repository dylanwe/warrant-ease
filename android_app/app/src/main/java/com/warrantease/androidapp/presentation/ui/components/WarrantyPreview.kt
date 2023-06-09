package com.warrantease.androidapp.presentation.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.warrantease.androidapp.R
import com.warrantease.androidapp.domain.model.Warranty
import com.warrantease.androidapp.presentation.ui.theme.AppTheme
import com.warrantease.androidapp.presentation.ui.utils.RelativeTime
import java.time.LocalDate

@Composable
fun WarrantPreview(warranty: Warranty, navController: NavController) {
	var isBottomSheetOpen by rememberSaveable { mutableStateOf(false) }

	Column {

		if (isBottomSheetOpen) {
			WarrantyBottomSheet(
				warranty = warranty,
				navController = navController,
				changeOpenSheetState = {
					isBottomSheetOpen = it
				})
		}

		val isWarrantyExpired = LocalDate.now() >= warranty.expirationDate

		Row(
			modifier = Modifier
				.fillMaxWidth()
				.clip(RoundedCornerShape(10.dp))
				.border(width = 1.dp, AppTheme.neutral300, shape = RoundedCornerShape(10.dp))
				.clickable { isBottomSheetOpen = true }
				.background(AppTheme.white)
				.padding(horizontal = 18.dp, vertical = 24.dp),
			horizontalArrangement = Arrangement.SpaceBetween,
		) {
			Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
				Text(
					text = warranty.name,
					fontWeight = FontWeight.Bold,
					color = AppTheme.neutral800
				)
				Row(horizontalArrangement = Arrangement.spacedBy(22.dp)) {
					Row(
						horizontalArrangement = Arrangement.spacedBy(6.dp),
						verticalAlignment = Alignment.CenterVertically
					) {
						Icon(
							painter = painterResource(id = R.drawable.baseline_access_time_24),
							contentDescription = "expiration",
							tint = if (isWarrantyExpired) AppTheme.red400 else AppTheme.neutral400,
							modifier = Modifier.size(18.dp)
						)
						Text(
							text = RelativeTime.getRelativeTime(warranty.expirationDate),
							color = if (isWarrantyExpired) AppTheme.red400 else AppTheme.neutral400,
							fontSize = 14.sp
						)
					}
					if (warranty.store.isNotBlank()) {
						Row(
							horizontalArrangement = Arrangement.spacedBy(6.dp),
							verticalAlignment = Alignment.CenterVertically
						) {
							Icon(
								painter = painterResource(id = R.drawable.baseline_storefront_24),
								contentDescription = "store",
								tint = AppTheme.neutral400,
								modifier = Modifier.size(18.dp)
							)
							Text(
								text = warranty.store,
								color = AppTheme.neutral400,
								fontSize = 14.sp,
								maxLines = 1,
								overflow = TextOverflow.Ellipsis
							)
						}
					}
				}
			}
			Icon(
				imageVector = Icons.Default.ArrowForward,
				contentDescription = "Enter",
				tint = AppTheme.neutral800
			)
		}
	}
}