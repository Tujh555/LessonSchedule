package com.example.lessonschedule.presentation.days.schedule

import androidx.compose.foundation.pager.PagerState
import com.example.lessonschedule.DependencyHolder
import com.example.lessonschedule.domain.LessonType
import com.example.lessonschedule.presentation.base.BaseScreenModel
import com.example.lessonschedule.presentation.models.LessonItem
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DaysScheduleScreenModel : BaseScreenModel<DaysScheduleScreen.Action, DaysScheduleScreen.State>(
    initialState = DaysScheduleScreen.State()
) {
    private val repository = DependencyHolder.lessonRepository
    init {
        val items = List(10) {
            LessonItem(
                id = it,
                title = "Title $it",
                startAt = "8:00",
                endAt = "9:00",
                type = LessonType.entries[it % 3],
                venue = "venue $it",
                teacherName = "teacher name $it"
            )
        }

        repeat(5) { i ->
            _state.update {
                val lessons = it.lessons + Pair("$i.09", items)
                it.copy(
                    lessons = lessons,
                    pagerState = PagerState(
                        currentPage = 0,
                        pageCount = { lessons.size }
                    )
                )
            }
        }
    }

    override fun onAction(action: DaysScheduleScreen.Action) {
        when (action) {
            DaysScheduleScreen.Action.CloseViewDialog -> setViewedItem(null)
            is DaysScheduleScreen.Action.OnItemClick -> setViewedItem(action.item)
            is DaysScheduleScreen.Action.DeleteItem -> deleteLesson(action.item)
        }
    }

    private fun deleteLesson(item: LessonItem) {
        ioScope.launch {
            repository.deleteLesson(item.id)
        }
    }

    private fun setViewedItem(item: LessonItem?) {
        _state.update {
            it.copy(viewedItem = item)
        }
    }
}