package com.release.inspectionapp.auth.ui.signin

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.release.domain.usecase.auth.SignInUseCase
import com.release.domain.utils.AppException
import com.release.inspectionapp.R
import com.release.inspectionapp.auth.ui.signin.mvi.Signin
import com.release.inspectionapp.auth.ui.signin.mvi.SigninIntent
import com.release.inspectionapp.auth.ui.signin.mvi.SigninState
import com.release.inspectionapp.utils.NavAction
import com.release.inspectionapp.utils.ResourceManager
import com.release.inspectionapp.utils.livedata.Event
import com.release.inspectionapp.utils.mvi.IModel
import com.release.inspectionapp.utils.presentation.BaseViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class SignInViewModel @Inject constructor(
    private val signInUseCase: SignInUseCase,
    private val resourceManager: ResourceManager
) : BaseViewModel(), IModel<SigninState, SigninIntent> {

    override val intents: Channel<SigninIntent> = Channel(Channel.UNLIMITED)

    private val _state = MutableLiveData(SigninState(Signin.Loading))
    override val state: LiveData<SigninState>
        get() = _state

    init {
        handlerIntent()
    }

    private fun handlerIntent() {
        viewModelScope.launch {
            intents.consumeAsFlow().collect { intent ->
                when (intent) {
                    is SigninIntent.SigninUser -> onSigninButtonClicked(
                        intent.email,
                        intent.password
                    )
                    is SigninIntent.SignupNewUser -> onSignUpNewUserButtonClicked()
                }
            }
        }
    }

    private fun onSigninButtonClicked(email: String, password: String) {
        viewModelScope.launch(handler) {
            try {
                signInUseCase.execute(SignInUseCase.Params(email, password))
                updateState {
                    SigninState(
                        Signin.UserSignedIn(
                            NavAction.ChangeGraph(R.navigation.main_graph)
                        )
                    )
                }
            } catch (e: AppException) {
                updateState { SigninState(Signin.ErrorMessage(e.message.toString())) }
            }
        }
    }

    private fun onSignUpNewUserButtonClicked() {
        _navigationEvent.value =
            Event(NavAction.Forward(R.id.action_signinFragment_to_signUpFragment))
    }

    private suspend fun updateState(handler: suspend (intent: SigninState) -> SigninState) {
        val value = state.value
        if (value != null) {
            _state.postValue(handler(value))
        }
    }
}