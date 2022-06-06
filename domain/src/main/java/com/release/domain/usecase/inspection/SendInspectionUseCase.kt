package com.release.domain.usecase.inspection

import com.release.domain.repositories.InspectionsRepository
import com.release.domain.usecase.BaseUseCase
import javax.inject.Inject

interface SendInspectionUseCase {
    suspend fun execute(params: Params)

    data class Params(val inspectionId: Int)
}

class SendInspectionUseCaseImpl @Inject constructor(
    private val inspectionsRepository: InspectionsRepository
) : BaseUseCase<Unit, SendInspectionUseCase.Params>(), SendInspectionUseCase {

    override suspend fun execute(params: SendInspectionUseCase.Params) {
        inspectionsRepository.sendInspection(params.inspectionId)
    }
}