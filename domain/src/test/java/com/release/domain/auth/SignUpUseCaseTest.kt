package com.release.domain.auth

import com.release.domain.repositories.AuthRepository
import com.release.domain.usecase.auth.SignUpUseCase
import com.release.domain.usecase.auth.SignUpUseCaseImpl
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.given

@ExperimentalCoroutinesApi
class SignUpUseCaseTest {

    private lateinit var signUpUseCaseImpl: SignUpUseCaseImpl

    @Mock
    lateinit var authRepository: AuthRepository

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        signUpUseCaseImpl = SignUpUseCaseImpl(authRepository)
    }

    @Test
    fun `signUpUseCaseImpl will call authRepo success`() = runBlocking {
        val email = "email"
        val password = "password"
        val params = SignUpUseCase.Params(email, password)

        given(authRepository.signUp(email, password)).willReturn(Unit)

        signUpUseCaseImpl.execute(params)

        Mockito.verify(authRepository, Mockito.only()).signUp(email, password)
    }
}