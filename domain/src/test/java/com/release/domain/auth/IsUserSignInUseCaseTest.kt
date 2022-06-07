package com.release.domain.auth

import com.release.domain.repositories.AuthRepository
import com.release.domain.usecase.None
import com.release.domain.usecase.auth.IsUserSignedInUseCaseImpl
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.given

@ExperimentalCoroutinesApi
class IsUserSignInUseCaseTest {

    private lateinit var isUserSignedInUseCaseImpl: IsUserSignedInUseCaseImpl

    @Mock
    lateinit var authRepository: AuthRepository

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        isUserSignedInUseCaseImpl = IsUserSignedInUseCaseImpl(authRepository)
    }

    @Test
    fun `isUserSignedInUseCaseImpl will call authRepo success`(): Unit = runBlocking {
        val params = None
        val isUserSignedIn = true

        given(authRepository.getUserEnteredKey()).willReturn(isUserSignedIn)

        isUserSignedInUseCaseImpl.execute(params)

        Mockito.verify(authRepository, Mockito.only()).getUserEnteredKey()
    }
}