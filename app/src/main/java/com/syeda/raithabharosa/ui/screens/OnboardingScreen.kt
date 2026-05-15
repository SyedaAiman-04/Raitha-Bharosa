package com.syeda.raithabharosa.ui.screens

import android.app.Activity
import android.content.Context
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.syeda.raithabharosa.ui.theme.EarthyText
import com.syeda.raithabharosa.ui.theme.GreenDark
import com.syeda.raithabharosa.ui.theme.GreenLight
import com.syeda.raithabharosa.ui.theme.SoftGray
import com.syeda.raithabharosa.utils.FirebaseAuthManager

@Composable
fun OnboardingScreen(navController: NavHostController) {
    val context = LocalContext.current
    var location by remember { mutableStateOf("") }
    var isSigningIn by remember { mutableStateOf(false) }

    val auth = FirebaseAuth.getInstance()
    val firestore = FirebaseFirestore.getInstance()

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            try {
                val account = task.result
                FirebaseAuthManager.firebaseAuthWithGoogle(
                    idToken = account.idToken!!,
                    onSuccess = {
                        val user = auth.currentUser
                        if (user != null) {
                            val prefs = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
                            prefs.edit()
                                .putString("name", user.displayName)
                                .putString("email", user.email)
                                .putString("photo", user.photoUrl.toString())
                                .putString("location", location.trim())
                                .apply()

                            val userMap = hashMapOf(
                                "name" to user.displayName,
                                "email" to user.email,
                                "photo" to user.photoUrl.toString(),
                                "location" to location.trim()
                            )

                            firestore.collection("users")
                                .document(user.uid)
                                .set(userMap)

                            isSigningIn = false
                            navController.navigate("language")
                        }
                    },
                    onFailure = { error ->
                        isSigningIn = false
                        Toast.makeText(context, error, Toast.LENGTH_LONG).show()
                    }
                )
            } catch (e: Exception) {
                isSigningIn = false
                Toast.makeText(context, e.message, Toast.LENGTH_LONG).show()
            }
        } else {
            isSigningIn = false
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(GreenLight.copy(alpha = 0.25f), MaterialTheme.colorScheme.background)
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

            // Brand Representation Area
            Surface(
                modifier = Modifier.size(90.dp),
                shape = RoundedCornerShape(24.dp),
                color = GreenDark.copy(alpha = 0.1f)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Text(
                        text = "🌱",
                        fontSize = 42.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Raitha Bharosa",
                style = MaterialTheme.typography.headlineLarge.copy(
                    fontWeight = FontWeight.Bold,
                    letterSpacing = (-0.5).sp,
                    color = EarthyText
                ),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = "AGRICULTURAL AI AGENT",
                style = MaterialTheme.typography.labelLarge.copy(
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.5.sp,
                    color = GreenDark
                ),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(40.dp))

            // Primary Interaction Card Layout
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(28.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(
                    modifier = Modifier.padding(24.dp)
                ) {
                    Text(
                        text = "Enter Your Location",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.SemiBold,
                            color = EarthyText
                        )
                    )

                    Text(
                        text = "Helps our AI tailor localized crop and soil analysis models.",
                        style = MaterialTheme.typography.bodySmall.copy(color = SoftGray),
                        modifier = Modifier.padding(top = 2.dp, bottom = 16.dp)
                    )

                    OutlinedTextField(
                        value = location,
                        onValueChange = { location = it },
                        placeholder = { Text("e.g. Hassan, Karnataka", color = SoftGray) },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.LocationOn,
                                contentDescription = null,
                                tint = GreenDark
                            )
                        },
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(
                            capitalization = KeyboardCapitalization.Words,
                            imeAction = ImeAction.Done
                        ),
                        shape = RoundedCornerShape(14.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = GreenDark,
                            unfocusedBorderColor = Color(0xFFE0E0E0),
                            focusedContainerColor = Color(0xFFF9F9F9),
                            unfocusedContainerColor = Color(0xFFF9F9F9)
                        ),
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    Button(
                        onClick = {
                            if (location.isNotBlank()) {
                                isSigningIn = true
                                val client = FirebaseAuthManager.getGoogleSignInClient(context)
                                FirebaseAuthManager.signIn(launcher, client)
                            } else {
                                Toast.makeText(context, "Please enter your location to proceed", Toast.LENGTH_SHORT).show()
                            }
                        },
                        enabled = !isSigningIn,
                        shape = RoundedCornerShape(16.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = GreenDark),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp)
                    ) {
                        if (isSigningIn) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(24.dp),
                                color = Color.White,
                                strokeWidth = 2.5.dp
                            )
                        } else {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center
                            ) {
                                // Simple structural visual asset for Google logo spacing
                                Text(
                                    text = "G ",
                                    style = MaterialTheme.typography.titleMedium.copy(
                                        fontWeight = FontWeight.Black,
                                        color = Color.White
                                    )
                                )
                                Text(
                                    text = "Continue with Google",
                                    style = MaterialTheme.typography.titleMedium.copy(
                                        fontWeight = FontWeight.SemiBold,
                                        fontSize = 16.sp,
                                        color = Color.White
                                    )
                                )
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(40.dp))
        }
    }
}