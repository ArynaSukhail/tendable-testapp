package com.release.inspectionapp.questions.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.release.core_di.component.CoreInjectHelper
import com.release.inspectionapp.databinding.FragmentQuestionsBinding
import com.release.inspectionapp.questions.di.component.DaggerQuestionsComponent
import com.release.inspectionapp.questions.ui.adapter.QuestionsAdapter
import com.release.inspectionapp.questions.ui.adapter.QuestionsItemClickListener
import com.release.inspectionapp.questions.ui.mvi.Questions
import com.release.inspectionapp.questions.ui.mvi.QuestionsIntent
import com.release.inspectionapp.questions.ui.mvi.QuestionsState
import com.release.inspectionapp.utils.mvi.IView
import com.release.inspectionapp.utils.presentation.BaseFragment
import kotlinx.coroutines.launch

class QuestionsFragment : BaseFragment(), QuestionsItemClickListener, IView<QuestionsState> {

    private var _binding: FragmentQuestionsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: QuestionsViewModel by viewModels { viewModelFactory }

    private val adapter = QuestionsAdapter(this)

    override fun onAttach(context: Context) {
        DaggerQuestionsComponent
            .builder()
            .coreComponent(CoreInjectHelper.provideCoreComponent(requireActivity().applicationContext))
            .build()
            .inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentQuestionsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvQuestions.adapter = adapter

        viewModel.state.observe(viewLifecycleOwner) {
            render(it)
        }

        val inspectionId: Int? = arguments?.getInt("bundle")
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.intents.send(QuestionsIntent.GetQuestions(inspectionId))
        }
    }

    override fun onQuestionSelected(questionId: Int, answerId: Int) {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.intents.send(QuestionsIntent.SaveSelectedQuestion(questionId, answerId))
        }
    }

    override fun render(state: QuestionsState) {
        when (state.questions) {
            is Questions.Loading -> {}
            is Questions.GetQuestions -> adapter.updateList(state.questions.questionItems)
            is Questions.SelectedQuestionsSaved -> {}
            is Questions.ErrorMessage -> showDialog(state.questions.msg)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}