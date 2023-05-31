package com.warrantease.androidapp.presentation.ui.sign_in

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp

@Composable
fun GoogleSignInButton(
    text: String,
    loadingText: String = "Signing in...",
    icon: Painter,
    isLoading: Boolean = false,
    shape: Shape = RoundedCornerShape(5.dp),
    borderColor: Color = MaterialTheme.colorScheme.outline,
    backgroundColor: Color = MaterialTheme.colorScheme.background,
    progressIndicatorColor: Color = MaterialTheme.colorScheme.primary,
    googleSignIn: () -> Unit,
) {
	Surface(
		modifier = Modifier
            .clickable(
                enabled = !isLoading,
                onClick = googleSignIn
            )
            .fillMaxWidth(),
		shape = shape,
		border = BorderStroke(width = 1.dp, color = borderColor),
		color = backgroundColor
	) {
		Row(
			modifier = Modifier
                .padding(
                    start = 12.dp,
                    end = 16.dp,
                    top = 12.dp,
                    bottom = 12.dp
                )
                .fillMaxWidth()
                .animateContentSize(
                    animationSpec = tween(
                        durationMillis = 300,
                        easing = LinearOutSlowInEasing
                    )
                ),
			verticalAlignment = Alignment.CenterVertically,
			horizontalArrangement = Arrangement.Center,
		) {
			Icon(
				painter = icon,
				contentDescription = "SignInButton",
				tint = Color.Unspecified
			)
			Spacer(modifier = Modifier.width(8.dp))

			Text(text = if (isLoading) loadingText else text)
			if (isLoading) {
				Spacer(modifier = Modifier.width(16.dp))
				CircularProgressIndicator(
					modifier = Modifier
                        .height(16.dp)
                        .width(16.dp),
					strokeWidth = 2.dp,
					color = progressIndicatorColor
				)
			}
		}
	}
}
