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
fun TimelineScreen(navController: NavHostController) {

    val darkGreen = Color(0xFF1B5E20)

    val allTasks = remember {
        mutableStateListOf<CalendarTask>()
    }

    // 🔥 FIREBASE LOAD
    LaunchedEffect(Unit) {

        FirebaseTaskManager.getTasks { firebaseTasks ->

            allTasks.clear()

            allTasks.addAll(
                firebaseTasks.sortedBy {
                    it.date
                }
            )
        }
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
                    text = "🌿 Timeline",

                    style =
                        MaterialTheme.typography.headlineMedium.copy(
                            fontWeight = FontWeight.ExtraBold,
                            color = darkGreen
                        )
                )

                Text(
                    text = "Track all farming activities 🚜",

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

        // 🌱 TAB BAR
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
                    selected = true,
                    darkGreen = darkGreen,

                    onClick = {

                    }
                ) {

                    Icon(
                        Icons.Default.Schedule,
                        contentDescription = null
                    )
                }

                TimelineTabButton(
                    selected = false,
                    darkGreen = darkGreen,

                    onClick = {
                        navController.navigate("tasklist")
                    }
                ) {

                    Icon(
                        Icons.Default.CheckCircle,
                        contentDescription = null
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(34.dp))

        // 🚫 EMPTY STATE
        if (allTasks.isEmpty()) {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 80.dp),

                horizontalAlignment =
                    Alignment.CenterHorizontally
            ) {

                Text(
                    text = "📭",
                    fontSize = 70.sp
                )

                Spacer(modifier = Modifier.height(18.dp))

                Text(
                    text = "No Activities Yet",

                    style =
                        MaterialTheme.typography.headlineSmall.copy(
                            fontWeight = FontWeight.Bold,
                            color = darkGreen
                        )
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Your farming timeline will appear here 🌱",

                    style =
                        MaterialTheme.typography.bodyMedium.copy(
                            color = Color.Gray
                        )
                )
            }

        } else {

            allTasks.forEachIndexed { index, task ->

                TimelineItem(
                    date = task.date,

                    title = task.title,

                    subtitle = task.status,

                    active =
                        task.status == "COMPLETED"
                )

                if (index != allTasks.lastIndex) {

                    Spacer(modifier = Modifier.height(30.dp))
                }
            }
        }

        Spacer(modifier = Modifier.height(120.dp))
    }
}

@Composable
fun TimelineTabButton(
    selected: Boolean,
    darkGreen: Color,
    onClick: () -> Unit,
    icon: @Composable () -> Unit
) {

    Box(
        modifier = Modifier
            .background(

                if (selected)
                    Brush.horizontalGradient(
                        colors = listOf(
                            Color(0xFF2E7D32),
                            Color(0xFF43A047)
                        )
                    )
                else
                    Brush.horizontalGradient(
                        colors = listOf(
                            Color.Transparent,
                            Color.Transparent
                        )
                    ),

                RoundedCornerShape(26.dp)
            )

            .padding(
                horizontal = 26.dp,
                vertical = 14.dp
            ),

        contentAlignment = Alignment.Center
    ) {

        IconButton(
            onClick = onClick
        ) {

            CompositionLocalProvider(

                LocalContentColor provides

                        if (selected)
                            Color.White
                        else
                            Color.DarkGray
            ) {

                icon()
            }
        }
    }
}

@Composable
fun TimelineItem(
    date: String,
    title: String,
    subtitle: String,
    active: Boolean
) {

    Row(
        modifier = Modifier.fillMaxWidth()
    ) {

        // 🌿 TIMELINE LINE
        Column(
            horizontalAlignment =
                Alignment.CenterHorizontally
        ) {

            Box(
                modifier = Modifier
                    .size(26.dp)

                    .background(
                        Color.White,
                        CircleShape
                    ),

                contentAlignment =
                    Alignment.Center
            ) {

                Box(
                    modifier = Modifier
                        .size(14.dp)

                        .background(

                            if (active)
                                Color(0xFF10B15A)
                            else
                                Color(0xFFCFCFCF),

                            CircleShape
                        )
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Box(
                modifier = Modifier
                    .width(3.dp)
                    .height(220.dp)

                    .background(
                        Color(0xFFE4E4E4)
                    )
            )
        }

        Spacer(modifier = Modifier.width(18.dp))

        // 🌾 CARD
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .shadow(
                    elevation = 8.dp,
                    shape = RoundedCornerShape(32.dp)
                ),

            shape = RoundedCornerShape(32.dp),

            colors = CardDefaults.cardColors(

                containerColor =

                    if (active)
                        Color(0xFFF1FFF4)
                    else
                        Color.White
            )
        ) {

            Column(
                modifier = Modifier.padding(24.dp)
            ) {

                // 📅 DATE
                Text(
                    text = "📅 $date",

                    style =
                        MaterialTheme.typography.bodyMedium.copy(
                            color = Color.Gray,
                            fontWeight = FontWeight.Medium
                        )
                )

                Spacer(modifier = Modifier.height(14.dp))

                // 🌱 TITLE
                Text(
                    text = title,

                    style =
                        MaterialTheme.typography.headlineSmall.copy(
                            fontWeight = FontWeight.ExtraBold,
                            color = Color(0xFF1B1B1B)
                        )
                )

                Spacer(modifier = Modifier.height(16.dp))

                // 🚦 STATUS CHIP
                Surface(
                    shape = RoundedCornerShape(20.dp),

                    color =

                        when(subtitle) {

                            "COMPLETED" ->
                                Color(0xFFE8F5E9)

                            "SCHEDULED" ->
                                Color(0xFFE3F2FD)

                            else ->
                                Color(0xFFFFF8E1)
                        }
                ) {

                    Text(
                        text =

                            when(subtitle) {

                                "COMPLETED" ->
                                    "✅ Completed"

                                "SCHEDULED" ->
                                    "📅 Scheduled"

                                else ->
                                    "🕒 Pending"
                            },

                        modifier = Modifier.padding(
                            horizontal = 16.dp,
                            vertical = 8.dp
                        ),

                        style =
                            MaterialTheme.typography.labelLarge.copy(
                                fontWeight = FontWeight.Bold,

                                color =

                                    when(subtitle) {

                                        "COMPLETED" ->
                                            Color(0xFF2E7D32)

                                        "SCHEDULED" ->
                                            Color(0xFF1565C0)

                                        else ->
                                            Color(0xFFE65100)
                                    }
                            )
                    )
                }
            }
        }
    }
}