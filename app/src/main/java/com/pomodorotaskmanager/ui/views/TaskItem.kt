package com.pomodorotaskmanager.ui.views

import android.content.res.Configuration
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pomodorotaskmanager.ui.theme.PomodoroTaskManagerTheme

@Composable
fun TaskInfo(
    title: String,
    description: String,
    progress: Int,
    onEdit: () -> Unit,
    onDelete: () -> Unit,
    onReset: () -> Unit,
    onPlay: () -> Unit
) {
    val colorScheme = MaterialTheme.colorScheme
    val textColor = colorScheme.onSurface
    val iconColor = colorScheme.onSurface.copy(alpha = 0.7f)
    val buttonBackground = colorScheme.background

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Top
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                color = textColor,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = description,
                color = textColor.copy(alpha = 0.7f),
                fontSize = 14.sp
            )
        }

        Row(
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            modifier = Modifier
                .padding(start = 8.dp)
                .align(Alignment.Top)
        ) {
            IconButton(onClick = onEdit) {
                Icon(Icons.Default.Edit, "Edit", tint = iconColor)
            }
            IconButton(onClick = onDelete) {
                Icon(Icons.Default.Delete, "Delete", tint = iconColor)
            }

            if (progress >= 4) {
                // Reset button with emphasis
                IconButton(
                    onClick = onReset,
                    modifier = Modifier
                        .background(buttonBackground, shape = RoundedCornerShape(6.dp))
                        .border(1.dp, iconColor, RoundedCornerShape(6.dp))
                ) {
                    Icon(
                        imageVector = Icons.Default.Refresh,
                        contentDescription = "Reset",
                        tint = iconColor
                    )
                }
            } else {
                // Play button with emphasis
                IconButton(
                    onClick = onPlay,
                    modifier = Modifier
                        .background(buttonBackground, shape = RoundedCornerShape(6.dp))
                        .border(1.dp, iconColor, RoundedCornerShape(6.dp))
                ) {
                    Icon(
                        imageVector = Icons.Default.PlayArrow,
                        contentDescription = "Start",
                        tint = iconColor,

                    )
                }
            }
        }
    }
}

@Composable
fun TaskItem(
    title: String,
    description: String,
    progress: Int,
    onEdit: () -> Unit,
    onDelete: () -> Unit,
    onReset: () -> Unit,
    onPlay: () -> Unit
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
        ),
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp)
            .padding(8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            TaskInfo(
                title = title,
                description = description,
                onEdit = onEdit,
                onDelete = onDelete,
                onReset = onReset,
                onPlay = onPlay,
                progress = progress
            )

            // Pomodoro progress bar (unchanged)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                repeat(4) { index ->
                    PomodoroBar(
                        isFilled = index < progress,
                        isLastCompleted = index == 3 && progress >= 4,
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }
    }
}

@Composable
fun PomodoroBar(
    isFilled: Boolean,
    isLastCompleted: Boolean,
    modifier: Modifier = Modifier
) {
    val color = when {
        isLastCompleted -> Color(0xFFFFC107)
        isFilled -> MaterialTheme.colorScheme.primary
        else -> MaterialTheme.colorScheme.surface.copy(alpha = 0.3f)
    }

    val stripeColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f)

    Canvas(
        modifier = modifier
            .height(8.dp)
            .clip(RoundedCornerShape(4.dp))
    ) {
        // Background
        drawRect(color)

        // Hatching only for incomplete bars
        if (!isFilled) {
             val stripeWidth = 4.dp.toPx()
            val spacing = 6.dp.toPx()

            val height = size.height
            val width = size.width

            var x = -height

            while (x < width) {
                drawLine(
                    color = stripeColor,
                    start = Offset(x, 0f),
                    end = Offset(x + height, height),
                    strokeWidth = stripeWidth
                )
                x += spacing
            }
        }
    }
}



@Preview(name = "Light Mode", uiMode = Configuration.UI_MODE_NIGHT_NO, showBackground = true)
@Composable
fun PreviewLightMode() {
    PomodoroTaskManagerTheme {
        TaskItem(
            title = "Test the application UI",
            description = "Check if the UI looks good!",
            progress = (0..3).random(),
            onEdit = { /* Handle Edit */ },
            onDelete = { /* Handle Delete */ },
            onReset = { /* Handle Reset */},
            onPlay = { /* Handle Run */ }
        )
    }
}
@Preview(name = "Light Mode Full Bars", uiMode = Configuration.UI_MODE_NIGHT_NO, showBackground = true)
@Composable
fun PreviewLightModeFullBars() {
    PomodoroTaskManagerTheme {
        TaskItem(
            title = "Test the application UI",
            description = "Check if the UI looks good!",
            progress = 4,
            onEdit = { /* Handle Edit */ },
            onDelete = { /* Handle Delete */ },
            onReset = { /* Handle Reset */},
            onPlay = { /* Handle Run */ }
        )
    }
}

@Preview(name = "Dark Mode", uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun PreviewDarkMode() {
    PomodoroTaskManagerTheme {
        TaskItem(
            title = "Test the application UI",
            description = "Check if the UI looks good!",
            progress = (0..4).random(),
            onEdit = { /* Handle Edit */ },
            onDelete = { /* Handle Delete */ },
            onReset = { /* Handle Reset */},
            onPlay = { /* Handle Run */ }
        )
    }
}

@Preview(name = "Dark Mode Full Bars", uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun PreviewDarkModeFullBars() {
    PomodoroTaskManagerTheme {
        TaskItem(
            title = "Test the application UI",
            description = "It does! 😀",
            progress = 4,
            onEdit = { /* Handle Edit */ },
            onDelete = { /* Handle Delete */ },
            onReset = { /* Handle Reset */},
            onPlay = { /* Handle Run */ }
        )
    }
}
