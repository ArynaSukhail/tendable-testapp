package com.release.domain.inspections

import com.release.domain.getInspectionItems
import com.release.domain.repositories.InspectionsRepository
import com.release.domain.usecase.None
import com.release.domain.usecase.inspection.StartInspectionUseCaseImpl
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
class StartInspectionUseCaseTest {

    private lateinit var startInspectionUseCaseImpl: StartInspectionUseCaseImpl

    @Mock
    lateinit var inspectionsRepository: InspectionsRepository

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        startInspectionUseCaseImpl = StartInspectionUseCaseImpl(inspectionsRepository)
    }

    @Test
    fun `startInspectionUseCaseImpl will call inspectionRepo success`(): Unit = runBlocking {
        val params = None
        val response = getInspectionItems()

        given(inspectionsRepository.startInspection()).willReturn(response)

        startInspectionUseCaseImpl.execute(params)

        Mockito.verify(inspectionsRepository, Mockito.only()).startInspection()
        assertEquals(startInspectionUseCaseImpl.execute(params), response)
    }
}