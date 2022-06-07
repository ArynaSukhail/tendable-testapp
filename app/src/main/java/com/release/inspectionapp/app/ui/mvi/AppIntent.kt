package com.release.inspectionapp.app.ui.mvi

import com.release.inspectionapp.utils.mvi.IIntent

sealed class AppIntent : IIntent {
    object UserEnteredTheApp : AppIntent()
}
