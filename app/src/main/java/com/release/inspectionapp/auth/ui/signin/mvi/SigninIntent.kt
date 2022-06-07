package com.release.inspectionapp.auth.ui.signin.mvi

import com.release.inspectionapp.utils.mvi.IIntent

sealed class SigninIntent : IIntent {
    data class SigninUser(
        val email: String,
        val password: String
    ) : SigninIntent()

    object SignupNewUser : SigninIntent()
}