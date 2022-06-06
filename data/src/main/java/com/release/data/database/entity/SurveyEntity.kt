package com.release.data.database.entity

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class SurveyEntity(
    @PrimaryKey var id: Int = 0
) : RealmObject()