package com.syeda.raithabharosa.ui.screens

import android.app.Activity
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.google.firebase.auth.FirebaseAuth
import com.syeda.raithabharosa.R
import com.syeda.raithabharosa.utils.FirebaseAuthManager
import com.syeda.raithabharosa.utils.LanguageManager
import com.syeda.raithabharosa.utils.LocaleHelper

@Composable
fun ProfileScreen(navController: NavHostController) {

    val context = LocalContext.current

    val prefs =
        context.getSharedPreferences(
            "app_prefs",
            Context.MODE_PRIVATE
        )

    val name =
        prefs.getString("name", "Farmer")

    val email =
        prefs.getString("email", "No Email")

    var selectedLanguage by remember {

        mutableStateOf(
            LanguageManager.getLanguage(context)
        )
    }

    val backgroundBrush = Brush.verticalGradient(
        colors = listOf(
            Color(0xFFF4FAF2),
            Color(0xFFFFFFFF)
        )
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundBrush)
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 20.dp)
    ) {

        Spacer(modifier = Modifier.height(36.dp))

        // 🌾 TOP BAR
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Surface(
                shape = CircleShape,
                color = Color.White,
                shadowElevation = 8.dp,
                modifier = Modifier.size(54.dp)
            ) {

                IconButton(
                    onClick = {
                        navController.popBackStack()
                    }
                ) {

                    Icon(
                        Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = null,
                        tint = Color(0xFF1B5E20)
                    )
                }
            }

            Spacer(modifier = Modifier.width(20.dp))

            Column {

                Text(
                    text = "👤 Profile",

                    style =
                        MaterialTheme.typography.headlineMedium.copy(
                            fontWeight = FontWeight.ExtraBold,
                            color = Color(0xFF1B1B1B)
                        )
                )

                Text(
                    text = "Manage your farming identity 🌱",

                    style =
                        MaterialTheme.typography.bodySmall.copy(
                            color = Color.Gray
                        )
                )
            }
        }

        Spacer(modifier = Modifier.height(34.dp))

        // 🌱 PROFILE CARD
        Card(
            modifier = Modifier.fillMaxWidth(),

            shape = RoundedCornerShape(40.dp),

            colors = CardDefaults.cardColors(
                containerColor = Color.White
            ),

            elevation = CardDefaults.cardElevation(
                defaultElevation = 10.dp
            )
        ) {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = 28.dp,
                        vertical = 34.dp
                    ),

                horizontalAlignment =
                    Alignment.CenterHorizontally
            ) {

                Surface(
                    modifier = Modifier.size(130.dp),

                    shape = RoundedCornerShape(42.dp),

                    shadowElevation = 12.dp,

                    color = Color(0xFF2E7D32)
                ) {

                    Box(
                        contentAlignment = Alignment.Center
                    ) {

                        Icon(
                            Icons.Default.Person,
                            contentDescription = null,

                            tint = Color.White,

                            modifier = Modifier.size(60.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(28.dp))

                Text(
                    text = "🌿 ${name ?: "Farmer"}",

                    style =
                        MaterialTheme.typography.headlineLarge.copy(
                            fontWeight = FontWeight.ExtraBold,
                            color = Color(0xFF1B1B1B)
                        )
                )

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = email ?: "",

                    style =
                        MaterialTheme.typography.bodyLarge.copy(
                            color = Color.Gray,
                            letterSpacing = 1.5.sp
                        )
                )

                Spacer(modifier = Modifier.height(20.dp))

                Surface(
                    shape = RoundedCornerShape(20.dp),

                    color = Color(0xFFE8F5E9)
                ) {

                    Text(
                        text = "🚜 Verified Farmer",

                        modifier = Modifier.padding(
                            horizontal = 18.dp,
                            vertical = 10.dp
                        ),

                        style =
                            MaterialTheme.typography.labelLarge.copy(
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF2E7D32)
                            )
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // 📧 EMAIL CARD
        Card(
            modifier = Modifier.fillMaxWidth(),

            shape = RoundedCornerShape(34.dp),

            colors = CardDefaults.cardColors(
                containerColor = Color.White
            ),

            elevation = CardDefaults.cardElevation(
                defaultElevation = 6.dp
            )
        ) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),

                verticalAlignment =
                    Alignment.CenterVertically
            ) {

                Surface(
                    modifier = Modifier.size(66.dp),

                    shape = RoundedCornerShape(20.dp),

                    color = Color(0xFFE8F5E9)
                ) {

                    Box(
                        contentAlignment = Alignment.Center
                    ) {

                        Icon(
                            Icons.Default.Email,
                            contentDescription = null,

                            tint = Color(0xFF2E7D32)
                        )
                    }
                }

                Spacer(modifier = Modifier.width(18.dp))

                Column {

                    Text(
                        text = "📧 AUTHENTICATED ACCOUNT",

                        style =
                            MaterialTheme.typography.labelMedium.copy(
                                fontWeight = FontWeight.ExtraBold,
                                color = Color.Gray,
                                letterSpacing = 1.5.sp
                            )
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = email ?: "",

                        style =
                            MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF1B1B1B)
                            )
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // 🌐 LANGUAGE CARD
        Card(
            modifier = Modifier.fillMaxWidth(),

            shape = RoundedCornerShape(36.dp),

            colors = CardDefaults.cardColors(
                containerColor = Color.White
            ),

            elevation = CardDefaults.cardElevation(
                defaultElevation = 6.dp
            )
        ) {

            Column(
                modifier = Modifier.padding(24.dp)
            ) {

                Row(
                    verticalAlignment =
                        Alignment.CenterVertically
                ) {

                    Surface(
                        modifier = Modifier.size(68.dp),

                        shape = RoundedCornerShape(22.dp),

                        color = Color(0xFF2E7D32)
                    ) {

                        Box(
                            contentAlignment = Alignment.Center
                        ) {

                            Icon(
                                Icons.Default.Language,
                                contentDescription = null,

                                tint = Color.White
                            )
                        }
                    }

                    Spacer(modifier = Modifier.width(18.dp))

                    Column {

                        Text(
                            text = "🌐 CHANGE LANGUAGE",

                            style =
                                MaterialTheme.typography.labelMedium.copy(
                                    fontWeight = FontWeight.ExtraBold,
                                    color = Color.Gray,
                                    letterSpacing = 1.5.sp
                                )
                        )

                        Spacer(modifier = Modifier.height(6.dp))

                        Text(
                            text =
                                when (selectedLanguage) {

                                    "kn" -> "ಕನ್ನಡ"

                                    "hi" -> "हिन्दी"

                                    else -> "ENGLISH"
                                },

                            style =
                                MaterialTheme.typography.headlineSmall.copy(
                                    fontWeight = FontWeight.ExtraBold,
                                    color = Color(0xFF2E7D32)
                                )
                        )
                    }
                }

                Spacer(modifier = Modifier.height(28.dp))

                Row(
                    horizontalArrangement =
                        Arrangement.spacedBy(12.dp)
                ) {

                    LanguageButton(
                        text = "ENGLISH",
                        selected = selectedLanguage == "en"
                    ) {

                        changeLanguage(context, "en")
                        selectedLanguage = "en"
                    }

                    LanguageButton(
                        text = "ಕನ್ನಡ",
                        selected = selectedLanguage == "kn"
                    ) {

                        changeLanguage(context, "kn")
                        selectedLanguage = "kn"
                    }

                    LanguageButton(
                        text = "हिन्दी",
                        selected = selectedLanguage == "hi"
                    ) {

                        changeLanguage(context, "hi")
                        selectedLanguage = "hi"
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(30.dp))

        // 🚪 LOGOUT BUTTON
        Button(
            onClick = {

                FirebaseAuth.getInstance().signOut()

                val googleClient =
                    FirebaseAuthManager
                        .getGoogleSignInClient(context)

                googleClient.signOut()

                prefs.edit().clear().apply()

                Toast.makeText(
                    context,
                    "Session Terminated",
                    Toast.LENGTH_SHORT
                ).show()

                navController.navigate("onboarding") {

                    popUpTo(0)
                }
            },

            modifier = Modifier
                .fillMaxWidth()
                .height(72.dp),

            shape = RoundedCornerShape(28.dp),

            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFFFF1F1)
            )
        ) {

            Icon(
                Icons.Default.ExitToApp,
                contentDescription = null,

                tint = Color.Red
            )

            Spacer(modifier = Modifier.width(12.dp))

            Text(
                text = "🚪 LOGOUT",

                style =
                    MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.ExtraBold,
                        color = Color.Red,
                        letterSpacing = 1.5.sp
                    )
            )
        }

        Spacer(modifier = Modifier.height(30.dp))

        Text(
            text = "🌾 RAITHA APP V1.0.0",

            modifier = Modifier.fillMaxWidth(),

            style =
                MaterialTheme.typography.bodySmall.copy(
                    color = Color.LightGray,
                    letterSpacing = 2.sp
                )
        )

        Spacer(modifier = Modifier.height(120.dp))
    }
}

fun changeLanguage(
    context: Context,
    language: String
) {

    LanguageManager.saveLanguage(
        context,
        language
    )

    LocaleHelper.setLocale(
        context,
        language
    )

    (context as? Activity)?.recreate()
}

@Composable
fun LanguageButton(
    text: String,

    selected: Boolean,

    onClick: () -> Unit
) {

    Button(
        onClick = onClick,

        shape = RoundedCornerShape(18.dp),

        colors = ButtonDefaults.buttonColors(

            containerColor =

                if (selected)
                    Color(0xFF2E7D32)
                else
                    Color(0xFFF3F5EF)
        )
    ) {

        Text(
            text = text,

            color =

                if (selected)
                    Color.White
                else
                    Color.DarkGray,

            fontWeight = FontWeight.Bold
        )
    }
}