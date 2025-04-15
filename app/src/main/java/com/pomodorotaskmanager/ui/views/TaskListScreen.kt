package com.pomodorotaskmanager.ui.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pomodorotaskmanager.core.TaskData
import com.pomodorotaskmanager.ui.theme.PomodoroTaskManagerTheme

@Composable
fun TimerWidget() {
    val simulatedFullTime = 25 * 60
    val simulatedTimeRemaining = 3 * 60 + 21
    val timerSize = 200.dp

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Header
        Text(
            text = "Do you want to continue where you left off?",
            style = MaterialTheme.typography.headlineMedium,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(top = 8.dp, bottom = 16.dp)
        )
        //  Timer
        TimerDisplay(
            timeInSeconds = simulatedTimeRemaining,
            fullTime = simulatedFullTime,
            globalSize = timerSize
        )

        // Resume button
        Button(
            onClick = { /* Resume action */ },
            modifier = Modifier.padding(top = 4.dp)
        ) {
            Text(text = "Resume")
        }
    }
}


@Composable
fun TaskListScreen(
    tasks: List<TaskData>,
    onEdit: (TaskData) -> Unit,
    onDelete: (TaskData) -> Unit,
    onReset: (TaskData) -> Unit,
    onPlay: (TaskData) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Timer
        TimerWidget()

        // Task List
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(tasks) { task ->
                TaskItem(
                    title = task.title,
                    description = task.description,
                    progress = task.progress,
                    onEdit = { onEdit(task) },
                    onDelete = { onDelete(task) },
                    onReset = { onReset(task) },
                    onPlay = { onPlay(task) }
                )
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewTaskListScreen() {
    val sampleTasks = listOf(
        TaskData("Design UI", "Mock the interface", 1),
        TaskData("Implement Logic", "Add logic for timer", 3),
        TaskData("Refactor Code", "Clean code structure", 4),
        TaskData("Write Documentation", "Document all components", 2)
    )

    PomodoroTaskManagerTheme {
        TaskListScreen(
            tasks = sampleTasks,
            onEdit = {},
            onDelete = {},
            onReset = {},
            onPlay = {}
        )
    }
}