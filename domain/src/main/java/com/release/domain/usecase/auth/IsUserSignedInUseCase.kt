package com.release.domain.usecase.auth

import com.release.domain.repositories.AuthRepository
import com.release.domain.usecase.BaseUseCase
import com.release.domain.usecase.None
import javax.inject.Inject

interface IsUserSignedInUseCase {
    suspend fun execute(params: None): Boolean
}

class IsUserSignedInUseCaseImpl @Inject constructor(
    val authRepository: AuthRepository
) : BaseUseCase<Boolean, None>(), IsUserSignedInUseCase {

    override suspend fun execute(params: None): Boolean {
        return authRepository.getUserEnteredKey()
    }
}