package com.release.domain.usecase.questions

import com.release.domain.model.QuestionItem
import com.release.domain.repositories.InspectionsRepository
import com.release.domain.usecase.BaseUseCase
import javax.inject.Inject

interface GetQuestionsUseCase {
    suspend fun execute(params: Params): List<QuestionItem>

    data class Params(val inspectionId: Int)
}

class GetQuestionsUseCaseImpl @Inject constructor(
    private val inspectionsRepository: InspectionsRepository
) : BaseUseCase<List<QuestionItem>, GetQuestionsUseCase.Params>(), GetQuestionsUseCase {

    override suspend fun execute(params: GetQuestionsUseCase.Params): List<QuestionItem> {
        return inspectionsRepository.getQuestionsById(params.inspectionId)
    }
}