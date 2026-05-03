package com.syeda.raithabharosa.ui.screens

import android.app.Activity
import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.syeda.raithabharosa.R
import com.syeda.raithabharosa.utils.LocaleHelper

@Composable
fun LanguageScreen(navController: NavHostController) {

    var selectedLang by remember { mutableStateOf("en") }

    val context = LocalContext.current
    val activity = context as Activity

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.height(30.dp))

        Text(
            text = stringResource(R.string.select_language),
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(30.dp))

        LanguageItem("ಕನ್ನಡ", "kn", selectedLang) { selectedLang = it }
        LanguageItem("English", "en", selectedLang) { selectedLang = it }
        LanguageItem("हिंदी", "hi", selectedLang) { selectedLang = it }

        Spacer(modifier = Modifier.height(30.dp))

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
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(stringResource(R.string.continue_btn))
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
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = title)

            RadioButton(
                selected = selectedLang == code,
                onClick = { onSelect(code) }
            )
        }
    }
}