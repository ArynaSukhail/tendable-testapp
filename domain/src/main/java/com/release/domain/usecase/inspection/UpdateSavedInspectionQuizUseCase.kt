package com.release.domain.usecase.inspection

import com.release.domain.repositories.InspectionsRepository
import com.release.domain.usecase.BaseUseCase
import javax.inject.Inject

interface UpdateSavedInspectionQuizUseCase {
    suspend fun execute(params: Params): Boolean

    data class Params(
        val questionId: Int,
        val answerId: Int
    )
}

class UpdateSavedInspectionQuizUseCaseImpl @Inject constructor(
    private val inspectionsRepository: InspectionsRepository
) : BaseUseCase<Boolean, UpdateSavedInspectionQuizUseCase.Params>(),
    UpdateSavedInspectionQuizUseCase {

    override suspend fun execute(params: UpdateSavedInspectionQuizUseCase.Params): Boolean {
        return inspectionsRepository.updateQuestionAnswer(
            params.questionId,
            params.answerId
        )
    }
}