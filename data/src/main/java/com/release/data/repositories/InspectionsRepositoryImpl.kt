package com.release.data.repositories

import com.release.data.database.dbrepository.InspectionsRealm
import com.release.data.model.SendRequest
import com.release.data.model.StartResponse
import com.release.data.service.ApiService
import com.release.data.utils.NetworkCall
import com.release.data.utils.mapper.DataEntitiesMapper
import com.release.domain.model.InspectionItem
import com.release.domain.model.QuestionItem
import com.release.domain.repositories.InspectionsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class InspectionsRepositoryImpl @Inject constructor(
    private val inspectionsRealm: InspectionsRealm,
    private val apiService: ApiService,
    private val networkCall: NetworkCall,
    private val inspectionMapper: DataEntitiesMapper<StartResponse, InspectionItem>,
) : InspectionsRepository {

    override suspend fun startInspection(): List<InspectionItem> {
        return withContext(Dispatchers.IO) {
            val response = networkCall.apiCall { apiService.start() }
            inspectionsRealm.insertInspection(response.inspection)
            return@withContext listOf(inspectionMapper.mapDataToUi(response))
        }
    }

    override suspend fun getSavedInspections(): List<InspectionItem> {
        return withContext(Dispatchers.IO) {
            return@withContext inspectionsRealm.getInspectionItems()
        }
    }

    override suspend fun getQuestionsById(
        inspectionId: Int
    ): List<QuestionItem> {
        return withContext(Dispatchers.IO) {
            return@withContext inspectionsRealm.getQuestions(inspectionId)
        }
    }

    override suspend fun updateQuestionAnswer(
        questionId: Int,
        answerId: Int
    ): Boolean {
        return withContext(Dispatchers.IO) {
            return@withContext inspectionsRealm.updateInspection(questionId, answerId)
        }
    }

    override suspend fun sendInspection(inspectionId: Int) {
        withContext(Dispatchers.IO) {
            val inspectionDB = inspectionsRealm.getInspection(inspectionId)
            val body = SendRequest(inspectionDB!!)
            networkCall.apiCall { apiService.send(body) }
            inspectionsRealm.deleteInspection(inspectionId)
        }
    }
}