package com.release.inspectionapp.inspection.ui

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.release.domain.usecase.None
import com.release.domain.usecase.auth.SignOutUseCase
import com.release.domain.usecase.inspection.GetSavedInspectionUseCase
import com.release.domain.usecase.inspection.SendInspectionUseCase
import com.release.domain.usecase.inspection.StartInspectionUseCase
import com.release.domain.utils.AppException
import com.release.inspectionapp.R
import com.release.inspectionapp.inspection.ui.mvi.InspectionIntent
import com.release.inspectionapp.inspection.ui.mvi.InspectionState
import com.release.inspectionapp.inspection.ui.mvi.Inspections
import com.release.inspectionapp.utils.NavAction
import com.release.inspectionapp.utils.ResourceManager
import com.release.inspectionapp.utils.livedata.Event
import com.release.inspectionapp.utils.mvi.IModel
import com.release.inspectionapp.utils.presentation.BaseViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class InspectionsViewModel @Inject constructor(
    private val signOutUseCase: SignOutUseCase,
    private val getSavedInspectionsUseCase: GetSavedInspectionUseCase,
    private val sendInspectionUseCase: SendInspectionUseCase,
    private val requestStartInspectionUseCase: StartInspectionUseCase,
    private val resourceManager: ResourceManager
) : BaseViewModel(), IModel<InspectionState, InspectionIntent> {

    val btnStartVisibility = MutableLiveData<Int>()
    val noStoredInspectionsVisibility = MutableLiveData<Int>()
    val rvVisibility = MutableLiveData<Int>()
    val btnSendVisibility = MutableLiveData<Int>()

    override val intents: Channel<InspectionIntent> = Channel(Channel.UNLIMITED)

    private val _state = MutableLiveData(InspectionState(Inspections.Loading))
    override val state: LiveData<InspectionState>
        get() = _state

    private var inspectionId: Int = 0

    init {
        handlerIntent()
    }

    private fun handlerIntent() {
        viewModelScope.launch {
            intents.consumeAsFlow().collect { intent ->
                when (intent) {
                    is InspectionIntent.ShowInspections -> onGetSavedData()
                    is InspectionIntent.FetchInspections -> onStartButtonClicked()
                    is InspectionIntent.SendFinishedInspection -> onSendButtonClicked()
                    is InspectionIntent.SignOut -> onLogoutButtonClicked()
                    is InspectionIntent.GoForward -> onContinueButtonClicked(intent.id)
                }
            }
        }
    }

    private fun onGetSavedData() {
        viewModelScope.launch(handler) {
            try {
                val inspectionsItems = getSavedInspectionsUseCase.execute(None)
                if (inspectionsItems.isEmpty()) {
                    updateState { InspectionState(Inspections.NoSavedInspections) }
                    viewVisibilities(View.VISIBLE, View.GONE)
                } else {
                    updateState { InspectionState(Inspections.SavedInspections(inspectionsItems)) }
                    inspectionId = inspectionsItems.first().id
                    viewVisibilities(View.GONE, View.VISIBLE)
                }
            } catch (e: AppException) {
                updateState { InspectionState(Inspections.ErrorMessage(e.message.toString())) }
            }
        }
    }

    private fun onStartButtonClicked() {
        viewModelScope.launch(handler) {
            val request = async { requestStartInspectionUseCase.execute(None) }
            if (request.await().isNotEmpty()) {
                val inspectionItems = getSavedInspectionsUseCase.execute(None)
                updateState { InspectionState(Inspections.SavedInspections(inspectionItems)) }
                inspectionId = inspectionItems.first().id
                viewVisibilities(View.GONE, View.VISIBLE)
            } else {
                updateState { InspectionState(Inspections.NoSavedInspections) }
                viewVisibilities(View.VISIBLE, View.GONE)
            }
        }
    }

    private fun onSendButtonClicked() {
        viewModelScope.launch(handler) {
            try {
                sendInspectionUseCase.execute(SendInspectionUseCase.Params(inspectionId))
                updateState {
                    InspectionState(
                        Inspections.InspectionSent(
                            resourceManager.getString(R.string.successfully_sent)
                        )
                    )
                }
                viewVisibilities(View.VISIBLE, View.GONE)
            } catch (e: AppException) {
                updateState { InspectionState(Inspections.ErrorMessage(e.message.toString())) }
            }
        }
    }

    private fun onLogoutButtonClicked() {
        viewModelScope.launch(handler) {
            try {
                signOutUseCase.execute(None)
                _navigationEvent.value = Event(NavAction.ChangeGraph(R.navigation.auth_graph))
                updateState { InspectionState(Inspections.UserSignedOut) }
            } catch (e: AppException) {
                updateState { InspectionState(Inspections.ErrorMessage(e.message.toString())) }
            }
        }
    }

    private fun onContinueButtonClicked(id: Int) {
        _navigationEvent.value = Event(
            NavAction.ForwardWithBundle(
                R.id.action_savedInspectionFragment_to_inspectionQuizFragment,
                id
            )
        )
    }

    private fun viewVisibilities(visible: Int, visible2: Int) {
        btnStartVisibility.value = visible
        noStoredInspectionsVisibility.value = visible
        rvVisibility.value = visible2
        btnSendVisibility.value = visible2
    }

    private suspend fun updateState(handler: suspend (intent: InspectionState) -> InspectionState) {
        val value = state.value
        if (value != null) {
            _state.postValue(handler(value))
        }
    }
}