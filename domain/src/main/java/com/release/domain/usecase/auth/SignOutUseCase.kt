package com.release.domain.usecase.auth

import com.release.domain.repositories.AuthRepository
import com.release.domain.usecase.BaseUseCase
import com.release.domain.usecase.None
import javax.inject.Inject

interface SignOutUseCase {
    suspend fun execute(params: None)
}

class SignOutUseCaseImpl @Inject constructor(
    private val authRepository: AuthRepository
) : BaseUseCase<Unit, None>(), SignOutUseCase {

    override suspend fun execute(params: None) {
        authRepository.signout()
    }
}
