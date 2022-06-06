package com.release.domain.inspections

import com.release.domain.getInspectionItems
import com.release.domain.repositories.InspectionsRepository
import com.release.domain.usecase.None
import com.release.domain.usecase.inspection.GetSavedInspectionUseCaseImpl
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
class GetSavedInspectionUseCaseTest {

    private lateinit var getSavedInspectionUseCaseImpl: GetSavedInspectionUseCaseImpl

    @Mock
    lateinit var inspectionsRepository: InspectionsRepository

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        getSavedInspectionUseCaseImpl = GetSavedInspectionUseCaseImpl(inspectionsRepository)
    }

    @Test
    fun `getSavedInspectionUseCaseImpl will call inspectionRepo success`(): Unit = runBlocking {
        val params = None
        val response = getInspectionItems()

        given(inspectionsRepository.getSavedInspections()).willReturn(response)

        getSavedInspectionUseCaseImpl.execute(params)

        Mockito.verify(inspectionsRepository, Mockito.only()).getSavedInspections()
        Assert.assertEquals(getSavedInspectionUseCaseImpl.execute(params), response)
    }
}