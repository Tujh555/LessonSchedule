package com.example.lessonschedule.presentation.days.schedule.create

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import com.example.lessonschedule.domain.LessonType
import com.example.lessonschedule.presentation.base.BaseScreen
import com.example.lessonschedule.presentation.base.BaseScreenModel
import com.example.lessonschedule.presentation.models.InputState
import java.time.Instant

class CreateLessonScreen : BaseScreen<CreateLessonScreen.Action, CreateLessonScreen.State>() {
    @Immutable
    data class State(
        val title: InputState = InputState(),
        val venue: InputState = InputState(),
        val teacherName: InputState = InputState(),
        val selectedLessonType: LessonType? = null,
        val selectedDate: Instant? = null,
        val selectedTime: Pair<Int, Int>? = null
    )

    @Immutable
    sealed interface Action {
        data class TitleUpdate(val text: String) : Action
        data class VenueUpdate(val text: String) : Action
        data class TeacherUpdate(val text: String) : Action
        data class LessonTypeUpdate(val type: LessonType) : Action
        data class DateSelected(val date: Long) : Action
        data class TimeSelected(val time: Pair<Int, Int>) : Action
    }

    @Composable
    override fun Content(state: State, onAction: (Action) -> Unit) {
        CreateLessonScreenContent(state, onAction)
    }

    override fun createModel(): BaseScreenModel<Action, State> =
        CreateLessonScreenModel()
}