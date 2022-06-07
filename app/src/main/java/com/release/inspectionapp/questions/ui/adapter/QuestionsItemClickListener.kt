package com.release.inspectionapp.questions.ui.adapter

interface QuestionsItemClickListener {
    fun onQuestionSelected(
        questionId: Int,
        answerId: Int
    )
}