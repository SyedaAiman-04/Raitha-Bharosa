package com.syeda.raithabharosa.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.syeda.raithabharosa.ui.models.CalendarTask

@Composable
fun TaskSection(
    tasks: List<CalendarTask>
) {

    Spacer(modifier = Modifier.height(20.dp))

    Card(
        modifier = Modifier.fillMaxWidth(),

        shape = RoundedCornerShape(30.dp),

        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),

        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(22.dp)
        ) {

            // 🌾 HEADER
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {

                Text(
                    text = "📝",
                    fontSize = 28.sp
                )

                Spacer(modifier = Modifier.width(10.dp))

                Column {

                    Text(
                        text = "Today's Activities",

                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.ExtraBold,
                            color = Color(0xFF1B5E20)
                        )
                    )

                    Text(
                        text = "Manage your farming schedule 🌱",

                        style = MaterialTheme.typography.bodySmall.copy(
                            color = Color.Gray
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.height(22.dp))

            // 🚫 EMPTY STATE
            if (tasks.isEmpty()) {

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 20.dp),

                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Text(
                        text = "📭",
                        fontSize = 42.sp
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = "No Activities Scheduled",

                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF757575)
                        )
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = "Add farming tasks using the + button 🚜",

                        style = MaterialTheme.typography.bodySmall.copy(
                            color = Color.Gray
                        ),

                        fontStyle = FontStyle.Italic
                    )
                }

            } else {

                // 🌿 TASKS
                tasks.forEachIndexed { index, task ->

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),

                        shape = RoundedCornerShape(22.dp),

                        elevation = CardDefaults.cardElevation(
                            defaultElevation = 2.dp
                        ),

                        colors = CardDefaults.cardColors(

                            containerColor =

                                if (index % 2 == 0)
                                    Color(0xFFF4FAF2)
                                else
                                    Color(0xFFF8F5FF)
                        )
                    ) {

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(
                                    horizontal = 18.dp,
                                    vertical = 18.dp
                                ),

                            verticalAlignment =
                                Alignment.CenterVertically
                        ) {

                            // 🌱 ICON
                            Box(
                                modifier = Modifier
                                    .size(50.dp)

                                    .background(
                                        brush = Brush.linearGradient(
                                            colors = listOf(
                                                Color(0xFF43A047),
                                                Color(0xFF81C784)
                                            )
                                        ),

                                        shape = CircleShape
                                    ),

                                contentAlignment = Alignment.Center
                            ) {

                                Text(
                                    text =

                                        when (index % 4) {

                                            0 -> "🌾"
                                            1 -> "💧"
                                            2 -> "🚜"
                                            else -> "🌱"
                                        },

                                    fontSize = 22.sp
                                )
                            }

                            Spacer(modifier = Modifier.width(16.dp))

                            Column(
                                modifier = Modifier.weight(1f)
                            ) {

                                Text(
                                    text = task.title,

                                    style = MaterialTheme.typography.titleMedium.copy(
                                        fontWeight = FontWeight.Bold,
                                        color = Color(0xFF1B5E20)
                                    )
                                )

                                Spacer(
                                    modifier = Modifier.height(4.dp)
                                )

                                Text(
                                    text = "Scheduled farming activity 🌿",

                                    style = MaterialTheme.typography.bodySmall.copy(
                                        color = Color.Gray
                                    )
                                )
                            }

                            // ✅ STATUS DOT
                            Box(
                                modifier = Modifier
                                    .size(12.dp)

                                    .background(
                                        Color(0xFF4CAF50),
                                        CircleShape
                                    )
                            )
                        }
                    }
                }
            }
        }
    }
}