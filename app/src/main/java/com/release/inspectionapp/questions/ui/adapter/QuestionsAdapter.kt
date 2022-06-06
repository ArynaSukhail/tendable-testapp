package com.release.inspectionapp.questions.ui.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.release.domain.model.QuestionItem

class QuestionsAdapter(
    private val questionsItemClickListener: QuestionsItemClickListener
) : RecyclerView.Adapter<QuestionViewHolder>() {

    private val items = mutableListOf<QuestionItem>()

    fun updateList(questionItem: List<QuestionItem>) {
        items.clear()
        items.addAll(questionItem)
        this.notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuestionViewHolder {
        return QuestionViewHolder(parent, questionsItemClickListener)
    }

    override fun onBindViewHolder(holder: QuestionViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int {
        return items.count()
    }
}