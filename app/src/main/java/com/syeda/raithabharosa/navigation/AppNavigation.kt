package com.syeda.raithabharosa.navigation

import androidx.compose.foundation.background
import androidx.compose.runtime.Composable
import androidx.compose.material3.*
import androidx.compose.ui.Modifier
import androidx.navigation.compose.*
import androidx.compose.foundation.layout.padding
import com.syeda.raithabharosa.ui.screens.RaithaAssistScreen
import com.syeda.raithabharosa.ui.screens.*


@Composable
fun AppNavigation() {

    val navController = rememberNavController()

    val currentRoute =
        navController.currentBackStackEntryAsState().value?.destination?.route

    val showBottomBar = currentRoute in listOf(
        "dashboard",
        "calendar",
        "assist",
        "profile"
    )

    Scaffold(
        bottomBar = {
            if (showBottomBar) {
                NavigationBar(
                    modifier = Modifier
                        .background(MaterialTheme.colorScheme.secondary.copy(alpha = 0.2f))
                ){
                    NavigationBarItem(
                        selected = currentRoute == "dashboard",
                        onClick = { navController.navigate("dashboard") },
                        icon = { Text("🏠") },
                        label = { Text("Home") }
                    )

                    NavigationBarItem(
                        selected = currentRoute == "calendar",
                        onClick = { navController.navigate("calendar") },
                        icon = { Text("📅") },
                        label = { Text("Calendar") }
                    )

                    NavigationBarItem(
                        selected = currentRoute == "assist",
                        onClick = { navController.navigate("assist") },
                        icon = { Text("🤖") },
                        label = { Text("Assist") }
                    )

                    NavigationBarItem(
                        selected = currentRoute == "profile",
                        onClick = { navController.navigate("profile") },
                        icon = { Text("👤") },
                        label = { Text("Profile") }
                    )
                }
            }
        }
    ) { innerPadding ->

        NavHost(
            navController = navController,
            startDestination = "onboarding",
            modifier = Modifier.padding(innerPadding)
        ) {
            composable("assist") {
                RaithaAssistScreen(navController)
            }

            composable("onboarding") {
                OnboardingScreen(navController)
            }

            composable("language") {
                LanguageScreen(navController)
            }

            composable("dashboard") {
                DashboardScreen(navController)
            }

            composable("soil") {
                SoilTestingScreen(navController)
            }

            // ✅ RESULT SCREEN (ONLY ONCE)
            composable(
                "soil_result/{type}/{analysis}/{crops}/{process}"
            ) { backStackEntry ->

                val type = backStackEntry.arguments?.getString("type") ?: ""
                val analysis = backStackEntry.arguments?.getString("analysis") ?: ""
                val crops = backStackEntry.arguments?.getString("crops") ?: ""
                val process = backStackEntry.arguments?.getString("process") ?: ""

                SoilResultScreen(
                    navController,
                    type,
                    analysis,
                    crops,
                    process
                )
            }

            composable("calendar") {
                Text("Calendar Screen")
            }

            composable("profile") {
                Text("Profile Screen")
            }
        }
    }
}