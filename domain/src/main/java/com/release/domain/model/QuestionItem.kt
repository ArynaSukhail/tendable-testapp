package com.release.domain.model

data class QuestionItem(
    val id: Int,
    val name: String,
    val answers: List<AnswerItem>,
    val category: String,
    var selectedAnswerId: Int?
)