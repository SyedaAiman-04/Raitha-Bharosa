package com.syeda.raithabharosa.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun AddTaskDialog(
    selectedDate: String,
    onDismiss: () -> Unit,
    onAddTask: (String, String) -> Unit
) {

    var activityTitle by remember {
        mutableStateOf("")
    }

    var expanded by remember {
        mutableStateOf(false)
    }

    var selectedStatus by remember {
        mutableStateOf("PENDING")
    }

    val statusOptions = listOf(
        "PENDING",
        "SCHEDULED",
        "COMPLETED"
    )

    AlertDialog(
        onDismissRequest = {
            onDismiss()
        },

        confirmButton = {},

        containerColor = Color.Transparent,

        text = {

            Card(
                shape = RoundedCornerShape(36.dp),

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
                        .padding(26.dp)
                ) {

                    // 🌾 HEADER
                    Row(
                        modifier = Modifier.fillMaxWidth(),

                        horizontalArrangement =
                            Arrangement.SpaceBetween,

                        verticalAlignment =
                            Alignment.CenterVertically
                    ) {

                        Column {

                            Text(
                                text = "🌱 Add Activity",

                                style = MaterialTheme.typography.headlineMedium.copy(
                                    fontWeight = FontWeight.ExtraBold,
                                    color = Color(0xFF1B5E20)
                                )
                            )

                            Spacer(modifier = Modifier.height(4.dp))

                            Text(
                                text = "Plan your farming tasks smarter 🚜",

                                style = MaterialTheme.typography.bodySmall.copy(
                                    color = Color.Gray
                                )
                            )
                        }

                        Surface(
                            shape = CircleShape,
                            color = Color(0xFFF4F4F4)
                        ) {

                            IconButton(
                                onClick = {
                                    onDismiss()
                                }
                            ) {

                                Icon(
                                    Icons.Default.Close,
                                    contentDescription = null,
                                    tint = Color.Gray
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(30.dp))

                    // 📝 TITLE LABEL
                    Text(
                        text = "📝 ACTIVITY TITLE",

                        color = Color(0xFF757575),

                        fontWeight = FontWeight.ExtraBold,

                        letterSpacing = 1.sp
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    // 🌿 INPUT FIELD
                    OutlinedTextField(
                        value = activityTitle,

                        onValueChange = {
                            activityTitle = it
                        },

                        placeholder = {
                            Text("Ex: Add fertilizer 🌱")
                        },

                        modifier = Modifier
                            .fillMaxWidth()
                            .height(85.dp),

                        shape = RoundedCornerShape(24.dp),

                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor =
                                Color(0xFF43A047),

                            unfocusedBorderColor =
                                Color(0xFFE0E0E0),

                            focusedContainerColor =
                                Color(0xFFF9FFF8),

                            unfocusedContainerColor =
                                Color(0xFFF9FFF8)
                        )
                    )

                    Spacer(modifier = Modifier.height(26.dp))

                    // 📅 DATE LABEL
                    Text(
                        text = "📅 SCHEDULED DATE",

                        color = Color(0xFF757575),

                        fontWeight = FontWeight.ExtraBold,

                        letterSpacing = 1.sp
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    // 📆 DATE BOX
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(76.dp),

                        shape = RoundedCornerShape(24.dp),

                        colors = CardDefaults.cardColors(
                            containerColor = Color(0xFFF7FAF6)
                        )
                    ) {

                        Row(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(horizontal = 22.dp),

                            verticalAlignment =
                                Alignment.CenterVertically,

                            horizontalArrangement =
                                Arrangement.SpaceBetween
                        ) {

                            Column {

                                Text(
                                    text = "Selected Date",

                                    style = MaterialTheme.typography.bodySmall.copy(
                                        color = Color.Gray
                                    )
                                )

                                Spacer(
                                    modifier = Modifier.height(4.dp)
                                )

                                Text(
                                    text = selectedDate,

                                    fontSize = 20.sp,

                                    fontWeight = FontWeight.Bold,

                                    color = Color(0xFF1B5E20)
                                )
                            }

                            Text(
                                text = "📆",
                                fontSize = 28.sp
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(26.dp))

                    // 🚦 STATUS LABEL
                    Text(
                        text = "🚦 TASK STATUS",

                        color = Color(0xFF757575),

                        fontWeight = FontWeight.ExtraBold,

                        letterSpacing = 1.sp
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Box {

                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(76.dp),

                            shape = RoundedCornerShape(24.dp),

                            colors = CardDefaults.cardColors(
                                containerColor = Color(0xFFF7FAF6)
                            ),

                            onClick = {
                                expanded = true
                            }
                        ) {

                            Row(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(horizontal = 22.dp),

                                verticalAlignment =
                                    Alignment.CenterVertically,

                                horizontalArrangement =
                                    Arrangement.SpaceBetween
                            ) {

                                Row(
                                    verticalAlignment =
                                        Alignment.CenterVertically
                                ) {

                                    Text(
                                        text =

                                            when(selectedStatus) {

                                                "PENDING" -> "🕒 Pending"
                                                "SCHEDULED" -> "📅 Scheduled"
                                                else -> "✅ Completed"
                                            },

                                        fontSize = 18.sp,

                                        fontWeight = FontWeight.Bold,

                                        color = Color(0xFF1B5E20)
                                    )
                                }

                                Icon(
                                    Icons.Default.KeyboardArrowDown,
                                    contentDescription = null,
                                    tint = Color.Gray
                                )
                            }
                        }

                        DropdownMenu(
                            expanded = expanded,

                            onDismissRequest = {
                                expanded = false
                            }
                        ) {

                            statusOptions.forEach { status ->

                                DropdownMenuItem(

                                    text = {

                                        Text(

                                            when(status) {

                                                "PENDING" ->
                                                    "🕒 Pending"

                                                "SCHEDULED" ->
                                                    "📅 Scheduled"

                                                else ->
                                                    "✅ Completed"
                                            }
                                        )
                                    },

                                    onClick = {

                                        selectedStatus = status

                                        expanded = false
                                    }
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(34.dp))

                    // 🚀 BUTTON
                    Button(
                        onClick = {

                            if (activityTitle.isNotBlank()) {

                                onAddTask(
                                    activityTitle,
                                    selectedStatus
                                )

                                onDismiss()
                            }
                        },

                        modifier = Modifier
                            .fillMaxWidth()
                            .height(66.dp),

                        shape = RoundedCornerShape(24.dp),

                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Transparent
                        ),

                        contentPadding = PaddingValues()
                    ) {

                        Box(
                            modifier = Modifier
                                .fillMaxSize()

                                .background(
                                    brush = Brush.horizontalGradient(
                                        colors = listOf(
                                            Color(0xFF2E7D32),
                                            Color(0xFF43A047)
                                        )
                                    ),

                                    shape = RoundedCornerShape(24.dp)
                                ),

                            contentAlignment = Alignment.Center
                        ) {

                            Text(
                                text = "🚀 Add Activity",

                                fontSize = 18.sp,

                                fontWeight = FontWeight.ExtraBold,

                                color = Color.White
                            )
                        }
                    }
                }
            }
        }
    )
}