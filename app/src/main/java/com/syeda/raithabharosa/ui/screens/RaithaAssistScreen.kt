package com.syeda.raithabharosa.ui.screens

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.syeda.raithabharosa.utils.GeminiApi
import kotlinx.coroutines.launch

data class ChatMessage(
    val text: String,
    val isUser: Boolean
)

@Composable
fun RaithaAssistScreen(
    navController: NavHostController
) {

    val context = LocalContext.current

    val prefs = context.getSharedPreferences(
        "app_prefs",
        Context.MODE_PRIVATE
    )

    val farmerName =
        prefs.getString("name", "Farmer")
            ?: "Farmer"

    var message by remember {
        mutableStateOf("")
    }

    val messages = remember {
        mutableStateListOf<ChatMessage>()
    }

    val listState = rememberLazyListState()

    val coroutineScope = rememberCoroutineScope()

    // 🌱 FIRST GREETING
    LaunchedEffect(Unit) {

        if (messages.isEmpty()) {

            messages.add(
                ChatMessage(
                    "Namaste $farmerName 👨‍🌾\nI am Raitha Assist.\nHow can I help your farming today?",
                    false
                )
            )
        }
    }

    // 🔥 AUTO SCROLL
    LaunchedEffect(messages.size) {

        if (messages.isNotEmpty()) {

            listState.animateScrollToItem(
                messages.size - 1
            )
        }
    }

    val backgroundBrush = Brush.verticalGradient(
        colors = listOf(
            Color(0xFFF4FAF2),
            Color(0xFFFFFFFF)
        )
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundBrush)
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 18.dp)
                .imePadding()
        ) {

            Spacer(modifier = Modifier.height(40.dp))

            // 🌟 PREMIUM HEADER
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Surface(
                    shape = CircleShape,
                    color = Color.White,
                    shadowElevation = 6.dp,
                    modifier = Modifier.size(48.dp)
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

                Spacer(modifier = Modifier.width(16.dp))

                Column {

                    Text(
                        text = "Raitha Assist",
                        style = MaterialTheme.typography.headlineSmall.copy(
                            fontWeight = FontWeight.ExtraBold,
                            color = Color(0xFF1B5E20)
                        )
                    )

                    Text(
                        text = "AI Farming Companion",
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = Color.Gray
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.height(22.dp))

            // 🌿 CHAT CONTAINER
            Card(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),

                shape = RoundedCornerShape(30.dp),

                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFFF7F3FB)
                )
            ) {

                LazyColumn(
                    state = listState,
                    modifier = Modifier.padding(16.dp)
                ) {

                    items(messages) { msg ->

                        Row(
                            modifier = Modifier.fillMaxWidth(),

                            horizontalArrangement =

                                if (msg.isUser)
                                    Arrangement.End
                                else
                                    Arrangement.Start
                        ) {

                            Card(
                                shape = RoundedCornerShape(
                                    topStart = 22.dp,
                                    topEnd = 22.dp,
                                    bottomStart =
                                        if (msg.isUser) 22.dp else 4.dp,
                                    bottomEnd =
                                        if (msg.isUser) 4.dp else 22.dp
                                ),

                                colors = CardDefaults.cardColors(

                                    containerColor =

                                        if (msg.isUser)
                                            Color(0xFF2E7D32)
                                        else
                                            Color(0xFFE4F4E4)
                                ),

                                modifier = Modifier
                                    .padding(vertical = 6.dp)
                                    .widthIn(max = 300.dp)
                                    .shadow(
                                        elevation = 2.dp,
                                        shape = RoundedCornerShape(22.dp)
                                    )
                            ) {

                                Text(
                                    text = msg.text,

                                    modifier = Modifier.padding(
                                        horizontal = 16.dp,
                                        vertical = 14.dp
                                    ),

                                    style = MaterialTheme.typography.bodyLarge.copy(
                                        lineHeight = 24.sp
                                    ),

                                    color =

                                        if (msg.isUser)
                                            Color.White
                                        else
                                            Color(0xFF222222)
                                )
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // 🚀 MODERN INPUT BAR
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {

                OutlinedTextField(
                    value = message,

                    onValueChange = {
                        message = it
                    },

                    placeholder = {
                        Text(
                            "Ask anything about farming..."
                        )
                    },

                    modifier = Modifier.weight(1f),

                    singleLine = true,

                    shape = RoundedCornerShape(20.dp),

                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color(0xFF2E7D32),
                        unfocusedBorderColor = Color.LightGray,
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White
                    )
                )

                Spacer(modifier = Modifier.width(10.dp))

                // 🚀 SEND BUTTON
                Surface(
                    shape = CircleShape,
                    color = Color(0xFF2E7D32),
                    shadowElevation = 6.dp,
                    modifier = Modifier.size(56.dp)
                ) {

                    IconButton(

                        onClick = {

                            if (message.isNotBlank()) {

                                val userMessage = message

                                messages.add(
                                    ChatMessage(
                                        userMessage,
                                        true
                                    )
                                )

                                messages.add(
                                    ChatMessage(
                                        "🌱 Raitha Assist is thinking...",
                                        false
                                    )
                                )

                                message = ""

                                coroutineScope.launch {

                                    val response =
                                        GeminiApi.getResponse(
                                            userMessage
                                        )

                                    if (messages.isNotEmpty()) {

                                        messages.removeAt(
                                            messages.size - 1
                                        )
                                    }

                                    messages.add(
                                        ChatMessage(
                                            response,
                                            false
                                        )
                                    )
                                }
                            }
                        }
                    ) {

                        Icon(
                            Icons.Default.Send,
                            contentDescription = null,
                            tint = Color.White
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}