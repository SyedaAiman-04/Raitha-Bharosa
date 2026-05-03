package com.syeda.raithabharosa.ui.screens

import androidx.compose.foundation.layout.*
import androidx.navigation.NavHostController
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import android.content.Context
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.ui.platform.LocalContext

@Composable
fun OnboardingScreen(navController: NavHostController) {

    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var location by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ){

        Spacer(modifier = Modifier.height(40.dp))

        // 🌿 ICON
        Text(
            text = "🌿",
            style = MaterialTheme.typography.displayMedium
        )

        Spacer(modifier = Modifier.height(12.dp))

        // APP NAME
        Text(
            text = "Raitha App",
            style = MaterialTheme.typography.headlineMedium
        )

        // SUBTITLE
        Text(
            text = "AGRICULTURAL AI AGENT",
            style = MaterialTheme.typography.bodySmall
        )

        Spacer(modifier = Modifier.height(30.dp))

        // 📦 CARD
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface
            ),
            elevation = CardDefaults.cardElevation(8.dp)
        ) {
            Column(
                modifier = Modifier
                    .padding(20.dp)
            ) {

                Text("FULL NAME")
                Spacer(modifier = Modifier.height(6.dp))

                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    placeholder = { Text("e.g: Rajesh Patil") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text("EMAIL ID (GMAIL)")
                Spacer(modifier = Modifier.height(6.dp))

                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    placeholder = { Text("e.g: rajesh.p@gmail.com") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text("YOUR LOCATION (DISTRICT)")
                Spacer(modifier = Modifier.height(6.dp))

                OutlinedTextField(
                    value = location,
                    onValueChange = { location = it },
                    placeholder = { Text("e.g. Hassan") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(24.dp))

                val context = LocalContext.current
                Button(
                    onClick = {
                        if (name.isNotBlank() && location.isNotBlank()) {

                            // 🔥 SAVE DATA HERE
                            val prefs = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)

                            prefs.edit()
                                .putString("name", name)
                                .putString("location", location)
                                .apply()

                            // 👉 THEN NAVIGATE
                            navController.navigate("language")
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                ) {
                    Text("GET STARTED")
                }
            }
        }
    }
}