package com.release.inspectionapp.auth.ui.signup.mvi

import com.release.inspectionapp.utils.NavAction
import com.release.inspectionapp.utils.mvi.IState

data class SignUpState(
    val signup: SignUp
) : IState

sealed class SignUp : IState {
    object Loading : SignUp()
    data class UserSignedUp(val navId: NavAction.ChangeGraph) : SignUp()
    data class ErrorMessage(val msg: String) : SignUp()
}