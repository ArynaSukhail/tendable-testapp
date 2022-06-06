package com.release.inspectionapp.questions.ui.adapter

import android.view.ViewGroup
import android.widget.RadioButton
import com.release.domain.model.QuestionItem
import com.release.inspectionapp.R
import com.release.inspectionapp.databinding.ItemQuestionBinding
import com.release.inspectionapp.utils.presentation.BaseViewHolder

class QuestionViewHolder(
    parent: ViewGroup,
    private val questionsItemClickListener: QuestionsItemClickListener
) : BaseViewHolder(parent, R.layout.item_question) {

    private val viewBinding = ItemQuestionBinding.bind(itemView)

    fun bind(questionItem: QuestionItem) {
        viewBinding.questionItemModel = questionItem

        AnswersGroup(itemView.context)
        viewBinding.answersGroup.setData(questionItem)

        viewBinding.answersGroup.setOnCheckedChangeListener { radioGroup, i ->
            val answerId = questionItem.answers.find {
                it.name == radioGroup.findViewById<RadioButton>(i).text
            }?.id
            if (answerId != null) {
                questionsItemClickListener.onQuestionSelected(questionItem.id, answerId)
            }
        }
    }
}