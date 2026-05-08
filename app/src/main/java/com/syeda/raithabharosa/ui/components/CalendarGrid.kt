package com.syeda.raithabharosa.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.YearMonth

@Composable
fun CalendarGrid(
    currentMonth: YearMonth,
    selectedDate: LocalDate,
    onDateSelected: (LocalDate) -> Unit
) {

    val firstDayOfMonth = currentMonth.atDay(1)
    val daysInMonth = currentMonth.lengthOfMonth()

    val startOffset =
        (firstDayOfMonth.dayOfWeek.value % 7)

    val dates = mutableListOf<LocalDate?>()

    repeat(startOffset) {
        dates.add(null)
    }

    for (day in 1..daysInMonth) {
        dates.add(currentMonth.atDay(day))
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(36.dp),
        elevation = CardDefaults.cardElevation(6.dp)
    ) {

        Column(
            modifier = Modifier.padding(20.dp)
        ) {

            // 🔹 Month Header
            Text(
                text = "${currentMonth.month} ${currentMonth.year}",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(20.dp))

            // 🔹 Week Days
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                listOf(
                    "MON",
                    "TUE",
                    "WED",
                    "THU",
                    "FRI",
                    "SAT",
                    "SUN"
                ).forEach {

                    Text(
                        text = it,
                        fontWeight = FontWeight.Bold,
                        color = Color.Gray
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // 🔹 Calendar Dates
            dates.chunked(7).forEach { week ->

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {

                    week.forEach { date ->

                        if (date == null) {

                            Spacer(modifier = Modifier.size(40.dp))

                        } else {

                            val isSelected =
                                date == selectedDate

                            Box(
                                modifier = Modifier
                                    .size(42.dp)
                                    .background(
                                        if (isSelected)
                                            Color(0xFFE8F5E9)
                                        else
                                            Color.Transparent,
                                        CircleShape
                                    )
                                    .clickable {
                                        onDateSelected(date)
                                    },
                                contentAlignment = Alignment.Center
                            ) {

                                Text(
                                    text = date.dayOfMonth.toString(),
                                    color =
                                        if (
                                            date.dayOfWeek == DayOfWeek.SATURDAY ||
                                            date.dayOfWeek == DayOfWeek.SUNDAY
                                        )
                                            Color.Red
                                        else
                                            Color.DarkGray,
                                    fontWeight =
                                        if (isSelected)
                                            FontWeight.Bold
                                        else
                                            FontWeight.Normal
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}