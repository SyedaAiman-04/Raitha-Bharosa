package com.syeda.raithabharosa.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import com.syeda.raithabharosa.ui.models.CalendarTask

@Composable
fun TaskSection(
    tasks: List<CalendarTask>
) {

    Spacer(modifier = Modifier.height(20.dp))

    if (tasks.isEmpty()) {

        Text(
            text = "No tasks scheduled",
            color = Color.Gray,
            fontStyle = FontStyle.Italic,
            style = MaterialTheme.typography.bodySmall
        )

    } else {

        tasks.forEach { task ->

            Text(
                text = "• ${task.title}"
            )

            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}