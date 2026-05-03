package com.syeda.raithabharosa.ui.screens

import android.content.Context
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack

@Composable
fun SoilTestingScreen(navController: NavHostController) {

    val context = LocalContext.current
    val prefs = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)

    var imageUri by remember { mutableStateOf<Uri?>(null) }
    var description by remember { mutableStateOf("") }

    var nitrogen by remember { mutableStateOf(45f) }
    var phosphorus by remember { mutableStateOf(30f) }
    var potassium by remember { mutableStateOf(25f) }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        imageUri = uri
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {

        // 🔙 HEADER
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = {
                navController.popBackStack()
            }) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back",
                    modifier = Modifier.size(28.dp) // 👈 increase size here
                )
            }

            Text(
                text = "Soil Testing AI",
                style = MaterialTheme.typography.headlineMedium
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        // 📸 CAMERA CARD
        Card(modifier = Modifier.fillMaxWidth()) {
            Column(
                modifier = Modifier.padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Button(onClick = { launcher.launch("image/*") }) {
                    Text("Take / Upload Photo")
                }

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = if (imageUri != null) "Image Selected ✅" else "No image selected"
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // 📝 DESCRIPTION
        Text("Soil Description")

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = description,
            onValueChange = { description = it },
            placeholder = { Text("Describe color, texture...") },
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp)
        )

        Spacer(modifier = Modifier.height(20.dp))

        // 🤖 TEST BUTTON (UPDATED FLOW)
        Button(
            onClick = {

                if (imageUri == null && description.isBlank()) {
                    Toast.makeText(
                        context,
                        "Please upload image or enter description",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {

                    // 🧠 AI SIMULATION
                    val soilType =
                        if (description.contains("red", true)) "Red Soil"
                        else if (description.contains("black", true)) "Black Soil"
                        else "Loamy Soil"

                    val analysis =
                        when (soilType) {
                            "Red Soil" -> "Well-drained but low in nutrients."
                            "Black Soil" -> "Retains moisture, good for cotton."
                            else -> "Balanced soil suitable for many crops."
                        }

                    val crops =
                        when (soilType) {
                            "Red Soil" -> "Groundnut,Ragi,Millets,Pulses"
                            "Black Soil" -> "Cotton,Wheat,Soybean"
                            else -> "Rice,Vegetables"
                        }

                    val process =
                        when (soilType) {
                            "Red Soil" -> "Add compost and irrigate frequently."
                            "Black Soil" -> "Avoid overwatering, ensure drainage."
                            else -> "Maintain balanced fertilization."
                        }

                    // 🚀 NAVIGATE TO RESULT SCREEN
                    navController.navigate(
                        "soil_result/$soilType/$analysis/$crops/$process"
                    )
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Test My Soil")
        }

        Spacer(modifier = Modifier.height(24.dp))

        // 🟤 UPDATE SOIL DATA CARD (MANUAL OVERRIDE)
        Card(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.padding(20.dp)) {

                Text("Update Soil Data")

                Spacer(modifier = Modifier.height(20.dp))

                Text("Nitrogen: ${nitrogen.toInt()}%")
                Slider(
                    value = nitrogen,
                    onValueChange = { nitrogen = it },
                    valueRange = 0f..100f
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text("Phosphorus: ${phosphorus.toInt()}%")
                Slider(
                    value = phosphorus,
                    onValueChange = { phosphorus = it },
                    valueRange = 0f..100f
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text("Potassium: ${potassium.toInt()}%")
                Slider(
                    value = potassium,
                    onValueChange = { potassium = it },
                    valueRange = 0f..100f
                )

                Spacer(modifier = Modifier.height(20.dp))

                Button(
                    onClick = {
                        prefs.edit()
                            .putInt("nitrogen", nitrogen.toInt())
                            .putInt("phosphorus", phosphorus.toInt())
                            .putInt("potassium", potassium.toInt())
                            .apply()

                        Toast.makeText(context, "Soil data updated!", Toast.LENGTH_SHORT).show()

                        navController.navigate("dashboard") {
                            popUpTo("soil") { inclusive = true }
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Update Soil Levels")
                }
            }
        }

        Spacer(modifier = Modifier.height(100.dp))
    }
}