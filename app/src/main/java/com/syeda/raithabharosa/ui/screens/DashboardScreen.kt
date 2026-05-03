package com.syeda.raithabharosa.ui.screens

import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.syeda.raithabharosa.viewmodel.WeatherViewModel
import androidx.compose.ui.res.stringResource
import com.syeda.raithabharosa.R
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController

@Composable
fun DashboardScreen(navController: NavHostController) {

    val context = LocalContext.current
    val prefs = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)

    val name = prefs.getString("name", "User") ?: "User"
    val location = prefs.getString("location", "Unknown") ?: "Unknown"
    val langCode = prefs.getString("lang", "en") ?: "en"

    val languageText = when (langCode) {
        "kn" -> "Kannada"
        "hi" -> "Hindi"
        else -> "English"
    }

    var city by remember { mutableStateOf(location) }

    val viewModel: WeatherViewModel = viewModel()

    val temperature by viewModel.temperature.collectAsState()
    val weatherType by viewModel.weatherType.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val feelsLike by viewModel.feelsLike.collectAsState()
    val humidity by viewModel.humidity.collectAsState()

    val icon = when (weatherType) {
        "Clear" -> "☀️"
        "Rain" -> "🌧️"
        "Clouds" -> "☁️"
        else -> "🌍"
    }

    // 🧠 AI RECOMMENDATION LOGIC (FIXED)
    val recommendation = when {
        weatherType == "Rain" -> "🌧️ Rain expected. Avoid spraying pesticides."
        humidity.toIntOrNull() ?: 0 > 80 -> "💧 High humidity. Risk of fungal diseases."
        temperature.replace("°C", "").toFloatOrNull() ?: 0f > 35 -> "🔥 High temperature. Increase irrigation."
        else -> "🌱 Conditions are good for farming activities."
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {

        Spacer(modifier = Modifier.height(20.dp))

        // 🔥 HEADER
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Column {
                Text(
                    text = "Welcome, $name",
                    style = MaterialTheme.typography.headlineMedium
                )

                Text(
                    text = "$location • $languageText",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Card(
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)
                )
            ) {
                Text(
                    text = "Farm Status: Good",
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // 🔥 WEATHER INPUT
        OutlinedTextField(
            value = city,
            onValueChange = { city = it },
            label = { Text(stringResource(R.string.enter_city)) },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))

        Button(
            onClick = {
                if (city.isNotBlank()) {
                    viewModel.getWeather(city)
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(stringResource(R.string.get_weather))
        }

        Spacer(modifier = Modifier.height(20.dp))

        // 🔵 WEATHER CARD
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)
            ),
            elevation = CardDefaults.cardElevation(6.dp)
        ) {
            Column(
                modifier = Modifier.padding(20.dp)
            ) {

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Column {
                        Text("Temperature")

                        if (isLoading) {
                            CircularProgressIndicator(modifier = Modifier.size(20.dp))
                        } else {
                            Text(
                                text = temperature,
                                style = MaterialTheme.typography.headlineMedium
                            )
                        }
                    }

                    Text(
                        text = icon,
                        style = MaterialTheme.typography.displaySmall
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                Text("Condition: $weatherType")
                Text("${stringResource(R.string.feels_like)}: $feelsLike")
                Text("${stringResource(R.string.humidity)}: $humidity")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // 🟢 AI RISK CARD
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.15f)
            )
        ) {
            Column(
                modifier = Modifier.padding(20.dp)
            ) {

                Text("Precipitation AI")

                Spacer(modifier = Modifier.height(6.dp))

                val risk = when (weatherType) {
                    "Rain" -> "High Risk"
                    "Clouds" -> "Moderate Risk"
                    else -> "Low Risk"
                }

                Text(
                    text = risk,
                    style = MaterialTheme.typography.headlineSmall
                )

                Spacer(modifier = Modifier.height(10.dp))

                val progress = when (risk) {
                    "High Risk" -> 0.9f
                    "Moderate Risk" -> 0.6f
                    else -> 0.3f
                }

                LinearProgressIndicator(
                    progress = progress,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // 🟤 SOIL HEALTH (NPK CARD)
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface
            ),
            elevation = CardDefaults.cardElevation(8.dp)
        ) {
            Column(
                modifier = Modifier.padding(20.dp)
            ) {

                Text("Soil Health")

                Spacer(modifier = Modifier.height(16.dp))

                val nitrogen = prefs.getInt("nitrogen", 0)
                val phosphorus = prefs.getInt("phosphorus", 0)
                val potassium = prefs.getInt("potassium", 0)

                NutrientRow("Nitrogen (N)", nitrogen)
                Spacer(modifier = Modifier.height(12.dp))

                NutrientRow("Phosphorus (P)", phosphorus)
                Spacer(modifier = Modifier.height(12.dp))

                NutrientRow("Potassium (K)", potassium)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // 🟡 AI RECOMMENDATION CARD
        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(8.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.secondary.copy(alpha = 0.1f)
            )
        ) {
            Column(
                modifier = Modifier.padding(20.dp)
            ) {

                Text("AI Recommendation")

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = recommendation,
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Features",
            style = MaterialTheme.typography.titleMedium
        )

        Spacer(modifier = Modifier.height(12.dp))

        FeatureItem(
            title = "Soil Testing AI",
            subtitle = "Analyze soil using image + description",
            icon = "🌱" ,
            onClick = { navController.navigate("soil") }
        )

        FeatureItem(
            title = "Raitha Assist",
            subtitle = "Ask farming questions",
            icon = "🤖",
            onClick = { }
        )

        FeatureItem(
            title = "Video Tutorials",
            subtitle = "Learn farming techniques",
            icon = "🎥",
            onClick = { }
        )

        FeatureItem(
            title = "Krishi Calendar",
            subtitle = "Track farming activities",
            icon = "📅",
            onClick = { }
        )

        Spacer(modifier = Modifier.height(30.dp))

// 🌾 CROP PROGRESS CARD
        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(8.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.White   // 👈 THIS FIXES GREY
            )
        ) {
            Column(
                modifier = Modifier.padding(20.dp)
            ) {

                Text(
                    text = "Crop Progress",
                    style = MaterialTheme.typography.titleMedium
                )

                Spacer(modifier = Modifier.height(16.dp))

                val cropName = "Paddy"        // 🔁 will come from user later
                val cropStage = "Growing"    // 🔁 dynamic later
                val progress = 0.6f          // 60%

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = cropName, style = MaterialTheme.typography.bodyLarge)
                    Text(text = "${(progress * 100).toInt()}%")
                }

                Spacer(modifier = Modifier.height(6.dp))

                LinearProgressIndicator(
                    progress = progress,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = "Stage: $cropStage",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
        Spacer(modifier = Modifier.height(100.dp))
    }
}

@Composable
fun NutrientRow(label: String, value: Int) {

    Column {

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(label)
            Text("$value%")
        }

        Spacer(modifier = Modifier.height(6.dp))

        LinearProgressIndicator(
            progress = value / 100f,
            modifier = Modifier.fillMaxWidth()
        )
    }
}
@Composable
fun FeatureItem(
    title: String,
    subtitle: String,
    icon: String,
    onClick: () -> Unit
) {
    Card(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        elevation = CardDefaults.cardElevation(6.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White   // 👈 THIS FIXES GREY
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {

            Row(verticalAlignment = Alignment.CenterVertically) {

                Text(icon, style = MaterialTheme.typography.headlineSmall)

                Spacer(modifier = Modifier.width(12.dp))

                Column {
                    Text(title)
                    Text(
                        subtitle,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            Text("➡️")
        }
    }
}