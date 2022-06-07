package com.release.inspectionapp.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.release.domain.usecase.inspection.UpdateSavedInspectionQuizUseCase
import com.release.domain.usecase.questions.GetQuestionsUseCase
import com.release.inspectionapp.questions.ui.QuestionsViewModel
import com.release.inspectionapp.questions.ui.mvi.Questions
import com.release.inspectionapp.questions.ui.mvi.QuestionsIntent
import com.release.inspectionapp.questions.ui.mvi.QuestionsState
import com.release.inspectionapp.utils.ResourceManager
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
class QuestionsViewModelTest {

    @get:Rule
    val executorRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    var coroutinesTestRule = MainCoroutineRule()

    lateinit var viewModel: QuestionsViewModel

    @Mock
    lateinit var getQuestionsUseCase: GetQuestionsUseCase

    @Mock
    lateinit var updateSavedInspectionQuizUseCase: UpdateSavedInspectionQuizUseCase

    @Mock
    lateinit var resourceManager: ResourceManager

    @Mock
    private lateinit var observer: Observer<QuestionsState>

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        viewModel = QuestionsViewModel(
            getQuestionsUseCase,
            updateSavedInspectionQuizUseCase,
            resourceManager
        )
        observer = Observer<QuestionsState> {}
        viewModel.state.observeForever(observer)
    }

    @Test
    fun `getQuestionsUseCase state successfully changed`() {
        val questionItems = getFakeQuestionItems()
        val inspectionId = 1
        val getQuestionsIntent = QuestionsIntent.GetQuestions(inspectionId)
        val params = GetQuestionsUseCase.Params(1)
        val expectedViewState = QuestionsState(Questions.GetQuestions(questionItems))

        runBlocking {
            Mockito.`when`(getQuestionsUseCase.execute(params)).thenReturn(questionItems)
            viewModel.intents.send(getQuestionsIntent)
        }

        val actualViewState = viewModel.state
        Assert.assertEquals(actualViewState.value!!.questions, expectedViewState.questions)
    }

    @Test
    fun `updateSelectedQuestion state successfully changed`() {
        val isQuestionUpdated = true
        val questionId = 1
        val answerId = 1
        val getQuestionsIntent = QuestionsIntent.SaveSelectedQuestion(questionId, answerId)
        val params = UpdateSavedInspectionQuizUseCase.Params(questionId, answerId)
        val expectedViewState = QuestionsState(Questions.SelectedQuestionsSaved)

        runBlocking {
            Mockito.`when`(updateSavedInspectionQuizUseCase.execute(params))
                .thenReturn(isQuestionUpdated)
            viewModel.intents.send(getQuestionsIntent)
        }

        val actualViewState = viewModel.state
        Assert.assertEquals(actualViewState.value!!.questions, expectedViewState.questions)
    }
}