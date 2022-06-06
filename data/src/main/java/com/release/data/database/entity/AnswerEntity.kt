package com.release.data.database.entity

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class AnswerEntity(
    @PrimaryKey var id: Int = 0,
    var name: String = "",
    var score: Double = 0.0
) : RealmObject()