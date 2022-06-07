package com.release.inspectionapp.questions.ui.mvi

import com.release.inspectionapp.utils.mvi.IIntent

sealed class QuestionsIntent : IIntent {
    data class GetQuestions(val inspectionId: Int?) : QuestionsIntent()
    data class SaveSelectedQuestion(
        val questionId: Int,
        val answerId: Int
    ) : QuestionsIntent()
}