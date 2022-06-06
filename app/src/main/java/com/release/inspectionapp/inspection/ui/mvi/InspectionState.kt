package com.release.inspectionapp.inspection.ui.mvi

import com.release.domain.model.InspectionItem
import com.release.inspectionapp.utils.mvi.IState

data class InspectionState(
    val inspections: Inspections
) : IState

sealed class Inspections : IState {
    object Loading : Inspections()
    data class SavedInspections(val inspectionItems: List<InspectionItem>) : Inspections()
    data class ReceivedInspections(val inspectionItems: List<InspectionItem>) : Inspections()
    object NoSavedInspections : Inspections()
    data class InspectionSent(val message: String) : Inspections()
    data class ErrorMessage(val msg: String) : Inspections()
    object UserSignedOut : Inspections()
}