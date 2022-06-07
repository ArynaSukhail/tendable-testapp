package com.release.domain.inspections

import com.release.domain.repositories.InspectionsRepository
import com.release.domain.usecase.inspection.SendInspectionUseCase
import com.release.domain.usecase.inspection.SendInspectionUseCaseImpl
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.given

@ExperimentalCoroutinesApi
class SendInspectionUseCaseTest {

    private lateinit var sendInspectionUseCaseImpl: SendInspectionUseCaseImpl

    @Mock
    lateinit var inspectionsRepository: InspectionsRepository

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        sendInspectionUseCaseImpl = SendInspectionUseCaseImpl(inspectionsRepository)
    }

    @Test
    fun `sendInspectionUseCaseImpl will call inspectionRepo success`(): Unit = runBlocking {
        val inspectionId = 1
        val params = SendInspectionUseCase.Params(inspectionId)

        given(inspectionsRepository.sendInspection(inspectionId)).willReturn(Unit)

        sendInspectionUseCaseImpl.execute(params)

        Mockito.verify(inspectionsRepository, Mockito.only()).sendInspection(inspectionId)
    }
}