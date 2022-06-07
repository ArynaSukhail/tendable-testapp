package com.release.domain.usecase.auth

import com.release.domain.repositories.AuthRepository
import com.release.domain.usecase.BaseUseCase
import javax.inject.Inject

interface SignUpUseCase {
    suspend fun execute(params: Params)

    data class Params(
        val email: String,
        val password: String
    )
}

class SignUpUseCaseImpl @Inject constructor(
    private val authRepository: AuthRepository
) : BaseUseCase<Unit, SignUpUseCase.Params>(), SignUpUseCase {

    override suspend fun execute(params: SignUpUseCase.Params) {
        authRepository.signUp(
            params.email,
            params.password
        )
    }
}