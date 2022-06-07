package com.release.data.utils.mapper

import com.release.data.database.entity.InspectionsEntity
import com.release.domain.model.InspectionItem
import javax.inject.Inject

class InspectionDbEntitiesMapper @Inject constructor() : DataEntitiesMapper<InspectionsEntity, InspectionItem> {
    override fun mapDataToUi(dataEntity: InspectionsEntity): InspectionItem =
        InspectionItem(
            id = dataEntity.id,
            type = dataEntity.inspectionType?.name,
            area = dataEntity.area?.name,
            access = dataEntity.inspectionType?.access
        )
}