package com.example.lessonschedule.presentation.models

import androidx.compose.runtime.Immutable
import com.example.lessonschedule.domain.LessonType

@Immutable
data class LessonItem(
    val id: Int,
    val title: String,
    val startAt: String,
    val endAt: String,
    val type: LessonType,
    val venue: String,
    val teacherName: String
)