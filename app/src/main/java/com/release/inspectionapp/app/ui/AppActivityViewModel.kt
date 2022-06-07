package com.release.inspectionapp.app.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.release.domain.usecase.None
import com.release.domain.usecase.auth.IsUserSignedInUseCase
import com.release.domain.utils.AppException
import com.release.inspectionapp.R
import com.release.inspectionapp.app.ui.mvi.App
import com.release.inspectionapp.app.ui.mvi.AppIntent
import com.release.inspectionapp.app.ui.mvi.AppState
import com.release.inspectionapp.utils.mvi.IModel
import com.release.inspectionapp.utils.presentation.BaseViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class AppActivityViewModel @Inject constructor(
    private val isUserSignedInUseCase: IsUserSignedInUseCase
) : BaseViewModel(), IModel<AppState, AppIntent> {

    override val intents: Channel<AppIntent> = Channel(Channel.UNLIMITED)

    private val _state = MutableLiveData(AppState(App.Loading))
    override val state: LiveData<AppState>
        get() = _state

    init {
        handlerIntent()
    }

    private fun handlerIntent() {
        viewModelScope.launch {
            intents.consumeAsFlow().collect { intent ->
                when (intent) {
                    is AppIntent.UserEnteredTheApp -> onUserEnteredTheApp()
                }
            }
        }
    }

    private fun onUserEnteredTheApp() {
        viewModelScope.launch(handler) {
            try {
                val isUserLoggedIn = isUserSignedInUseCase.execute(None)
                if (!isUserLoggedIn) {
                    updateState {
                        AppState(
                            App.UserEnteredTheApp(
                                R.navigation.auth_graph,
                                R.id.signinFragment
                            )
                        )
                    }
                } else {
                    updateState {
                        AppState(
                            App.UserEnteredTheApp(
                                R.navigation.main_graph,
                                R.id.savedInspectionFragment
                            )
                        )
                    }
                }
            } catch (e: AppException) {
                updateState { AppState(App.ErrorMessage(e.message.toString())) }
            }
        }
    }

    private suspend fun updateState(handler: suspend (intent: AppState) -> AppState) {
        val value = state.value
        if (value != null) {
            _state.postValue(handler(value))
        }
    }
}