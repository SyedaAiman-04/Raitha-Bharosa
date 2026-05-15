package com.syeda.raithabharosa.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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

    val cardBrush = Brush.verticalGradient(
        colors = listOf(
            Color(0xFFFFFFFF),
            Color(0xFFF5FAF4)
        )
    )

    Card(
        modifier = Modifier.fillMaxWidth(),

        shape = RoundedCornerShape(34.dp),

        elevation = CardDefaults.cardElevation(
            defaultElevation = 8.dp
        )
    ) {

        Column(
            modifier = Modifier
                .background(cardBrush)
                .padding(22.dp)
        ) {

            // 🌾 HEADER
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Column {

                    Text(
                        text = "🌿 ${currentMonth.month} ${currentMonth.year}",

                        style = MaterialTheme.typography.headlineMedium.copy(
                            fontWeight = FontWeight.ExtraBold,
                            color = Color(0xFF1B5E20)
                        )
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = "Plan your farm smarter 🚜",

                        style = MaterialTheme.typography.bodySmall.copy(
                            color = Color.Gray
                        )
                    )
                }

                Text(
                    text = "📅",
                    fontSize = 34.sp
                )
            }

            Spacer(modifier = Modifier.height(26.dp))

            // 🌤 WEEK DAYS
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
                ).forEach { day ->

                    Text(
                        text = day,

                        fontWeight = FontWeight.ExtraBold,

                        color =
                            if (day == "SAT" || day == "SUN")
                                Color(0xFFE53935)
                            else
                                Color(0xFF6D6D6D),

                        fontSize = 13.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // 📆 DATES GRID
            dates.chunked(7).forEach { week ->

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 7.dp),

                    horizontalArrangement =
                        Arrangement.SpaceBetween
                ) {

                    week.forEach { date ->

                        if (date == null) {

                            Spacer(
                                modifier = Modifier.size(46.dp)
                            )

                        } else {

                            val isSelected =
                                date == selectedDate

                            val isWeekend =
                                date.dayOfWeek == DayOfWeek.SATURDAY ||
                                        date.dayOfWeek == DayOfWeek.SUNDAY

                            Box(
                                modifier = Modifier
                                    .size(48.dp)

                                    .background(

                                        brush =

                                            if (isSelected)

                                                Brush.radialGradient(
                                                    colors = listOf(
                                                        Color(0xFFE8F5E9),
                                                        Color(0xFFC8E6C9)
                                                    )
                                                )

                                            else

                                                Brush.radialGradient(
                                                    colors = listOf(
                                                        Color.Transparent,
                                                        Color.Transparent
                                                    )
                                                ),

                                        shape = CircleShape
                                    )

                                    .border(
                                        width =
                                            if (isSelected) 2.dp else 0.dp,

                                        color =
                                            if (isSelected)
                                                Color(0xFF66BB6A)
                                            else
                                                Color.Transparent,

                                        shape = CircleShape
                                    )

                                    .clickable {
                                        onDateSelected(date)
                                    },

                                contentAlignment =
                                    Alignment.Center
                            ) {

                                Column(
                                    horizontalAlignment =
                                        Alignment.CenterHorizontally
                                ) {

                                    Text(
                                        text =
                                            date.dayOfMonth.toString(),

                                        color =

                                            when {

                                                isSelected ->
                                                    Color(0xFF1B5E20)

                                                isWeekend ->
                                                    Color(0xFFE53935)

                                                else ->
                                                    Color(0xFF2F2F2F)
                                            },

                                        fontWeight =

                                            if (isSelected)
                                                FontWeight.ExtraBold
                                            else
                                                FontWeight.Medium
                                    )

                                    Spacer(
                                        modifier = Modifier.height(2.dp)
                                    )

                                    // 🌱 tiny activity indicator
                                    Box(
                                        modifier = Modifier
                                            .size(5.dp)

                                            .background(

                                                if (isSelected)
                                                    Color(0xFF43A047)
                                                else
                                                    Color(
                                                        0xFF81C784
                                                    ),

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
    }
}