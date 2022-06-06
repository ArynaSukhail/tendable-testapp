package com.release.domain.auth

import com.release.domain.repositories.AuthRepository
import com.release.domain.usecase.None
import com.release.domain.usecase.auth.SignOutUseCaseImpl
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.given

@ExperimentalCoroutinesApi
class SignOutUseCaseTest {

    private lateinit var signOutUseCaseImpl: SignOutUseCaseImpl

    @Mock
    lateinit var authRepository: AuthRepository

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        signOutUseCaseImpl = SignOutUseCaseImpl(authRepository)
    }

    @Test
    fun `signOutUseCaseImpl will call authRepo success`() = runBlocking {
        val params = None

        given(authRepository.signout()).willReturn(Unit)

        signOutUseCaseImpl.execute(params)

        Mockito.verify(authRepository, Mockito.only()).signout()
    }
}