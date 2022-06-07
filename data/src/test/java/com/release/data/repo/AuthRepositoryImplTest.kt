package com.release.data.repo

import com.release.data.getFakeSignURequest
import com.release.data.getFakeSigninRequest
import com.release.data.prefs.AppPrefs
import com.release.data.repositories.AuthRepositoryImpl
import com.release.data.service.ApiService
import com.release.data.utils.NetworkCall
import com.release.domain.repositories.AuthRepository
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations

@ExperimentalCoroutinesApi
class AuthRepoImplTest {

    lateinit var authRepo: AuthRepository

    @Mock
    lateinit var apiService: ApiService

    @Mock
    lateinit var networkCall: NetworkCall

    @Mock
    lateinit var appPrefs: AppPrefs

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        authRepo = AuthRepositoryImpl(appPrefs, apiService, networkCall)
    }

    @Test
    fun `signin request success`() {
        val email = "email"
        val password = "password"

        val signinRequestBody = getFakeSigninRequest(email, password)
        val responseSignin = Unit
        val expectedResult = Unit

        runBlocking {
            `when`(apiService.signin(signinRequestBody)).thenReturn(responseSignin)

            val actualResult = authRepo.signin(email, password)

            assertEquals(expectedResult, actualResult)
            verify(apiService, only()).signin(signinRequestBody)
            verify(appPrefs, only()).setEnteredKey(true)
        }
    }

    @Test(expected = Exception::class)
    fun `signin request not success`() {
        val email = "email"
        val password = "password"

        val signinRequestBody = getFakeSigninRequest(email, password)
        runBlocking {
            `when`(apiService.signin(signinRequestBody)).thenThrow(Exception())

            authRepo.signin(email, password)

            assertEquals(Exception(), apiService.signin(signinRequestBody))
            verify(apiService, only()).signin(signinRequestBody)
            verify(appPrefs, never()).setEnteredKey(true)
        }
    }

    @Test
    fun `signup request success`() {
        val email = "email"
        val password = "password"
        val isUserEntered = true

        val signupRequestBody = getFakeSignURequest(email, password)
        val responseSignUp = Unit
        val expectedResult = Unit

        runBlocking {
            `when`(apiService.signUp(signupRequestBody)).thenReturn(responseSignUp)

            val actualResult = authRepo.signUp(email, password)

            assertEquals(expectedResult, actualResult)
            verify(apiService, only()).signUp(signupRequestBody)
            verify(appPrefs, only()).setEnteredKey(isUserEntered)
        }
    }

    @Test(expected = Exception::class)
    fun `signup request not success`() {
        val email = "email"
        val password = "password"
        val isUserEntered = true

        val signupRequestBody = getFakeSignURequest(email, password)
        runBlocking {
            `when`(apiService.signUp(signupRequestBody)).thenThrow(Exception())

            authRepo.signin(email, password)

            assertEquals(Exception(), apiService.signUp(signupRequestBody))
            verify(apiService, only()).signUp(signupRequestBody)
            verify(appPrefs, never()).setEnteredKey(isUserEntered)
        }
    }

    @Test
    fun `isUserEntered success`() {

        val isUserEntered = true
        val expectedResult = true

        runBlocking {
            `when`(appPrefs.getEnteredKey()).thenReturn(isUserEntered)

            val actualResult = authRepo.getUserEnteredKey()

            assertEquals(expectedResult, actualResult)
            verify(appPrefs, only()).getEnteredKey()
        }
    }

    @Test
    fun `signout success`() {

        val isUserEntered = false
        val expectedResult = Unit

        runBlocking {
            val actualResult = authRepo.signout()

            assertEquals(expectedResult, actualResult)
            verify(appPrefs, only()).setEnteredKey(isUserEntered)
            assertEquals(appPrefs.getEnteredKey(), isUserEntered)
        }
    }
}