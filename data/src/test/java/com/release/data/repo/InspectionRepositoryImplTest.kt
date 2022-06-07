package com.release.data.repo

import com.nhaarman.mockitokotlin2.only
import com.release.data.*
import com.release.data.database.dbrepository.InspectionsRealm
import com.release.data.model.StartResponse
import com.release.data.repositories.InspectionsRepositoryImpl
import com.release.data.service.ApiService
import com.release.data.utils.NetworkCall
import com.release.data.utils.mapper.DataEntitiesMapper
import com.release.domain.model.InspectionItem
import com.release.domain.repositories.InspectionsRepository
import junit.framework.Assert.assertNotSame
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.never
import org.mockito.kotlin.verify

@ExperimentalCoroutinesApi
class InspectionRepositoryImplTest {

    lateinit var inspectionsRepository: InspectionsRepository

    @Mock
    lateinit var inspectionRealm: InspectionsRealm

    @Mock
    lateinit var inspectionMapper: DataEntitiesMapper<StartResponse, InspectionItem>

    @Mock
    lateinit var apiService: ApiService

    @Mock
    lateinit var networkCall: NetworkCall

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        inspectionsRepository =
            InspectionsRepositoryImpl(inspectionRealm, apiService, networkCall, inspectionMapper)
    }

    @Test
    fun `startInspection request success`() {

        val startResponse = getFakeStartResponse()

        runBlocking {
            Mockito.`when`(apiService.start()).thenReturn(startResponse)

            inspectionsRepository.startInspection()

            Mockito.verify(apiService, Mockito.only()).start()
            verify(inspectionRealm, only()).insertInspection(startResponse.inspection)
        }
    }

    @Test(expected = Exception::class)
    fun `startInspection request not success`() {

        val startResponse = getFakeStartResponse()
        runBlocking {
            Mockito.`when`(apiService.start()).thenThrow(Exception())

            val actualResponse = inspectionsRepository.startInspection()

            assertNotSame(startResponse, actualResponse)
            Assert.assertEquals(Exception(), apiService.start())
            Mockito.verify(apiService, Mockito.only()).start()
            verify(inspectionRealm, never()).insertInspection(startResponse.inspection)
        }
    }

    @Test
    fun `getSavedInspections success`() {

        val inspectionItems = getFakeListOfInspectionItems()

        runBlocking {
            Mockito.`when`(inspectionRealm.getInspectionItems()).thenReturn(inspectionItems)

            val actualResult = inspectionsRepository.getSavedInspections()

            Assert.assertEquals(inspectionItems, actualResult)
            Mockito.verify(inspectionRealm, Mockito.only()).getInspectionItems()
        }
    }

    @Test
    fun `getQuestionsById success`() {

        val inspectionId = 1
        val questionItems = getFakeQuestionItems()

        runBlocking {
            Mockito.`when`(inspectionRealm.getQuestions(inspectionId)).thenReturn(questionItems)

            val actualResult = inspectionsRepository.getQuestionsById(inspectionId)

            Assert.assertEquals(questionItems, actualResult)
            Mockito.verify(inspectionRealm, Mockito.only()).getQuestions(inspectionId)
        }
    }

    @Test
    fun `updateQuestionAnswer success`() {

        val inspectionId = 1
        val answerId = 1
        val isInspectionUpdated = true

        runBlocking {
            Mockito.`when`(inspectionRealm.updateInspection(inspectionId, answerId))
                .thenReturn(isInspectionUpdated)

            val actualResult = inspectionsRepository.updateQuestionAnswer(inspectionId, answerId)

            Assert.assertEquals(isInspectionUpdated, actualResult)
            Mockito.verify(inspectionRealm, Mockito.only()).updateInspection(inspectionId, answerId)
        }
    }

    @Test(expected = Exception::class)
    fun `updateQuestionAnswer not success`() {

        val inspectionId = 1
        val answerId = 1

        runBlocking {
            Mockito.`when`(inspectionRealm.updateInspection(inspectionId, answerId))
                .thenThrow(Exception())

            inspectionsRepository.updateQuestionAnswer(inspectionId, answerId)

            Mockito.verify(inspectionRealm, Mockito.never())
                .updateInspection(inspectionId, answerId)
        }
    }

    @Test
    fun `sendInspection request success`() {

        val inspectionId = 1
        val inspection = getFakeInspection()
        val sendRequest = getFakeSendRequest(inspection)

        runBlocking {
            Mockito.`when`(inspectionRealm.getInspection(inspectionId)).thenReturn(inspection)

            inspectionsRepository.sendInspection(inspectionId)

            Mockito.verify(apiService, Mockito.only()).send(sendRequest)
        }
    }
}