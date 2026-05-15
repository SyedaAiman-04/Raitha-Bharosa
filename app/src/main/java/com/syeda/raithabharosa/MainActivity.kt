package com.syeda.raithabharosa

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.syeda.raithabharosa.navigation.AppNavigation
import com.syeda.raithabharosa.ui.theme.RaithaBharosaTheme
import com.syeda.raithabharosa.utils.LanguageManager
import com.syeda.raithabharosa.utils.LocaleHelper

class MainActivity : ComponentActivity() {

    override fun attachBaseContext(newBase: Context) {

        val language =
            LanguageManager.getLanguage(newBase)

        val context =
            LocaleHelper.setLocale(
                newBase,
                language
            )

        super.attachBaseContext(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContent {

            RaithaBharosaTheme {

                AppNavigation()
            }
        }
    }
}