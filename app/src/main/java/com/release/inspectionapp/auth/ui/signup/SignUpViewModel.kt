package com.release.inspectionapp.auth.ui.signup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.release.domain.usecase.auth.SignUpUseCase
import com.release.domain.utils.AppException
import com.release.inspectionapp.R
import com.release.inspectionapp.auth.ui.signup.mvi.SignUp
import com.release.inspectionapp.auth.ui.signup.mvi.SignUpIntent
import com.release.inspectionapp.auth.ui.signup.mvi.SignUpState
import com.release.inspectionapp.utils.NavAction
import com.release.inspectionapp.utils.ResourceManager
import com.release.inspectionapp.utils.mvi.IModel
import com.release.inspectionapp.utils.presentation.BaseViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class SignUpViewModel @Inject constructor(
    private val signUpUseCase: SignUpUseCase,
    private val resourceManager: ResourceManager
) : BaseViewModel(), IModel<SignUpState, SignUpIntent> {

    override val intents: Channel<SignUpIntent> = Channel(Channel.UNLIMITED)

    private val _state = MutableLiveData(SignUpState(SignUp.Loading))
    override val state: LiveData<SignUpState>
        get() = _state

    init {
        handlerIntent()
    }

    private fun handlerIntent() {
        viewModelScope.launch {
            intents.consumeAsFlow().collect { intent ->
                when (intent) {
                    is SignUpIntent.SignUpUser -> onSignUpButtonClicked(
                        intent.email,
                        intent.password
                    )
                }
            }
        }
    }

    private fun onSignUpButtonClicked(email: String, password: String) {
        viewModelScope.launch(handler) {
            if (email.isNotEmpty() && password.isNotEmpty()) {
                try {
                    signUpUseCase.execute(SignUpUseCase.Params(email, password))
                    updateState {
                        SignUpState(
                            SignUp.UserSignedUp(
                                NavAction.ChangeGraph(R.navigation.main_graph)
                            )
                        )
                    }
                } catch (e: AppException) {
                    updateState { SignUpState(SignUp.ErrorMessage(e.message.toString())) }
                }
            } else {
                updateState { SignUpState(SignUp.ErrorMessage(
                    resourceManager.getString(R.string.fillout_all_fields)))
                }
            }
        }
    }

    private suspend fun updateState(handler: suspend (intent: SignUpState) -> SignUpState) {
        val value = state.value
        if (value != null) {
            _state.postValue(handler(value))
        }
    }
}