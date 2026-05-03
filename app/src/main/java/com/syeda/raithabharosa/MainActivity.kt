package com.syeda.raithabharosa

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.syeda.raithabharosa.navigation.AppNavigation
import com.syeda.raithabharosa.ui.theme.RaithaBharosaTheme
import com.syeda.raithabharosa.utils.LocaleHelper

class MainActivity : ComponentActivity() {

    override fun attachBaseContext(newBase: Context) {
        val prefs = newBase.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
        val lang = prefs.getString("lang", "en") ?: "en"

        val context = LocaleHelper.setLocale(newBase, lang)

        super.attachBaseContext(context)   // 🔥 THIS IS THE REAL FIX
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent {
            RaithaBharosaTheme {
                AppNavigation()
            }
        }
    }
}