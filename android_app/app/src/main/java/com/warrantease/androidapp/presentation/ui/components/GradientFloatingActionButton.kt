package com.warrantease.androidapp.presentation.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.warrantease.androidapp.presentation.ui.theme.AppTheme

@Composable
fun GradientFloatingActionButton(icon: ImageVector, onClick: () -> Unit) {
	Button(
		colors = ButtonDefaults.buttonColors(
			containerColor = Color.Transparent,
		),
		contentPadding = PaddingValues(0.dp),
		onClick = { onClick() },
		shape = RoundedCornerShape(18.dp),
		modifier = Modifier
			.shadow(elevation = 4.dp, shape = RoundedCornerShape(18.dp)),
	) {
		Box(
			modifier = Modifier
				.background(AppTheme.primaryGradient)
				.padding(16.dp),
			contentAlignment = Alignment.Center
		) {
			Icon(imageVector = icon, contentDescription = "", tint = AppTheme.neutral950)
		}
	}
}