package com.syeda.raithabharosa.ui.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.syeda.raithabharosa.ui.components.AddTaskDialog
import com.syeda.raithabharosa.ui.components.CalendarGrid
import com.syeda.raithabharosa.ui.components.TaskSection
import com.syeda.raithabharosa.ui.models.CalendarTask
import com.syeda.raithabharosa.utils.FirebaseTaskManager
import java.time.LocalDate
import java.time.YearMonth

@Composable
fun KrishiCalendarScreen(navController: NavHostController) {

    var currentMonth by remember {
        mutableStateOf(YearMonth.now())
    }

    var selectedDate by remember {
        mutableStateOf(LocalDate.now())
    }

    var showDialog by remember {
        mutableStateOf(false)
    }

    val allTasks = remember {
        mutableStateListOf<CalendarTask>()
    }

    LaunchedEffect(Unit) {

        FirebaseTaskManager.getTasks { firebaseTasks ->

            allTasks.clear()
            allTasks.addAll(firebaseTasks)
        }
    }

    val tasksForSelectedDate =
        allTasks.filter {
            it.date == selectedDate.toString()
        }

    val snackbarHostState = remember {
        SnackbarHostState()
    }

    val context = LocalContext.current

    val backgroundBrush = Brush.verticalGradient(
        colors = listOf(
            Color(0xFFF4FAF2),
            Color(0xFFFFFFFF)
        )
    )

    Scaffold(
        snackbarHost = {
            SnackbarHost(snackbarHostState)
        },

        containerColor = Color.Transparent
    ) { paddingValues ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(backgroundBrush)
        ) {

            Column(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 20.dp)
            ) {

                Spacer(modifier = Modifier.height(38.dp))

                // 🌱 TOP HEADER
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Surface(
                        shape = CircleShape,
                        color = Color.White,
                        shadowElevation = 6.dp,
                        modifier = Modifier.size(50.dp)
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

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        Text(
                            text = "🌾 Krishi Calendar",
                            style = MaterialTheme.typography.headlineSmall.copy(
                                fontWeight = FontWeight.ExtraBold,
                                color = Color(0xFF1B5E20)
                            )
                        )

                        Text(
                            text = "Plan your farming smarter",
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = Color.Gray
                            )
                        )
                    }

                    FloatingActionButton(
                        onClick = {
                            showDialog = true
                        },

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

                Spacer(modifier = Modifier.height(26.dp))

                // 🚀 QUICK ACTIONS
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {

                    ElevatedButton(
                        onClick = {
                            currentMonth =
                                currentMonth.minusMonths(1)
                        },

                        shape = RoundedCornerShape(18.dp),

                        colors = ButtonDefaults.elevatedButtonColors(
                            containerColor = Color(0xFF1B5E20)
                        ),

                        modifier = Modifier.weight(1f)
                    ) {

                        Text(
                            text = "⬅ Prev",
                            color = Color.White
                        )
                    }

                    ElevatedButton(
                        onClick = {
                            currentMonth =
                                currentMonth.plusMonths(1)
                        },

                        shape = RoundedCornerShape(18.dp),

                        colors = ButtonDefaults.elevatedButtonColors(
                            containerColor = Color(0xFF1B5E20)
                        ),

                        modifier = Modifier.weight(1f)
                    ) {

                        Text(
                            text = "Next ➡",
                            color = Color.White
                        )
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // 🌟 FEATURE BUTTONS
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {

                    ElevatedCard(
                        onClick = {
                            navController.navigate("timeline")
                        },

                        modifier = Modifier.weight(1f),

                        shape = RoundedCornerShape(22.dp),

                        colors = CardDefaults.elevatedCardColors(
                            containerColor = Color.White
                        )
                    ) {

                        Column(
                            modifier = Modifier.padding(18.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {

                            Icon(
                                Icons.Default.Schedule,
                                contentDescription = null,
                                tint = Color(0xFF1B5E20)
                            )

                            Spacer(modifier = Modifier.height(10.dp))

                            Text(
                                text = "⏳ Timeline",
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }

                    ElevatedCard(
                        onClick = {
                            navController.navigate("tasklist")
                        },

                        modifier = Modifier.weight(1f),

                        shape = RoundedCornerShape(22.dp),

                        colors = CardDefaults.elevatedCardColors(
                            containerColor = Color.White
                        )
                    ) {

                        Column(
                            modifier = Modifier.padding(18.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {

                            Icon(
                                Icons.Default.CheckCircle,
                                contentDescription = null,
                                tint = Color(0xFF10B15A)
                            )

                            Spacer(modifier = Modifier.height(10.dp))

                            Text(
                                text = "✅ Tasks",
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                // 🌤 WEATHER INSIGHT CARD
                ElevatedCard(
                    modifier = Modifier.fillMaxWidth(),

                    shape = RoundedCornerShape(28.dp),

                    colors = CardDefaults.elevatedCardColors(
                        containerColor = Color.White
                    )
                ) {

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(22.dp),

                        horizontalArrangement = Arrangement.SpaceAround
                    ) {

                        WeatherMiniCard(
                            emoji = "☀",
                            title = "28°C",
                            subtitle = "Sunny"
                        )

                        WeatherMiniCard(
                            emoji = "🌱",
                            title = "Season",
                            subtitle = "Kharif"
                        )

                        WeatherMiniCard(
                            emoji = "💧",
                            title = "Humidity",
                            subtitle = "64%"
                        )
                    }
                }

                Spacer(modifier = Modifier.height(22.dp))

                // 📅 CALENDAR
                ElevatedCard(
                    modifier = Modifier.fillMaxWidth(),

                    shape = RoundedCornerShape(30.dp),

                    colors = CardDefaults.elevatedCardColors(
                        containerColor = Color.White
                    ),

                    elevation = CardDefaults.elevatedCardElevation(
                        defaultElevation = 6.dp
                    )
                ) {

                    Column(
                        modifier = Modifier.padding(18.dp)
                    ) {

//                        Text(
//                            text = "📅 ${currentMonth.month} ${currentMonth.year}",
//                            style = MaterialTheme.typography.headlineSmall.copy(
//                                fontWeight = FontWeight.ExtraBold,
//                                color = Color(0xFF1B5E20)
//                            )
                        //)

                        Spacer(modifier = Modifier.height(18.dp))

                        CalendarGrid(
                            currentMonth = currentMonth,
                            selectedDate = selectedDate,
                            onDateSelected = {
                                selectedDate = it
                            }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // 📝 TASK SECTION
                ElevatedCard(
                    modifier = Modifier.fillMaxWidth(),

                    shape = RoundedCornerShape(28.dp),

                    colors = CardDefaults.elevatedCardColors(
                        containerColor = Color.White
                    )
                ) {

                    Column(
                        modifier = Modifier.padding(20.dp)
                    ) {

                        Text(
                            text = "📝 Activities",
                            style = MaterialTheme.typography.titleLarge.copy(
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF1B5E20)
                            )
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        TaskSection(
                            tasks = tasksForSelectedDate
                        )
                    }
                }

                Spacer(modifier = Modifier.height(120.dp))
            }
        }

        // ➕ ADD TASK DIALOG
        if (showDialog) {

            AddTaskDialog(

                selectedDate = selectedDate.toString(),

                onDismiss = {
                    showDialog = false
                },

                onAddTask = { title, status ->

                    val task = CalendarTask(
                        title = title,
                        date = selectedDate.toString(),
                        status = status
                    )

                    FirebaseTaskManager.addTask(

                        task = task,

                        onSuccess = {

                            allTasks.add(task)

                            Toast.makeText(
                                context,
                                "Task Added",
                                Toast.LENGTH_SHORT
                            ).show()
                        },

                        onFailure = {

                            Toast.makeText(
                                context,
                                it,
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    )
                }
            )
        }
    }
}

@Composable
fun WeatherMiniCard(
    emoji: String,
    title: String,
    subtitle: String
) {

    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = emoji,
            fontSize = 24.sp
        )

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = title,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF1B5E20)
        )

        Text(
            text = subtitle,
            style = MaterialTheme.typography.bodySmall.copy(
                color = Color.Gray
            )
        )
    }
}