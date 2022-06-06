package com.release.domain.question

import com.release.domain.getQuestionsItems
import com.release.domain.repositories.InspectionsRepository
import com.release.domain.usecase.questions.GetQuestionsUseCase
import com.release.domain.usecase.questions.GetQuestionsUseCaseImpl
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.given

@ExperimentalCoroutinesApi
class GetQuestionsUseCaseTest {

    private lateinit var getQuestionsUseCaseImpl: GetQuestionsUseCaseImpl

    @Mock
    lateinit var inspectionsRepository: InspectionsRepository

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        getQuestionsUseCaseImpl = GetQuestionsUseCaseImpl(inspectionsRepository)
    }

    @Test
    fun `getQuestionsUseCaseImpl will call inspectionRepo success`(): Unit = runBlocking {
        val questionId = 1
        val params = GetQuestionsUseCase.Params(questionId)
        val response = getQuestionsItems()

        given(inspectionsRepository.getQuestionsById(questionId)).willReturn(response)

        getQuestionsUseCaseImpl.execute(params)

        Mockito.verify(inspectionsRepository, Mockito.only()).getQuestionsById(questionId)
        assertEquals(getQuestionsUseCaseImpl.execute(params), response)
    }
}