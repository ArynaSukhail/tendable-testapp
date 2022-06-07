package com.release.inspectionapp.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.release.domain.usecase.auth.SignInUseCase
import com.release.inspectionapp.R
import com.release.inspectionapp.auth.ui.signin.SignInViewModel
import com.release.inspectionapp.auth.ui.signin.mvi.Signin
import com.release.inspectionapp.auth.ui.signin.mvi.SigninIntent
import com.release.inspectionapp.auth.ui.signin.mvi.SigninState
import com.release.inspectionapp.utils.NavAction
import com.release.inspectionapp.utils.ResourceManager
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

@ExperimentalCoroutinesApi
class SignInViewModelTest {

    @get:Rule
    val executorRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    var coroutinesTestRule = MainCoroutineRule()

    lateinit var viewModel: SignInViewModel

    @Mock
    lateinit var signInUseCase: SignInUseCase

    @Mock
    lateinit var resourceManager: ResourceManager

    @Mock
    private lateinit var observer: Observer<SigninState>

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        viewModel = SignInViewModel(signInUseCase, resourceManager)
        observer = Observer<SigninState> {}
        viewModel.state.observeForever(observer)
    }

    @Test
    fun `signin success`() {
        val email = "email"
        val password = "password"
        val signInIntent = SigninIntent.SigninUser(email, password)
        val params = SignInUseCase.Params(email, password)
        val signinResponse = Unit
        val expectedViewState =
            SigninState(Signin.UserSignedIn(NavAction.ChangeGraph(R.navigation.main_graph)))

        runBlocking {
            `when`(signInUseCase.execute(params)).thenReturn(signinResponse)
            viewModel.intents.send(signInIntent)
        }

        val actualViewState = viewModel.state
        assertEquals(actualViewState.value!!.signin, expectedViewState.signin)
    }

    @Test
    fun `signin not success`() {
        val email = "email"
        val password = "password"
        val signInIntent = SigninIntent.SigninUser(email, password)
        val params = SignInUseCase.Params(email, password)
        val expectedViewState =
            SigninState(Signin.UserSignedIn(NavAction.ChangeGraph(R.navigation.main_graph)))

        runBlocking {
            `when`(signInUseCase.execute(params)).thenThrow(CancellationException())
            viewModel.intents.send(signInIntent)
        }

        val actualViewState = viewModel.state
        assertNotEquals(actualViewState.value!!.signin, expectedViewState.signin)
    }
}