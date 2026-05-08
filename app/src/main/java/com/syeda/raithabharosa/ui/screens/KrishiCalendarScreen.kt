package com.syeda.raithabharosa.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.syeda.raithabharosa.ui.components.CalendarGrid
import com.syeda.raithabharosa.ui.components.TaskSection
import com.syeda.raithabharosa.ui.models.CalendarTask
import java.time.LocalDate
import java.time.YearMonth
import com.syeda.raithabharosa.ui.components.AddTaskDialog

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
    // 🔥 Dummy tasks
    val allTasks = listOf(
        CalendarTask(
            title = "Sow Paddy Seeds",
            date = LocalDate.now().toString(),
            status = "PENDING"
        )
    )

    val tasksForSelectedDate =
        allTasks.filter {
            it.date == selectedDate.toString()
        }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF9F7EF))
            .verticalScroll(rememberScrollState())
            .padding(20.dp)
    ) {

        Spacer(modifier = Modifier.height(10.dp))

        // 🔹 TOP BAR
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            IconButton(
                onClick = {
                    navController.popBackStack()
                }
            ) {
                Icon(Icons.Default.ArrowBack, null)
            }

            Text(
                text = "Krishi Calendar",
                style = MaterialTheme.typography.headlineMedium
            )

            FloatingActionButton(
                onClick = {
                    showDialog = true
                },
                containerColor = Color(0xFF10B15A)
            ) {
                Icon(Icons.Default.Add, null)
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // 🔹 ACTION ROW
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {

            Button(
                onClick = {
                    currentMonth =
                        currentMonth.minusMonths(1)
                }
            ) {
                Text("‹")
            }

            Button(
                onClick = {
                    currentMonth =
                        currentMonth.plusMonths(1)
                }
            ) {
                Text("›")
            }

            IconButton(
                onClick = {
                    navController.navigate("timeline")
                }
            ) {
                Icon(Icons.Default.Schedule, null)
            }

            IconButton(
                onClick = {
                    navController.navigate("tasklist")
                }
            ) {
                Icon(Icons.Default.CheckCircle, null)
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // 🔹 CALENDAR GRID
        CalendarGrid(
            currentMonth = currentMonth,
            selectedDate = selectedDate,
            onDateSelected = {
                selectedDate = it
            }
        )

        Spacer(modifier = Modifier.height(24.dp))

        // 🔹 TASKS
        TaskSection(
            tasks = tasksForSelectedDate
        )

        Spacer(modifier = Modifier.height(120.dp))
    }
    if (showDialog) {

        AddTaskDialog(
            onDismiss = {
                showDialog = false
            }
        )
    }
}