package com.warrantease.androidapp.presentation.ui.home

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.ramcosta.composedestinations.annotation.Destination
import com.warrantease.androidapp.presentation.viewmodel.WarrantyViewModel
import org.koin.androidx.compose.koinViewModel

@Destination
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: WarrantyViewModel = koinViewModel()
) {
    viewModel.getExample()
    val example by viewModel.example.observeAsState()
    val user = Firebase.auth.currentUser!!

    Scaffold { innerPadding ->
        Column(
            modifier = Modifier.padding(
                top = innerPadding.calculateTopPadding(),
                bottom = innerPadding.calculateBottomPadding(),
                start = 18.dp,
                end = 18.dp
            )
        ) {
            Text(
                text = "Hello, ${user.displayName}",
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.titleMedium,
                fontSize = 30.sp
            )
            Text(text = viewModel.hello)
            if (example == null) {
                Text(text = "Loading...")
            } else {
                Text(text = example!!.toString())
            }

            Spacer(modifier = Modifier.height(10.dp))
            Text(text = user.email ?: "")
            Spacer(modifier = Modifier.height(10.dp))

            Button(onClick = {
                Firebase.auth.signOut()
            }) {
                Text(text = "Sign out")
            }
        }
    }
}
