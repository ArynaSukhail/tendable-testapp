package com.release.inspectionapp.auth.ui.signup.mvi

import com.release.inspectionapp.utils.mvi.IIntent

sealed class SignUpIntent : IIntent {
    data class SignUpUser(
        val email: String,
        val password: String
    ) : SignUpIntent()
}