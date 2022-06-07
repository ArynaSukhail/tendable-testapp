package com.release.domain.repositories

import com.release.domain.model.InspectionItem
import com.release.domain.model.QuestionItem

interface InspectionsRepository {

    suspend fun startInspection(): List<InspectionItem>

    suspend fun getSavedInspections(): List<InspectionItem>

    suspend fun getQuestionsById(inspectionId: Int): List<QuestionItem>

    suspend fun updateQuestionAnswer(
        questionId: Int,
        answerId: Int
    ): Boolean

    suspend fun sendInspection(inspectionId: Int)
}