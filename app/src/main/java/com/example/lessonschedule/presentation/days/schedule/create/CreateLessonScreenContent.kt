package com.example.lessonschedule.presentation.days.schedule.create

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastForEach
import cafe.adriel.voyager.navigator.LocalNavigator
import com.example.lessonschedule.domain.LessonType
import com.example.lessonschedule.presentation.getTitle
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateLessonScreenContent(
    state: CreateLessonScreen.State,
    onAction: (CreateLessonScreen.Action) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Добавить к расписанию") },
                navigationIcon = {
                    val navigator = LocalNavigator.current
                    Icon(
                        modifier = Modifier
                            .clip(CircleShape)
                            .clickable {
                                navigator?.pop()
                            },
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = null
                    )
                }
            )
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(start = 16.dp, end = 16.dp, top = innerPadding.calculateTopPadding())
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = state.title.text,
                isError = state.title.isError,
                label = {
                    Text("Название предмета")
                },
                onValueChange = { value ->
                    onAction(CreateLessonScreen.Action.TitleUpdate(value))
                }
            )

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = state.venue.text,
                isError = state.venue.isError,
                label = {
                    Text("Место проведение")
                },
                onValueChange = { value ->
                    onAction(CreateLessonScreen.Action.VenueUpdate(value))
                }
            )

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = state.teacherName.text,
                isError = state.teacherName.isError,
                label = {
                    Text("Имя преподавателя")
                },
                onValueChange = { value ->
                    onAction(CreateLessonScreen.Action.TeacherUpdate(value))
                }
            )

            var expanded by remember { mutableStateOf(false) }

            ExposedDropdownMenuBox(
                modifier = Modifier.fillMaxWidth(),
                expanded = expanded,
                onExpandedChange = { expanded = it }
            ) {
                OutlinedTextField(
                    modifier = Modifier
                        .menuAnchor(MenuAnchorType.PrimaryNotEditable)
                        .fillMaxWidth(),
                    value = state.selectedLessonType?.getTitle().orEmpty(),
                    readOnly = true,
                    singleLine = true,
                    onValueChange = {},
                    colors = ExposedDropdownMenuDefaults.textFieldColors(),
                    label = { Text(text = "Тип предмета") }
                )

                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    LessonType.entries.fastForEach {
                        DropdownMenuItem(
                            modifier = Modifier.fillMaxWidth(),
                            text = {
                                Text(
                                    text = it.getTitle(),
                                    style = MaterialTheme.typography.bodyLarge
                                )
                            },
                            onClick = {
                                onAction(CreateLessonScreen.Action.LessonTypeUpdate(it))
                                expanded = false
                            }
                        )
                    }
                }
            }

            var showDatePicker by remember { mutableStateOf(false) }
            val datePickerState = rememberDatePickerState()
            val selectedDate = remember(datePickerState.selectedDateMillis) {
                datePickerState.selectedDateMillis?.let {
                    convertMillisToDate(it)
                } ?: ""
            }

            Box(
                modifier = Modifier.fillMaxWidth()
            ) {
                OutlinedTextField(
                    value = selectedDate,
                    onValueChange = { },
                    label = { Text("DOB") },
                    readOnly = true,
                    enabled = false,
                    trailingIcon = {
                        IconButton(onClick = { showDatePicker = !showDatePicker }) {
                            Icon(
                                imageVector = Icons.Default.DateRange,
                                contentDescription = "Select date"
                            )
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(64.dp)
                )

                if (showDatePicker) {
                    DatePickerDialog(
                        onDismissRequest = { showDatePicker = false },
                        confirmButton = { Text("ok") }
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .shadow(elevation = 4.dp)
                                .background(MaterialTheme.colorScheme.surface)
                                .padding(16.dp)
                        ) {
                            DatePicker(
                                state = datePickerState,
                                showModeToggle = false
                            )
                        }
                    }
                }
            }
        }
    }
}

fun convertMillisToDate(millis: Long): String {
    val formatter = SimpleDateFormat("MM/dd/yyyy", Locale.getDefault())
    return formatter.format(Date(millis))
}