package com.release.inspectionapp.questions.ui.adapter

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.RadioButton
import android.widget.RadioGroup
import com.release.domain.model.QuestionItem

class AnswersGroup @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : RadioGroup(context, attrs) {

    fun setData(questionItem: QuestionItem) {
        questionItem.answers.forEach {
            val answersButton = RadioButton(context)
            answersButton.id = View.generateViewId()
            answersButton.text = it.name
            if (questionItem.selectedAnswerId != null && it.id == questionItem.selectedAnswerId) {
                answersButton.isChecked = true
            }
            this.addView(answersButton)
        }
    }
}
