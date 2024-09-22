package com.example.lessonschedule.presentation.days.schedule.create

import androidx.compose.runtime.internal.illegalDecoyCallException
import com.example.lessonschedule.domain.LessonType
import com.example.lessonschedule.presentation.base.BaseScreenModel
import com.example.lessonschedule.presentation.models.InputState
import kotlinx.coroutines.flow.update

class CreateLessonScreenModel : BaseScreenModel<CreateLessonScreen.Action, CreateLessonScreen.State>(
    initialState = CreateLessonScreen.State()
) {
    override fun onAction(action: CreateLessonScreen.Action) {
        when (action) {
            is CreateLessonScreen.Action.DateSelected -> onDateSelected(action.date)
            is CreateLessonScreen.Action.LessonTypeUpdate -> onLessonTypeSelected(action.type)
            is CreateLessonScreen.Action.TeacherUpdate -> onTeacherNameUpdate(action.text)
            is CreateLessonScreen.Action.StartTimeSelected -> onStartTimeSelected(action.time)
            is CreateLessonScreen.Action.TitleUpdate -> onTitleUpdate(action.text)
            is CreateLessonScreen.Action.VenueUpdate -> onVenueUpdate(action.text)
            is CreateLessonScreen.Action.EndTimeSelected -> onEndTimeSelected(action.time)
        }
    }

    private fun onDateSelected(date: Long) {
        _state.update {
            it.copy(
                selectedDate = InputState(date)
            )
        }
    }

    private fun onLessonTypeSelected(type: LessonType) {
        _state.update {
            it.copy(selectedLessonType = type)
        }
    }

    private fun onTeacherNameUpdate(name: String) {
        _state.update {
            it.copy(teacherName = InputState(value = name))
        }
    }

    private fun onTitleUpdate(name: String) {
        _state.update {
            it.copy(title = InputState(value = name))
        }
    }

    private fun onVenueUpdate(name: String) {
        _state.update {
            it.copy(venue = InputState(value = name))
        }
    }

    private fun onStartTimeSelected(time: Pair<Int, Int>) {
        _state.update {
            it.copy(selectedTimeStart = InputState(value = time))
        }
    }

    private fun onEndTimeSelected(time: Pair<Int, Int>) {
        _state.update {
            it.copy(selectedTimeEnd = InputState(value = time))
        }
    }

    private fun validateState(): Boolean {
        val currentState = state.value

        val startTime = currentState.selectedTimeStart.value
        val endTime = currentState.selectedTimeEnd.value

        val isTimeRangeError = startTime == null ||
                endTime == null ||
                (startTime.first + startTime.second * 60) > (endTime.first + endTime.second * 60)

        _state.update {
            it.copy(
                title = validateTextInput(it.title),
                teacherName = validateTextInput(it.teacherName),
                venue = validateTextInput(it.venue),
                selectedTimeStart = it.selectedTimeStart.copy(isError = isTimeRangeError),
                selectedTimeEnd = it.selectedTimeEnd.copy(isError = isTimeRangeError),
                selectedDate = it.selectedDate.copy(isError = it.selectedDate.value == null)
            )
        }

        state.value::class.members

        return
    }

    private fun validateTextInput(inputState: InputState<String>): InputState<String> {
        if (inputState.value.isBlank()) {
            return inputState.copy(isError = true)
        }

        return inputState
    }
}