package com.syeda.raithabharosa.ui.screens

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.syeda.raithabharosa.R
import com.syeda.raithabharosa.ui.theme.*
import com.syeda.raithabharosa.viewmodel.WeatherViewModel
import androidx.compose.ui.graphics.StrokeCap

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(navController: NavHostController) {
    val context = LocalContext.current
    val prefs = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)

    val name = prefs.getString("name", "Farmer") ?: "Farmer"
    val location = prefs.getString("location", "Unknown") ?: "Unknown"
    val langCode = prefs.getString("lang", "en") ?: "en"

    val languageText = when (langCode) {
        "kn" -> "ಕನ್ನಡ"
        "hi" -> "हिंदी"
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

    val recommendation = when {
        weatherType == "Rain" -> "🌧️ Rain expected. Avoid spraying pesticides today."
        humidity.toIntOrNull() ?: 0 > 80 -> "💧 High humidity. Keep an eye out for fungal diseases."
        temperature.replace("°C", "").toFloatOrNull() ?: 0f > 35 -> "🔥 High temperature. Increase irrigation schedules."
        else -> "🌱 Conditions are excellent for active farming tasks."
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(GreenLight.copy(alpha = 0.15f), MaterialTheme.colorScheme.background)
                )
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Spacer(modifier = Modifier.height(40.dp))

            // ✨ MODERNIZED HEADER AREA
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.Top
            ) {
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = "Vanakkam, $name 👋",
                        style = MaterialTheme.typography.headlineSmall.copy(
                            fontWeight = FontWeight.Bold,
                            color = EarthyText
                        )
                    )
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(top = 2.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.LocationOn,
                            contentDescription = null,
                            tint = GreenDark,
                            modifier = Modifier.size(14.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "${location.uppercase()} • $languageText",
                            style = MaterialTheme.typography.labelMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = SoftGray,
                                letterSpacing = 0.5.sp
                            )
                        )
                    }
                }

                Surface(
                    shape = RoundedCornerShape(18.dp),

                    color = Color(0xFFE8F5E9),

                    shadowElevation = 2.dp,

                    modifier = Modifier.padding(start = 12.dp)
                ) {

                    Row(
                        modifier = Modifier.padding(
                            horizontal = 14.dp,
                            vertical = 10.dp
                        ),

                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Box(
                            modifier = Modifier
                                .size(10.dp)

                                .background(
                                    Color(0xFF4CAF50),
                                    CircleShape
                                )
                        )

                        Spacer(modifier = Modifier.width(8.dp))

                        Column {

                            Text(
                                text = "Farm Status",

                                style =
                                    MaterialTheme.typography.labelSmall.copy(
                                        color = SoftGray,
                                        fontWeight = FontWeight.Medium
                                    )
                            )

                            Spacer(modifier = Modifier.height(2.dp))

                            Text(
                                text = "🟢 Good",

                                style =
                                    MaterialTheme.typography.bodySmall.copy(
                                        fontWeight = FontWeight.Bold,
                                        color = GreenDark
                                    )
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // 🔍 PREMIUM WEATHER INPUT BAR
            OutlinedTextField(
                value = city,
                onValueChange = { city = it },
                placeholder = { Text(stringResource(R.string.enter_city), color = SoftGray) },
                singleLine = true,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                shape = RoundedCornerShape(16.dp),
                trailingIcon = {
                    IconButton(
                        onClick = { if (city.isNotBlank()) viewModel.getWeather(city) },
                        modifier = Modifier
                            .padding(end = 6.dp)
                            .background(GreenDark, RoundedCornerShape(12.dp))
                            .size(38.dp)
                    ) {
                        Icon(imageVector = Icons.Default.Search, contentDescription = "Search", tint = Color.White)
                    }
                },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = GreenDark,
                    unfocusedBorderColor = Color.Transparent,
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White
                ),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(20.dp))

            // 📊 MODERN BALANCED WEATHER & RISK ROW GRID
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                // Weather Summary Panel
                ElevatedCard(
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(24.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text("Weather", style = MaterialTheme.typography.labelLarge.copy(color = SoftGray, fontWeight = FontWeight.Bold))
                            Text(icon, fontSize = 24.sp)
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        if (isLoading) {
                            CircularProgressIndicator(modifier = Modifier.size(24.dp), color = GreenDark)
                        } else {
                            Text(text = temperature, style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold, color = EarthyText))
                        }

                        Text(text = weatherType, style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold, color = GreenDark))
                        Spacer(modifier = Modifier.height(8.dp))
                        Text("Feels: $feelsLike", style = MaterialTheme.typography.labelSmall.copy(color = SoftGray))
                        Text("Humidity: $humidity", style = MaterialTheme.typography.labelSmall.copy(color = SoftGray))
                    }
                }

                // AI Risk Panel
                ElevatedCard(
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(24.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text("Precipitation AI", style = MaterialTheme.typography.labelLarge.copy(color = SoftGray, fontWeight = FontWeight.Bold))

                        Spacer(modifier = Modifier.height(16.dp))

                        val risk = when (weatherType) {
                            "Rain" -> "High Risk"
                            "Clouds" -> "Moderate"
                            else -> "Low Risk"
                        }
                        val riskColor = when(risk) {
                            "High Risk" -> Color(0xFFE53935)
                            "Moderate" -> Color(0xFFFFB300)
                            else -> Color(0xFF43A047)
                        }

                        Text(text = risk, style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold, color = riskColor))
                        Spacer(modifier = Modifier.height(12.dp))

                        val progress = when (risk) {
                            "High Risk" -> 0.9f
                            "Moderate" -> 0.6f
                            else -> 0.3f
                        }
                        LinearProgressIndicator(
                            progress = { progress },
                            color = riskColor,
                            trackColor = riskColor.copy(alpha = 0.15f),
                            strokeCap = StrokeCap.Round,
                            modifier = Modifier.fillMaxWidth().height(6.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // 🧠 ADVANCED INSIGHT RECOMMENDATION CARD
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = GreenDark.copy(alpha = 0.07f))
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Surface(modifier = Modifier.size(44.dp), shape = CircleShape, color = Color.White) {
                        Box(contentAlignment = Alignment.Center) { Text("✨", fontSize = 20.sp) }
                    }
                    Spacer(modifier = Modifier.width(14.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text("AI Smart Advisor", style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold, color = GreenDark))
                        Text(text = recommendation, style = MaterialTheme.typography.bodyMedium.copy(color = EarthyText, fontWeight = FontWeight.Medium))
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // 🚀 APP QUICK LINKS SECTION (SCROLLABLE ROW INSPIRED CHIPS)
            Text(text = "Core Toolsets", style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold, color = EarthyText))
            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                FeatureChip(title = "Soil AI", subtitle = "Analyze Mix", icon = "🌱") { navController.navigate("soil") }
                FeatureChip(title = "Raitha Chat", subtitle = "AI Assistant", icon = "🤖") { navController.navigate("assist") }
                FeatureChip(title = "Tutorials", subtitle = "Learn Techniques", icon = "🎥") {navController.navigate("calendar")}
                FeatureChip(title = "Krishi Logs", subtitle = "Track Work", icon = "📅") { navController.navigate("calendar")
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // 🟤 SOIL HEALTH DEEP ANALYTICS CONTAINER
            ElevatedCard(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Text("Live Soil Nutrients", style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold, color = EarthyText))
                    Spacer(modifier = Modifier.height(16.dp))

                    val nitrogen = prefs.getInt("nitrogen", 0)
                    val phosphorus = prefs.getInt("phosphorus", 0)
                    val potassium = prefs.getInt("potassium", 0)

                    NutrientRow("Nitrogen (N)", nitrogen, Color(0xFF1E88E5))
                    Spacer(modifier = Modifier.height(14.dp))
                    NutrientRow("Phosphorus (P)", phosphorus, Color(0xFFD81B60))
                    Spacer(modifier = Modifier.height(14.dp))
                    NutrientRow("Potassium (K)", potassium, Color(0xFF8E24AA))
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // 🌾 CROP MONITORING TARGET CARD
            ElevatedCard(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = "Active Crop Tracker", style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold, color = EarthyText))
                        Surface(color = Color(0xFFFFF3E0), shape = RoundedCornerShape(8.dp)) {
                            Text("Growing Stage", color = Color(0xFFE65100), style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold), modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp))
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    val cropName = "Paddy Field"
                    val progress = 0.6f

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.Bottom
                    ) {
                        Text(text = cropName, style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.SemiBold, color = EarthyText))
                        Text(text = "${(progress * 100).toInt()}% Complete", style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold, color = GreenDark))
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    LinearProgressIndicator(
                        progress = { progress },
                        color = GreenDark,
                        trackColor = GreenLight.copy(alpha = 0.3f),
                        strokeCap = StrokeCap.Round,
                        modifier = Modifier.fillMaxWidth().height(8.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(120.dp))
        }
    }
}

@Composable
fun NutrientRow(label: String, value: Int, color: Color) {
    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(label, style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Medium, color = EarthyText))
            Text("$value%", style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold, color = color))
        }

        Spacer(modifier = Modifier.height(6.dp))

        LinearProgressIndicator(
            progress = { value / 100f },
            color = color,
            trackColor = color.copy(alpha = 0.1f),
            strokeCap = StrokeCap.Round,
            modifier = Modifier.fillMaxWidth().height(6.dp)
        )
    }
}

@Composable
fun FeatureChip(
    title: String,
    subtitle: String,
    icon: String,
    onClick: () -> Unit
) {

    ElevatedCard(
        onClick = {
            onClick()
        },

        modifier = Modifier.width(150.dp),

        shape = RoundedCornerShape(24.dp),

        elevation = CardDefaults.elevatedCardElevation(
            defaultElevation = 4.dp
        ),

        colors = CardDefaults.elevatedCardColors(
            containerColor = Color.White
        )
    ) {

        Column(
            modifier = Modifier.padding(18.dp)
        ) {

            Surface(
                modifier = Modifier.size(48.dp),

                shape = RoundedCornerShape(16.dp),

                color = GreenDark.copy(alpha = 0.12f)
            ) {

                Box(
                    contentAlignment = Alignment.Center
                ) {

                    Text(
                        text = icon,
                        fontSize = 24.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(18.dp))

            Text(
                text = title,

                style =
                    MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.ExtraBold,
                        color = EarthyText
                    )
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = subtitle,

                style =
                    MaterialTheme.typography.bodySmall.copy(
                        color = SoftGray
                    )
            )
        }
    }
}