package com.release.domain

import com.release.domain.model.AnswerItem
import com.release.domain.model.InspectionItem
import com.release.domain.model.QuestionItem

fun getQuestionsItems(): List<QuestionItem> {
    return listOf(
        QuestionItem(
            1,
            "name",
            listOf(AnswerItem(1, "name")),
            "category",
            2
        )
    )
}

fun getInspectionItems(): List<InspectionItem> {
    return listOf(
        InspectionItem(
            1,
            "type",
            "area",
            "access"
        )
    )
}
