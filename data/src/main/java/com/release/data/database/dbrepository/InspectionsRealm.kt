package com.release.data.database.dbrepository

import com.release.data.model.Inspection
import com.release.domain.model.InspectionItem
import com.release.domain.model.QuestionItem

interface InspectionsRealm {
    suspend fun getInspectionItems(): List<InspectionItem>
    suspend fun getQuestions(inspectionId: Int): List<QuestionItem>
    suspend fun insertInspection(inspection: Inspection)
    suspend fun updateInspection(questionId: Int, answerId: Int): Boolean
    suspend fun getInspection(inspectionId: Int): Inspection?
    suspend fun deleteInspection(inspectionId: Int): Boolean
}
