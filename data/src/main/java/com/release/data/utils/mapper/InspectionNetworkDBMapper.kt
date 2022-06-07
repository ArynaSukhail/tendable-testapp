package com.release.data.utils.mapper

import com.release.data.database.entity.*
import com.release.data.model.*
import io.realm.RealmList
import javax.inject.Inject

class InspectionNetworkDBMapper @Inject constructor() : DataEntitiesMapper<Inspection, InspectionsEntity> {
    override fun mapDataToUi(dataEntity: Inspection): InspectionsEntity =
        InspectionsEntity(
            id = dataEntity.id,
            area = AreaEntity(
                id = dataEntity.area.id,
                name = dataEntity.area.name
            ),
            inspectionType = InspectionTypeEntity(
                id = dataEntity.inspectionType.id,
                access = dataEntity.inspectionType.access,
                name = dataEntity.inspectionType.name
            ),
            questions = getQuestions(dataEntity),
            surveyId = dataEntity.survey.id
        )

    private fun getQuestions(inspection: Inspection): RealmList<QuestionEntity> {
        val questionsRealm = RealmList<QuestionEntity>()
        inspection.survey.categories.map { category ->
            val categoryEntity = CategoryEntity(
                id = category.id,
                name = category.name,
            )
            val question = category.questions.map { question ->
                QuestionEntity(
                    id = question.id,
                    name = question.name,
                    selectedAnswerChoiceId = question.selectedAnswerChoiceId,
                    category = categoryEntity,
                    answerChoices = RealmList<AnswerEntity>().apply {
                        addAll(question.answerChoices.map { answer ->
                            AnswerEntity(
                                id = answer.id,
                                name = answer.name,
                                score = answer.score
                            )
                        })
                    }
                )
            }
            questionsRealm.addAll(question)
        }
        return questionsRealm
    }

    override fun mapUiToData(uiEntity: InspectionsEntity): Inspection {
        val categories = uiEntity.questions.map {
            Categories(
                it.category?.id ?: 0,
                it.category?.name ?: "",
                arrayListOf()
            )
        }.distinctBy { it.id }
        categories.forEach { category ->
            val questions =
                uiEntity.questions.filter { question -> question.category?.id ?: 0 == category.id }
                    .map { question ->
                        Questions(
                            id = question.id,
                            name = question.name,
                            selectedAnswerChoiceId = question.selectedAnswerChoiceId,
                            answerChoices = question.answerChoices.map { answer ->
                                AnswerChoices(
                                    answer.id,
                                    answer.name,
                                    answer.score
                                )
                            }
                        )
                    }
            category.questions.addAll(questions)
        }
        return Inspection(
            area = Area(
                id = uiEntity.area?.id ?: 0,
                name = uiEntity.area?.name ?: ""
            ),
            id = uiEntity.id,
            inspectionType = InspectionType(
                access = uiEntity.inspectionType?.access ?: "",
                id = uiEntity.inspectionType?.id ?: 0,
                name = uiEntity.inspectionType?.name ?: ""
            ),
            survey = Survey(
                id = uiEntity.surveyId,
                categories = categories
            )
        )
    }
}