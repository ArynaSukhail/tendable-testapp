package com.release.data.database.entity

import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class InspectionsEntity(
    @PrimaryKey var id: Int = 0,
    var surveyId: Int = 0,
    var area: AreaEntity? = null,
    var inspectionType: InspectionTypeEntity? = null,
    var questions: RealmList<QuestionEntity> = RealmList()
) : RealmObject()