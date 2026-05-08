package com.syeda.raithabharosa.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun AddTaskDialog(
    onDismiss: () -> Unit
) {

    var activityTitle by remember {
        mutableStateOf("")
    }

    AlertDialog(
        onDismissRequest = {
            onDismiss()
        },

        confirmButton = {},

        containerColor = Color.White,

        shape = RoundedCornerShape(36.dp),

        text = {

            Column {

                // 🔹 TOP ROW
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Text(
                        text = "Add Activity",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold
                    )

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

                Spacer(modifier = Modifier.height(28.dp))

                // 🔹 LABEL
                Text(
                    text = "ACTIVITY TITLE",
                    color = Color.Gray,
                    fontWeight = FontWeight.Black,
                    letterSpacing = 2.sp
                )

                Spacer(modifier = Modifier.height(10.dp))

                // 🔹 INPUT
                OutlinedTextField(
                    value = activityTitle,
                    onValueChange = {
                        activityTitle = it
                    },
                    placeholder = {
                        Text("Add fertilizer")
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(85.dp),

                    shape = RoundedCornerShape(24.dp),

                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color(0xFF10B15A),
                        unfocusedBorderColor = Color(0xFFEAEAEA)
                    )
                )

                Spacer(modifier = Modifier.height(28.dp))

                // 🔹 DATE LABEL
                Text(
                    text = "SCHEDULED DATE",
                    color = Color.Gray,
                    fontWeight = FontWeight.Black,
                    letterSpacing = 2.sp
                )

                Spacer(modifier = Modifier.height(10.dp))

                // 🔹 DATE BOX
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(74.dp),

                    shape = RoundedCornerShape(22.dp),

                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xFFF7F7F7)
                    )
                ) {

                    Row(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 20.dp),

                        verticalAlignment = Alignment.CenterVertically,

                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {

                        Text(
                            text = "30/04/2026",
                            fontSize = 26.sp
                        )

                        Icon(
                            Icons.Default.KeyboardArrowDown,
                            contentDescription = null,
                            tint = Color.Gray
                        )
                    }
                }

                Spacer(modifier = Modifier.height(34.dp))

                // 🔹 BUTTON
                Button(
                    onClick = {

                    },

                    modifier = Modifier
                        .fillMaxWidth()
                        .height(66.dp),

                    shape = RoundedCornerShape(24.dp),

                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF10B15A)
                    )
                ) {

                    Text(
                        text = "Add Activity",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    )
}