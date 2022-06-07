package com.release.inspectionapp.questions.ui.mvi

import com.release.domain.model.QuestionItem
import com.release.inspectionapp.utils.mvi.IState

data class QuestionsState(
    val questions: Questions
) : IState

sealed class Questions : IState {
    object Loading : Questions()
    data class GetQuestions(val questionItems: List<QuestionItem>) : Questions()
    object SelectedQuestionsSaved : Questions()
    data class ErrorMessage(val msg: String) : Questions()
}