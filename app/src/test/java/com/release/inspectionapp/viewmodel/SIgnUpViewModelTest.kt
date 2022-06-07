package com.release.inspectionapp.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.release.domain.usecase.auth.SignUpUseCase
import com.release.inspectionapp.R
import com.release.inspectionapp.auth.ui.signup.SignUpViewModel
import com.release.inspectionapp.auth.ui.signup.mvi.SignUp
import com.release.inspectionapp.auth.ui.signup.mvi.SignUpIntent
import com.release.inspectionapp.auth.ui.signup.mvi.SignUpState
import com.release.inspectionapp.utils.NavAction
import com.release.inspectionapp.utils.ResourceManager
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

@ExperimentalCoroutinesApi
class SIgnUpViewModelTest {

    @get:Rule
    val executorRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    var coroutinesTestRule = MainCoroutineRule()

    lateinit var viewModel: SignUpViewModel

    @Mock
    lateinit var signUpUseCase: SignUpUseCase

    @Mock
    lateinit var resourceManager: ResourceManager

    @Mock
    private lateinit var observer: Observer<SignUpState>

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        viewModel = SignUpViewModel(signUpUseCase, resourceManager)
        observer = Observer<SignUpState> {}
        viewModel.state.observeForever(observer)
    }

    @Test
    fun `signup success`() {
        val email = "email"
        val password = "password"
        val signUpIntent = SignUpIntent.SignUpUser(email, password)
        val params = SignUpUseCase.Params(email, password)
        val signupResponse = Unit
        val expectedViewState =
            SignUpState(SignUp.UserSignedUp(NavAction.ChangeGraph(R.navigation.main_graph)))

        runBlocking {
            Mockito.`when`(signUpUseCase.execute(params)).thenReturn(signupResponse)
            viewModel.intents.send(signUpIntent)
        }

        val actualViewState = viewModel.state
        Assert.assertEquals(actualViewState.value!!.signup, expectedViewState.signup)
    }

    @Test
    fun `signup not success`() {
        val email = "email"
        val password = "password"
        val signUpIntent = SignUpIntent.SignUpUser(email, password)
        val params = SignUpUseCase.Params(email, password)
        val expectedViewState =
            SignUpState(SignUp.UserSignedUp(NavAction.ChangeGraph(R.navigation.main_graph)))

        runBlocking {
            Mockito.`when`(signUpUseCase.execute(params)).thenThrow(CancellationException())
            viewModel.intents.send(signUpIntent)
        }

        val actualViewState = viewModel.state
        Assert.assertNotEquals(actualViewState.value!!.signup, expectedViewState.signup)
    }
}