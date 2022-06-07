package com.release.inspectionapp.app.ui.mvi

import com.release.inspectionapp.utils.mvi.IState

data class AppState(
    val app: App
) : IState

sealed class App : IState {
    object Loading : App()
    data class UserEnteredTheApp(
        val graph: Int,
        val destination: Int
    ) : App()

    data class ErrorMessage(val msg: String) : App()
}
