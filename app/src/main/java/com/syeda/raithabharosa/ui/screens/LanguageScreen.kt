package com.syeda.raithabharosa.ui.screens

import android.app.Activity
import android.content.Context
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.syeda.raithabharosa.R
import com.syeda.raithabharosa.ui.theme.EarthyText
import com.syeda.raithabharosa.ui.theme.GreenDark
import com.syeda.raithabharosa.ui.theme.GreenLight
import com.syeda.raithabharosa.ui.theme.SoftGray
import com.syeda.raithabharosa.utils.LocaleHelper

@Composable
fun LanguageScreen(navController: NavHostController) {

    var selectedLang by remember { mutableStateOf("en") }

    val context = LocalContext.current
    val activity = context as Activity

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
                .padding(24.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(modifier = Modifier.height(60.dp))

            // Decorative top element matching onboarding style
            Surface(
                modifier = Modifier.size(70.dp),
                shape = RoundedCornerShape(20.dp),
                color = GreenDark.copy(alpha = 0.08f)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Text(
                        text = "🌐",
                        fontSize = 32.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = stringResource(R.string.select_language),
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = EarthyText,
                    letterSpacing = (-0.5).sp
                ),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Choose your preferred language for farming instructions and AI chat tools.",
                style = MaterialTheme.typography.bodyMedium.copy(color = SoftGray),
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 16.dp)
            )

            Spacer(modifier = Modifier.height(36.dp))

            // Language Selection Container Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(28.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    LanguageItem("ಕನ್ನಡ", "kn", selectedLang) { selectedLang = it }
                    LanguageItem("English", "en", selectedLang) { selectedLang = it }
                    LanguageItem("हिंदी", "hi", selectedLang) { selectedLang = it }
                }
            }

            Spacer(modifier = Modifier.height(40.dp))

            Button(
                onClick = {
                    // 1. Save language
                    val prefs = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
                    prefs.edit().putString("lang", selectedLang).apply()

                    // 2. Apply language
                    LocaleHelper.setLocale(context, selectedLang)

                    // 3. Navigate to dashboard
                    navController.navigate("dashboard") {
                        popUpTo("language") { inclusive = true }
                    }

                    // 4. Restart app (to apply language)
                    activity.recreate()
                },
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = GreenDark),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
            ) {
                Text(
                    text = stringResource(R.string.continue_btn).uppercase(),
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 16.sp,
                        color = Color.White,
                        letterSpacing = 1.sp
                    )
                )
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
fun LanguageItem(
    title: String,
    code: String,
    selectedLang: String,
    onSelect: (String) -> Unit
) {
    val isSelected = selectedLang == code

    // Smoothly animate colors and borders based on focus selection state
    val backgroundColor by animateColorAsState(
        targetValue = if (isSelected) GreenDark.copy(alpha = 0.06f) else Color(0xFFF9F9F9),
        label = "bg_color"
    )
    val borderColor by animateColorAsState(
        targetValue = if (isSelected) GreenDark else Color.Transparent,
        label = "border_color"
    )
    val borderWidth by animateDpAsState(
        targetValue = if (isSelected) 1.5.dp else 0.dp,
        label = "border_width"
    )
    val textColor by animateColorAsState(
        targetValue = if (isSelected) GreenDark else EarthyText,
        label = "text_color"
    )

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .border(width = borderWidth, color = borderColor, shape = RoundedCornerShape(16.dp))
            .clickable { onSelect(code) },
        shape = RoundedCornerShape(16.dp),
        color = backgroundColor
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 18.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                    fontSize = 18.sp,
                    color = textColor
                )
            )

            RadioButton(
                selected = isSelected,
                onClick = { onSelect(code) },
                colors = RadioButtonDefaults.colors(
                    selectedColor = GreenDark,
                    unselectedColor = Color(0xFFBDBDBD)
                )
            )
        }
    }
}