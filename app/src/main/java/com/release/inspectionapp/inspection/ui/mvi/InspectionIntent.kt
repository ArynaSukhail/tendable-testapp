package com.release.inspectionapp.inspection.ui.mvi

import com.release.inspectionapp.utils.mvi.IIntent

sealed class InspectionIntent : IIntent {
    object ShowInspections : InspectionIntent()
    object FetchInspections : InspectionIntent()
    object SendFinishedInspection : InspectionIntent()
    data class GoForward(val id: Int) : InspectionIntent()
    object SignOut : InspectionIntent()
}