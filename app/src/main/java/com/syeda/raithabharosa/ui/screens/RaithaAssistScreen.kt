package com.syeda.raithabharosa.ui.screens

import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.syeda.raithabharosa.utils.GeminiApi

// 📌 Chat message model
data class ChatMessage(
    val text: String,
    val isUser: Boolean
)

@Composable
fun RaithaAssistScreen(navController: NavHostController) {

    val context = LocalContext.current
    val prefs = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)

    val name = prefs.getString("name", "Farmer") ?: "Farmer"

    var message by remember { mutableStateOf("") }
    val messages = remember { mutableStateListOf<ChatMessage>() }

    val listState = rememberLazyListState()

    // 🔥 Initial greeting
    LaunchedEffect(Unit) {
        messages.add(
            ChatMessage(
                "Namaste $name! I am Raitha Assist. How can I help you today?",
                false
            )
        )
    }

    // 🔥 Auto scroll
    LaunchedEffect(messages.size) {
        if (messages.isNotEmpty()) {
            listState.animateScrollToItem(messages.size - 1)
        }
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
                state = listState,
                modifier = Modifier.padding(12.dp)
            ) {
                items(messages) { msg ->

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = if (msg.isUser)
                            Arrangement.End
                        else
                            Arrangement.Start
                    ) {
                        Card(
                            colors = CardDefaults.cardColors(
                                containerColor = if (msg.isUser)
                                    MaterialTheme.colorScheme.primary
                                else
                                    Color(0xFFDFF5E1) // 🌿 Light green AI bubble
                            ),
                            modifier = Modifier.padding(6.dp)
                        ) {
                            Text(
                                text = msg.text,
                                modifier = Modifier.padding(10.dp),
                                color = if (msg.isUser)
                                    MaterialTheme.colorScheme.onPrimary
                                else
                                    Color.Black
                            )
                        }
                    }
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
                modifier = Modifier.weight(1f),
                singleLine = true
            )

            IconButton(
                onClick = {
                    if (message.isNotBlank()) {

                        // 👨‍🌾 User message
                        messages.add(ChatMessage(message, true))

                        // 🤖 Loading
                        messages.add(ChatMessage("Thinking...", false))

                        // 🚀 Gemini API call
                        GeminiApi.getResponse(message) { response ->

                            // Remove "Thinking..."
                            if (messages.isNotEmpty()) {
                                messages.removeAt(messages.size - 1)
                            }

                            // Add AI response
                            messages.add(ChatMessage(response, false))
                        }

                        message = ""
                    }
                }
            ) {
                Icon(Icons.Default.Send, contentDescription = "Send")
            }
        }
    }
}