package com.release.data.model

data class StartResponse(
    val inspection: Inspection
)

data class Inspection(
    val area: Area,
    val id: Int,
    val inspectionType: InspectionType,
    val survey: Survey
)

data class Area(
    val id: Int,
    val name: String
)

data class InspectionType(
    val access: String,
    val id: Int,
    val name: String
)

data class Survey(
    val id: Int,
    val categories: List<Categories>
)

data class Categories(
    val id: Int,
    val name: String,
    val questions: ArrayList<Questions>
)

data class Questions(
    val id: Int,
    val name: String,
    val selectedAnswerChoiceId: Int?,
    val answerChoices: List<AnswerChoices>
)

data class AnswerChoices(
    val id: Int,
    val name: String,
    val score: Double
)