package com.release.data.database.entity

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class InspectionTypeEntity(
    @PrimaryKey var id: Int = 0,
    var access: String = "",
    var name: String = ""
) : RealmObject()