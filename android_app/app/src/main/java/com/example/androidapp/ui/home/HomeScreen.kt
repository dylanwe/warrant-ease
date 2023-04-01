package com.example.androidapp.ui.home

import android.util.Log
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
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.androidapp.viewmodel.WarrantyViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: WarrantyViewModel = viewModel()
) {
    viewModel.getExample()
    val example by viewModel.example.observeAsState()
    val user = Firebase.auth.currentUser!!

    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Hello,",
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.titleMedium,
                fontSize = 30.sp
            )
            if (example == null) {
                Text(text = "Loading...")
            } else {
                Text(text = example!!.toString())
            }

            Spacer(modifier = Modifier.height(10.dp))
            Text(text = user.email ?: "")
            Button(onClick = {
                viewModel.getExample()
            }) {
                Text(text = "get example")
            }
            Button(onClick = {
                Firebase.auth.signOut()
            }) {
                Text(text = "Sign out")
            }
        }
    }
}
