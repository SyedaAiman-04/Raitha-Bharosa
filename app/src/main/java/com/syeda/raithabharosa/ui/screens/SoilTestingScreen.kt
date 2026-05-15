package com.syeda.raithabharosa.ui.screens

import android.content.Context
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import coil.compose.AsyncImage

@OptIn(ExperimentalMaterial3Api::class)
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

    // High-class, subtle earthy background gradient palette
    val backgroundGradient = Brush.verticalGradient(
        colors = listOf(Color(0xFFF1F8E9), Color(0xFFFAFAFA))
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundGradient)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp)
        ) {
            Spacer(modifier = Modifier.height(32.dp))

            // 🔙 MODERN ACTION HEADER Bar
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                IconButton(
                    onClick = { navController.popBackStack() },
                    modifier = Modifier
                        .background(Color.White, CircleShape)
                        .size(40.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back",
                        tint = Color(0xFF2E7D32),
                        modifier = Modifier.size(22.dp)
                    )
                }

                Spacer(modifier = Modifier.width(16.dp))

                Text(
                    text = "Soil Testing AI",
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF1B5E20),
                        fontSize = 22.sp
                    )
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // 📸 HIGH-CLASS VISUAL CAMERA & UPLOAD CARD ZONE
            ElevatedCard(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    if (imageUri != null) {
                        // 🌟 Addition: Clean Image Display Window with quick clear utility
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(180.dp)
                                .clip(RoundedCornerShape(16.dp))
                        ) {
                            AsyncImage(
                                model = imageUri,
                                contentDescription = "Selected Soil Sample Photograph",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier.fillMaxSize()
                            )
                            Surface(
                                modifier = Modifier
                                    .padding(10.dp)
                                    .size(32.dp)
                                    .align(Alignment.TopEnd)
                                    .clickable { imageUri = null },
                                shape = CircleShape,
                                color = Color.Black.copy(alpha = 0.6f)
                            ) {
                                Box(contentAlignment = Alignment.Center) {
                                    Text("❌", color = Color.White, fontSize = 11.sp)
                                }
                            }
                        }
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            text = "Image Selected Successfully! ✅",
                            style = MaterialTheme.typography.bodyMedium.copy(
                                color = Color(0xFF2E7D32),
                                fontWeight = FontWeight.Bold
                            )
                        )
                    } else {
                        // Empty State Premium Upload Frame Layout
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(140.dp)
                                .background(Color(0xFFE8F5E9).copy(alpha = 0.5f), RoundedCornerShape(16.dp))
                                .border(1.dp, Color(0xFF81C784).copy(alpha = 0.4f), RoundedCornerShape(16.dp))
                                .clickable { launcher.launch("image/*") },
                            contentAlignment = Alignment.Center
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text("📸", fontSize = 36.sp)
                                Spacer(modifier = Modifier.height(6.dp))
                                Text(
                                    text = "Take / Upload Photo",
                                    style = MaterialTheme.typography.bodyMedium.copy(
                                        color = Color(0xFF2E7D32),
                                        fontWeight = FontWeight.Bold
                                    )
                                )
                                Text(
                                    text = "Tap here to select soil image",
                                    style = MaterialTheme.typography.labelSmall.copy(color = Color.Gray)
                                )
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // 📝 DESCRIPTION ROW HEADER
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("📝 ", fontSize = 16.sp)
                Text(
                    text = "Soil Description",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF3E2723)
                    )
                )
            }
            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                placeholder = { Text("Describe visual traits like dark color, loose texture, or moisture levels...", fontSize = 14.sp, color = Color.LightGray) },
                shape = RoundedCornerShape(16.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFF2E7D32),
                    unfocusedBorderColor = Color.Transparent,
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(110.dp)
            )

            Spacer(modifier = Modifier.height(20.dp))

            // 🤖 ROBUST AI ANALYTICAL BUTTON EXECUTION THREAD
            Button(
                onClick = {
                    if (imageUri == null && description.isBlank()) {
                        Toast.makeText(
                            context,
                            "Please upload image or enter description",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        val soilType =
                            if (description.contains("red", true)) "Red Soil"
                            else if (description.contains("black", true)) "Black Soil"
                            else "Loamy Soil"

                        val analysis = when (soilType) {
                            "Red Soil" -> "Well-drained but low in nutrients."
                            "Black Soil" -> "Retains moisture, good for cotton."
                            else -> "Balanced soil suitable for many crops."
                        }

                        val crops = when (soilType) {
                            "Red Soil" -> "Groundnut,Ragi,Millets,Pulses"
                            "Black Soil" -> "Cotton,Wheat,Soybean"
                            else -> "Rice,Vegetables"
                        }

                        val process = when (soilType) {
                            "Red Soil" -> "Add compost and irrigate frequently."
                            "Black Soil" -> "Avoid overwatering, ensure drainage."
                            else -> "Maintain balanced fertilization."
                        }

                        val encodedSoilType = Uri.encode(soilType)

                        val encodedAnalysis = Uri.encode(analysis)

                        val encodedCrops = Uri.encode(crops)

                        val encodedProcess = Uri.encode(process)

                        navController.navigate(
                            "soil_result/$encodedSoilType/$encodedAnalysis/$encodedCrops/$encodedProcess"
                        )
                    }
                },
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2E7D32)),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp)
            ) {
                Text(
                    text = "🔬 TEST MY SOIL WITH AI",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        letterSpacing = 0.5.sp
                    )
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // 🟤 UPDATE SOIL LAB METRICS OVERRIDE PANEL
            ElevatedCard(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text("🎚️ ", fontSize = 16.sp)
                        Text(
                            text = "Update Soil Data",
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF3E2723)
                            )
                        )
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    // Nitrogen Row HUD Accent
                    NutrientSliderRow(label = "Nitrogen (N)", value = nitrogen, maxColor = Color(0xFF1565C0)) { nitrogen = it }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Phosphorus Row HUD Accent
                    NutrientSliderRow(label = "Phosphorus (P)", value = phosphorus, maxColor = Color(0xFFC2185B)) { phosphorus = it }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Potassium Row HUD Accent
                    NutrientSliderRow(label = "Potassium (K)", value = potassium, maxColor = Color(0xFF7B1FA2)) { potassium = it }

                    Spacer(modifier = Modifier.height(24.dp))

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
                        shape = RoundedCornerShape(14.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF5D4037)),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp)
                    ) {
                        Text(
                            text = "Update Soil Levels",
                            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(40.dp))
        }
    }
}

@Composable
fun NutrientSliderRow(
    label: String,
    value: Float,
    maxColor: Color,
    onValueChange: (Float) -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = label,
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFF5D4037)
                )
            )
            Surface(
                color = maxColor.copy(alpha = 0.1f),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(
                    text = "${value.toInt()}%",
                    color = maxColor,
                    style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                )
            }
        }
        Slider(
            value = value,
            onValueChange = onValueChange,
            valueRange = 0f..100f,
            colors = SliderDefaults.colors(
                thumbColor = maxColor,
                activeTrackColor = maxColor,
                inactiveTrackColor = maxColor.copy(alpha = 0.12f)
            )
        )
    }
}