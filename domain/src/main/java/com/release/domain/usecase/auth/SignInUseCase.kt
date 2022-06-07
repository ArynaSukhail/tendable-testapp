package com.release.domain.usecase.auth

import com.release.domain.repositories.AuthRepository
import com.release.domain.usecase.BaseUseCase
import javax.inject.Inject

interface SignInUseCase {
    suspend fun execute(params: Params)

    data class Params(
        val email: String,
        val password: String
    )
}

class SignInUseCaseImpl @Inject constructor(
    private val authRepository: AuthRepository
) : BaseUseCase<Unit, SignInUseCase.Params>(), SignInUseCase {

    override suspend fun execute(params: SignInUseCase.Params) {
        authRepository.signin(
            params.email,
            params.password
        )
    }
}
