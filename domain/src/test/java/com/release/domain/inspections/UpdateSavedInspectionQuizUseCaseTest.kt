package com.release.domain.inspections

import com.release.domain.repositories.InspectionsRepository
import com.release.domain.usecase.inspection.UpdateSavedInspectionQuizUseCase
import com.release.domain.usecase.inspection.UpdateSavedInspectionQuizUseCaseImpl
import junit.framework.Assert
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.given

@ExperimentalCoroutinesApi
class UpdateSavedInspectionQuizUseCaseTest {

    private lateinit var updateSavedInspectionQuizUseCaseImpl: UpdateSavedInspectionQuizUseCaseImpl

    @Mock
    lateinit var inspectionsRepository: InspectionsRepository

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        updateSavedInspectionQuizUseCaseImpl = UpdateSavedInspectionQuizUseCaseImpl(inspectionsRepository)
    }

    @Test
    fun `updateSavedInspectionQuizUseCaseImpl will call inspectionRepo success`(): Unit = runBlocking {
        val questionId = 1
        val answerId = 2
        val params = UpdateSavedInspectionQuizUseCase.Params(questionId, answerId)
        val response = true

        given(inspectionsRepository.updateQuestionAnswer(questionId, answerId)).willReturn(response)

        updateSavedInspectionQuizUseCaseImpl.execute(params)

        Mockito.verify(inspectionsRepository, Mockito.only())
            .updateQuestionAnswer(questionId, answerId)
        Assert.assertEquals(updateSavedInspectionQuizUseCaseImpl.execute(params), response)
    }
}