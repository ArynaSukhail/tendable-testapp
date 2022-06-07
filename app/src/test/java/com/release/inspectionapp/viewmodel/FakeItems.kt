package com.release.inspectionapp.viewmodel

import com.release.domain.model.AnswerItem
import com.release.domain.model.InspectionItem
import com.release.domain.model.QuestionItem

fun getFakeListOfInspectionItems(): List<InspectionItem> {
    return listOf(
        InspectionItem(
            1,
            "type",
            "area"
        )
    )
}

fun getFakeQuestionItems(): List<QuestionItem> {
    return listOf(
        QuestionItem(
            1,
            "type",
            listOf(
                AnswerItem(
                    1,
                    "name"
                )
            ),
            "category",
            1
        )
    )
}