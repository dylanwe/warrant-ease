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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.warrantease.androidapp.R
import com.warrantease.androidapp.presentation.ui.theme.AndroidAppTheme
import com.warrantease.androidapp.presentation.ui.theme.AppTheme

@Composable
fun WarrantPreview() {
    val title = "Macbook Pro 14-Ich"
    val date = "20-04-2024"
    val site = "www.apple.com"

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
            .border(width = 1.dp, AppTheme.neutral300, shape = RoundedCornerShape(10.dp))
            .clickable {  }
            .background(AppTheme.white)
            .padding(horizontal = 18.dp, vertical = 24.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text(text = title, fontWeight = FontWeight.Bold, color = AppTheme.neutral800)
            Row(horizontalArrangement = Arrangement.spacedBy(22.dp)) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_access_time_24),
                        contentDescription = "expiration",
                        tint = AppTheme.neutral400,
                        modifier = Modifier.size(18.dp)
                    )
                    Text(text = date, color = AppTheme.neutral400, fontSize = 14.sp)
                }
                Row(
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_storefront_24),
                        contentDescription = "expiration",
                        tint = AppTheme.neutral400,
                        modifier = Modifier.size(18.dp)
                    )
                    Text(text = site, color = AppTheme.neutral400, fontSize = 14.sp)
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

@Preview
@Composable
private fun Preview() {
    AndroidAppTheme {
        WarrantPreview()
    }
}