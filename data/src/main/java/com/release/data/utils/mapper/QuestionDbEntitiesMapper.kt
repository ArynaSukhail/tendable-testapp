package com.release.data.utils.mapper

import com.release.data.database.entity.QuestionEntity
import com.release.domain.model.AnswerItem
import com.release.domain.model.QuestionItem
import javax.inject.Inject

class QuestionDbEntitiesMapper @Inject constructor() : DataEntitiesMapper<QuestionEntity, QuestionItem> {
    override fun mapDataToUi(dataEntity: QuestionEntity): QuestionItem =
        QuestionItem(
            id = dataEntity.id,
            category = dataEntity.category?.name ?: "",
            name = dataEntity.name,
            selectedAnswerId = dataEntity.selectedAnswerChoiceId,
            answers = dataEntity.answerChoices.map { answer ->
                AnswerItem(
                    id = answer.id,
                    name = answer.name
                )
            }
        )
}