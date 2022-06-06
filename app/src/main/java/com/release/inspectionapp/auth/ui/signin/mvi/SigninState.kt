package com.release.inspectionapp.auth.ui.signin.mvi

import com.release.inspectionapp.utils.NavAction
import com.release.inspectionapp.utils.mvi.IState

data class SigninState(
    val signin: Signin
) : IState

sealed class Signin : IState {
    object Loading : Signin()
    data class UserSignedIn(val navId: NavAction.ChangeGraph) : Signin()
    data class ErrorMessage(val msg: String) : Signin()
}