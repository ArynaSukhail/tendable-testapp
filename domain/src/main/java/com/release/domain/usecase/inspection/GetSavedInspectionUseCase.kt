package com.release.domain.usecase.inspection

import com.release.domain.model.InspectionItem
import com.release.domain.repositories.InspectionsRepository
import com.release.domain.usecase.BaseUseCase
import com.release.domain.usecase.None
import javax.inject.Inject

interface GetSavedInspectionUseCase {
    suspend fun execute(params: None): List<InspectionItem>
}

class GetSavedInspectionUseCaseImpl @Inject constructor(
    private val inspectionsRepository: InspectionsRepository
) : BaseUseCase<List<InspectionItem>, None>(), GetSavedInspectionUseCase {

    override suspend fun execute(params: None): List<InspectionItem> {
        return inspectionsRepository.getSavedInspections()
    }
}