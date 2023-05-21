package com.warrantease.androidapp.presentation.ui.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.ramcosta.composedestinations.annotation.Destination
import com.warrantease.androidapp.R
import com.warrantease.androidapp.presentation.ui.components.BottomNav
import com.warrantease.androidapp.presentation.ui.components.WarrantPreview
import com.warrantease.androidapp.presentation.ui.theme.AppTheme
import com.warrantease.androidapp.presentation.viewmodel.WarrantyViewModel
import com.warrantease.androidapp.presentation.viewmodel.uiState.UIState
import org.koin.androidx.compose.koinViewModel

@Destination
@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: WarrantyViewModel = koinViewModel()
) {
    viewModel.getWarranties()
    val warranties by viewModel.warranties.collectAsState()
    val uiState by viewModel.state.collectAsState()
    val user = Firebase.auth.currentUser!!

    Content(navController = navController, user = user) {
        when (uiState) {
            UIState.NORMAL -> {
                LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    items(items = warranties) { warranty ->
                        WarrantPreview(warranty)
                    }
                }
            }

            UIState.EMPTY -> {
                Text(text = "Empty")
            }

            UIState.LOADING -> {
                Text(text = "Loading")
            }

            UIState.ERROR -> {
                Text(text = "Error")
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun Content(
    navController: NavController,
    user: FirebaseUser,
    content: @Composable (() -> Unit)
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(text = "Home") },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = AppTheme.white
                )
            )
        },
        bottomBar = { BottomNav(navController) }
    ) { innerPadding ->
        Column(
            modifier = Modifier.padding(
                top = innerPadding.calculateTopPadding(),
                bottom = innerPadding.calculateBottomPadding(),
                start = 18.dp,
                end = 18.dp
            )
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.outline_wb_sunny_24),
                    contentDescription = "time icon",
                    tint = AppTheme.neutral500,
                    modifier = Modifier.size(18.dp)
                )
                Text(text = "Good Morning,", fontSize = 16.sp, color = AppTheme.neutral500)
            }
            Text(
                text = "${user.displayName}",
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp
            )

            Spacer(modifier = Modifier.height(20.dp))

            content()
        }
    }
}