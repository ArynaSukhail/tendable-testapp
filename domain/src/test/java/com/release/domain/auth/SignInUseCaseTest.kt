package com.release.domain.auth

import com.release.domain.repositories.AuthRepository
import com.release.domain.usecase.auth.SignInUseCase
import com.release.domain.usecase.auth.SignInUseCaseImpl
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Test

import org.junit.Before
import org.mockito.Mock
import org.mockito.Mockito.only
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.given

@ExperimentalCoroutinesApi
class SignInUseCaseTest {

    private lateinit var signInUseCaseImpl: SignInUseCaseImpl

    @Mock
    lateinit var authRepository: AuthRepository

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        signInUseCaseImpl = SignInUseCaseImpl(authRepository)
    }

    @Test
    fun `signInUseCaseImpl will call authRepo success`() = runBlocking {
        val email = "email"
        val password = "password"
        val params = SignInUseCase.Params(email, password)

        given(authRepository.signin(email, password)).willReturn(Unit)

        signInUseCaseImpl.execute(params)

        verify(authRepository, only()).signin(email, password)
    }
}