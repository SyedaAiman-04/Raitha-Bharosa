package com.syeda.raithabharosa.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.syeda.raithabharosa.ui.models.CalendarTask
import com.syeda.raithabharosa.utils.FirebaseTaskManager

@Composable
fun TaskListScreen(navController: NavHostController) {

    val darkGreen = Color(0xFF1B5E20)

    val allTasks = remember {
        mutableStateListOf<CalendarTask>()
    }

    // 🔥 FIREBASE LOAD
    LaunchedEffect(Unit) {

        FirebaseTaskManager.getTasks { firebaseTasks ->

            allTasks.clear()

            allTasks.addAll(firebaseTasks)
        }
    }

    val pendingTasks =
        allTasks.filter {
            it.status == "PENDING"
        }

    val scheduledTasks =
        allTasks.filter {
            it.status == "SCHEDULED"
        }

    val completedTasks =
        allTasks.filter {
            it.status == "COMPLETED"
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
            .padding(horizontal = 20.dp)
            .verticalScroll(rememberScrollState())
    ) {

        Spacer(modifier = Modifier.height(36.dp))

        // 🌾 TOP BAR
        Row(
            modifier = Modifier.fillMaxWidth(),

            horizontalArrangement =
                Arrangement.SpaceBetween,

            verticalAlignment =
                Alignment.CenterVertically
        ) {

            Surface(
                shape = CircleShape,
                color = Color.White,
                shadowElevation = 6.dp,
                modifier = Modifier.size(52.dp)
            ) {

                IconButton(
                    onClick = {
                        navController.popBackStack()
                    }
                ) {

                    Icon(
                        Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = null,
                        tint = darkGreen
                    )
                }
            }

            Column(
                horizontalAlignment =
                    Alignment.CenterHorizontally
            ) {

                Text(
                    text = "📋 Task List",

                    style =
                        MaterialTheme.typography.headlineMedium.copy(
                            fontWeight = FontWeight.ExtraBold,
                            color = darkGreen
                        )
                )

                Text(
                    text = "Manage all farming activities 🌱",

                    style =
                        MaterialTheme.typography.bodySmall.copy(
                            color = Color.Gray
                        )
                )
            }

            FloatingActionButton(
                onClick = { },

                containerColor = Color(0xFF10B15A),

                shape = CircleShape
            ) {

                Icon(
                    Icons.Default.Add,
                    contentDescription = null,
                    tint = Color.White
                )
            }
        }

        Spacer(modifier = Modifier.height(28.dp))

        // 🌿 TAB BAR
        Card(
            modifier = Modifier.fillMaxWidth(),

            shape = RoundedCornerShape(34.dp),

            colors = CardDefaults.cardColors(
                containerColor = Color.White
            ),

            elevation = CardDefaults.cardElevation(
                defaultElevation = 4.dp
            )
        ) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = 10.dp,
                        vertical = 10.dp
                    ),

                horizontalArrangement =
                    Arrangement.SpaceBetween
            ) {

                TimelineTabButton(
                    selected = false,
                    darkGreen = darkGreen,

                    onClick = {
                        navController.navigate("calendar")
                    }
                ) {

                    Icon(
                        Icons.Default.CalendarMonth,
                        contentDescription = null
                    )
                }

                TimelineTabButton(
                    selected = false,
                    darkGreen = darkGreen,

                    onClick = {
                        navController.navigate("timeline")
                    }
                ) {

                    Icon(
                        Icons.Default.Schedule,
                        contentDescription = null
                    )
                }

                TimelineTabButton(
                    selected = true,
                    darkGreen = darkGreen,

                    onClick = {

                    }
                ) {

                    Icon(
                        Icons.Default.CheckCircle,
                        contentDescription = null
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(30.dp))

        // 🚫 EMPTY STATE
        if (allTasks.isEmpty()) {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 90.dp),

                horizontalAlignment =
                    Alignment.CenterHorizontally
            ) {

                Text(
                    text = "📭",
                    fontSize = 70.sp
                )

                Spacer(modifier = Modifier.height(18.dp))

                Text(
                    text = "No Tasks Available",

                    style =
                        MaterialTheme.typography.headlineSmall.copy(
                            fontWeight = FontWeight.Bold,
                            color = darkGreen
                        )
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Your farming tasks will appear here 🚜",

                    style =
                        MaterialTheme.typography.bodyMedium.copy(
                            color = Color.Gray
                        )
                )
            }

        } else {

            // 🕒 PENDING
            TaskCategorySection(
                title = "🕒 Pending",
                color = Color(0xFFFFF3E0),
                textColor = Color(0xFFE65100),
                tasks = pendingTasks
            )

            Spacer(modifier = Modifier.height(24.dp))

            // 📅 SCHEDULED
            TaskCategorySection(
                title = "📅 Scheduled",
                color = Color(0xFFE3F2FD),
                textColor = Color(0xFF1565C0),
                tasks = scheduledTasks
            )

            Spacer(modifier = Modifier.height(24.dp))

            // ✅ COMPLETED
            TaskCategorySection(
                title = "✅ Completed",
                color = Color(0xFFE8F5E9),
                textColor = Color(0xFF2E7D32),
                tasks = completedTasks
            )
        }

        Spacer(modifier = Modifier.height(120.dp))
    }
}

@Composable
fun TaskCategorySection(
    title: String,
    color: Color,
    textColor: Color,
    tasks: List<CalendarTask>
) {

    if (tasks.isNotEmpty()) {

        Text(
            text = "$title (${tasks.size})",

            style =
                MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.ExtraBold,
                    color = textColor
                )
        )

        Spacer(modifier = Modifier.height(16.dp))

        tasks.forEachIndexed { index, task ->

            TaskCard(
                task = task,
                cardColor = color
            )

            if (index != tasks.lastIndex) {

                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@Composable
fun TaskCard(
    task: CalendarTask,
    cardColor: Color
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(
                elevation = 8.dp,
                shape = RoundedCornerShape(30.dp)
            ),

        shape = RoundedCornerShape(30.dp),

        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),

            verticalAlignment =
                Alignment.CenterVertically
        ) {

            // 🌱 ICON BOX
            Box(
                modifier = Modifier
                    .size(68.dp)

                    .background(
                        color = cardColor,
                        shape = RoundedCornerShape(22.dp)
                    ),

                contentAlignment =
                    Alignment.Center
            ) {

                Text(
                    text =

                        when(task.status) {

                            "COMPLETED" -> "✅"
                            "SCHEDULED" -> "📅"
                            else -> "🌾"
                        },

                    fontSize = 28.sp
                )
            }

            Spacer(modifier = Modifier.width(18.dp))

            // 🌿 TASK DETAILS
            Column(
                modifier = Modifier.weight(1f)
            ) {

                Text(
                    text = task.title,

                    style =
                        MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.ExtraBold,
                            color = Color(0xFF1B1B1B)
                        )
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "🚜 Farming Activity",

                    style =
                        MaterialTheme.typography.bodyMedium.copy(
                            color = Color.Gray
                        )
                )
            }

            // 📅 DATE
            Surface(
                shape = RoundedCornerShape(18.dp),

                color = cardColor
            ) {

                Text(
                    text = task.date,

                    modifier = Modifier.padding(
                        horizontal = 14.dp,
                        vertical = 10.dp
                    ),

                    style =
                        MaterialTheme.typography.labelLarge.copy(
                            fontWeight = FontWeight.Bold,
                            color = Color.DarkGray
                        )
                )
            }
        }
    }
}