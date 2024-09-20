package com.example.lessonschedule.presentation.search

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import cafe.adriel.voyager.core.screen.Screen

class SearchScreen : Screen {


    data object State

    sealed interface Action

    @Composable
    override fun Content() {
        Box(modifier = Modifier.fillMaxSize().background(Color.Green))
    }
}