package com.example.lessonschedule.presentation.days.schedule.create

import com.example.lessonschedule.presentation.base.BaseScreenModel

class CreateLessonScreenModel : BaseScreenModel<CreateLessonScreen.Action, CreateLessonScreen.State>(
    initialState = CreateLessonScreen.State()
) {
    override fun onAction(action: CreateLessonScreen.Action) {
        when (action) {
            is CreateLessonScreen.Action.DateSelected -> TODO()
            is CreateLessonScreen.Action.LessonTypeUpdate -> TODO()
            is CreateLessonScreen.Action.TeacherUpdate -> TODO()
            is CreateLessonScreen.Action.TimeSelected -> TODO()
            is CreateLessonScreen.Action.TitleUpdate -> TODO()
            is CreateLessonScreen.Action.VenueUpdate -> TODO()
        }
    }
}