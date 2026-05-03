package com.syeda.raithabharosa.ui.screens

import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

// 📌 Chat message model
data class ChatMessage(
    val text: String,
    val isUser: Boolean
)

@Composable
fun RaithaAssistScreen(navController: NavHostController) {

    val context = LocalContext.current
    val prefs = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)

    // ✅ FIX: Make name non-null
    val name = prefs.getString("name", "Farmer") ?: "Farmer"

    var message by remember { mutableStateOf("") }

    // ✅ FIX: Use mutableStateList (better for Compose)
    val messages = remember { mutableStateListOf<ChatMessage>() }

    // 🔥 Greeting only once
    LaunchedEffect(Unit) {
        messages.add(
            ChatMessage(
                "Namaste $name! I am Raitha Assist. How can I help you today?",
                false
            )
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .imePadding()
    ) {

        // 🔹 Title
        Text(
            text = "Raitha Assist",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(12.dp))

        // 🔹 Chat Area
        Card(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ) {
            LazyColumn(
                modifier = Modifier.padding(12.dp)
            ) {
                items(messages) { msg ->
                    Text(
                        text = msg.text,
                        modifier = Modifier.padding(6.dp)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        // 🔹 Input Area
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {

            OutlinedTextField(
                value = message,
                onValueChange = { message = it },
                placeholder = { Text("Ask anything about farming...") },
                modifier = Modifier.weight(1f)
            )

            IconButton(
                onClick = {
                    if (message.isNotBlank()) {

                        // ✅ Add user message
                        messages.add(ChatMessage(message, true))

                        // ✅ Fake AI response
                        val reply = getAIResponse(message)

                        // ✅ Add AI message
                        messages.add(ChatMessage(reply, false))

                        message = ""
                    }
                }
            ) {
                Icon(Icons.Default.Send, contentDescription = "Send")
            }
        }
    }
}

// 🤖 Simple AI logic
fun getAIResponse(query: String): String {
    return when {
        query.contains("water", true) -> "Water your crops early morning."
        query.contains("fertilizer", true) -> "Use fertilizer based on soil test."
        query.contains("weather", true) -> "Check dashboard for weather updates."
        else -> "I will help you with that soon."
    }
}