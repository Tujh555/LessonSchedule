package com.example.lessonschedule.presentation.models

import androidx.compose.runtime.Immutable

@Immutable
data class InputState(val text: String = "", val isError: Boolean = false)