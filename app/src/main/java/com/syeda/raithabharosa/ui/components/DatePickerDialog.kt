package com.syeda.raithabharosa.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import java.time.LocalDate
import java.time.YearMonth

@Composable
fun DatePickerDialog(

    selectedDate: LocalDate,

    onDateSelected: (LocalDate) -> Unit,

    onDismiss: () -> Unit
) {

    var currentMonth by remember {
        mutableStateOf(
            YearMonth.from(selectedDate)
        )
    }

    AlertDialog(
        onDismissRequest = {
            onDismiss()
        },

        confirmButton = {},

        containerColor = Color.White,

        shape = RoundedCornerShape(32.dp),

        text = {

            Column {

                // 🔹 MONTH HEADER
                Row(
                    modifier = Modifier.fillMaxWidth(),

                    horizontalArrangement =
                        Arrangement.SpaceBetween,

                    verticalAlignment =
                        Alignment.CenterVertically
                ) {

                    IconButton(
                        onClick = {
                            currentMonth =
                                currentMonth.minusMonths(1)
                        }
                    ) {

                        Icon(
                            Icons.Default.KeyboardArrowLeft,
                            contentDescription = null
                        )
                    }

                    Text(
                        text =
                            "${currentMonth.month} ${currentMonth.year}",

                        style =
                            MaterialTheme.typography.titleLarge
                    )

                    IconButton(
                        onClick = {
                            currentMonth =
                                currentMonth.plusMonths(1)
                        }
                    ) {

                        Icon(
                            Icons.Default.KeyboardArrowRight,
                            contentDescription = null
                        )
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                // 🔹 REUSE EXISTING CALENDAR GRID
                CalendarGrid(
                    currentMonth = currentMonth,
                    selectedDate = selectedDate,

                    onDateSelected = {

                        onDateSelected(it)

                        onDismiss()
                    }
                )
            }
        }
    )
}