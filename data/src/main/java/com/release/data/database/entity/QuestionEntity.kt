package com.release.data.database.entity

import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class QuestionEntity(
    @PrimaryKey var id: Int = 0,
    var name: String = "",
    var selectedAnswerChoiceId: Int? = null,
    var answerChoices: RealmList<AnswerEntity> = RealmList(),
    var category: CategoryEntity? = null,
) : RealmObject()