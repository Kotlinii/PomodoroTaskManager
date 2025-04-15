package com.pomodorotaskmanager.ui.views

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.pomodorotaskmanager.ui.theme.PomodoroTaskManagerTheme
import kotlinx.coroutines.delay
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.ui.unit.Dp

@Composable
fun TimerScreen() {
    var fullTime by remember { mutableIntStateOf( 25 * 60) }
    var timeRemaining by remember { mutableIntStateOf(5 * 60 + 34) }
    var isRunning by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        TaskHeader(
            title = "Task Title Lorem Ipsum",
            description = "Task description lorem ipsum dolor sit amet...",
        )

        TimerDisplay(timeInSeconds = timeRemaining, fullTime = fullTime)

        ControlButtons(
            isRunning = isRunning,
            onPlayPause = { isRunning = !isRunning },
            onReset = {
                isRunning = false
                timeRemaining = 12 * 60 + 34
            }
        )
    }

    // Simple timer logic
    LaunchedEffect(isRunning) {
        if (isRunning) {
            while (timeRemaining > 0) {
                delay(1000L)
                timeRemaining--
            }
            isRunning = false
        }
    }
}

@Composable
fun TaskHeader(title: String, description: String) {

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        shape = RoundedCornerShape(12.dp),
        color = MaterialTheme.colorScheme.surface,
        tonalElevation = 2.dp,
        shadowElevation = 2.dp
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(24.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.onSurface,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = description,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
            )
        }
    }
}

@Composable
fun TimerDisplay(
    timeInSeconds: Int,
    fullTime: Int = 25 * 60,
    globalSize: Dp = 320.dp,
    modifier: Modifier = Modifier
) {
    val progress = timeInSeconds.toFloat() / fullTime.toFloat()
    val minutes = timeInSeconds / 60
    val seconds = timeInSeconds % 60

    val timerStrokeWidth = 16.dp

    // More intuitive colors based on progress
    val progressColor = when {
        progress > 0.6f -> MaterialTheme.colorScheme.primary
        progress > 0.3f -> MaterialTheme.colorScheme.tertiary
        else -> MaterialTheme.colorScheme.error
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier,
    ) {
        // Remaining time
        CircularProgressIndicator(
            progress = 1f,
            color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f),
            strokeWidth = timerStrokeWidth,
            modifier = Modifier.size(globalSize)
        )

        // Elapsed time
        CircularProgressIndicator(
            progress = progress,
            color = progressColor,
            strokeWidth = timerStrokeWidth,
            modifier = Modifier.size(globalSize).zIndex(1.0f),
        )

        Surface(
            shape = CircleShape,
            color = MaterialTheme.colorScheme.surface,
            modifier = Modifier.size(globalSize - timerStrokeWidth*2)
        ) {
            // Time text
            Box(contentAlignment = Alignment.Center) {
                Text(
                    text = String.format("%02d:%02d", minutes, seconds),
                    style = MaterialTheme.typography.displayLarge,
                    fontWeight = FontWeight.ExtraBold,
                    color = progressColor
                )
            }
        }
    }
}

@Composable
fun ControlButtons(
    isRunning: Boolean,
    onPlayPause: () -> Unit,
    onReset: () -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 32.dp, start = 16.dp, end = 16.dp)
    ) {
        Button(
            onClick = onPlayPause,
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary
            ),
            modifier = Modifier
                .weight(1f)
                .height(48.dp)
        ) {
            Icon(
                imageVector = if (isRunning) Icons.Default.Close else Icons.Default.PlayArrow,
                contentDescription = if (isRunning) "Pause" else "Resume",
                modifier = Modifier.padding(end = 8.dp)
            )
            Text(if (isRunning) "Pause" else "Resume")
        }

        Button(
            onClick = onReset,
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.secondary
            ),
            modifier = Modifier
                .weight(1f)
                .height(48.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Refresh,
                contentDescription = "Reset",
                modifier = Modifier.padding(end = 8.dp)
            )
            Text("Reset")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TimerScreenPreview() {
    PomodoroTaskManagerTheme {
        TimerScreen()
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun TimerScreenDarkPreview() {
    PomodoroTaskManagerTheme {
        TimerScreen()
    }
}