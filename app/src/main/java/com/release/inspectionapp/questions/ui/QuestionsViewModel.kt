package com.release.inspectionapp.questions.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.release.domain.usecase.questions.GetQuestionsUseCase
import com.release.domain.usecase.inspection.UpdateSavedInspectionQuizUseCase
import com.release.domain.utils.AppException
import com.release.inspectionapp.R
import com.release.inspectionapp.questions.ui.mvi.Questions
import com.release.inspectionapp.questions.ui.mvi.QuestionsIntent
import com.release.inspectionapp.questions.ui.mvi.QuestionsState
import com.release.inspectionapp.utils.ResourceManager
import com.release.inspectionapp.utils.mvi.IModel
import com.release.inspectionapp.utils.presentation.BaseViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class QuestionsViewModel @Inject constructor(
    private val getQuestionsUseCase: GetQuestionsUseCase,
    private val updateSavedInspectionQuizUseCase: UpdateSavedInspectionQuizUseCase,
    private val resourceManager: ResourceManager
) : BaseViewModel(), IModel<QuestionsState, QuestionsIntent> {

    override val intents: Channel<QuestionsIntent> = Channel(Channel.UNLIMITED)

    private val _state = MutableLiveData(QuestionsState(Questions.Loading))
    override val state: LiveData<QuestionsState>
        get() = _state

    init {
        handlerIntent()
    }

    private fun handlerIntent() {
        viewModelScope.launch {
            intents.consumeAsFlow().collect { intent ->
                when (intent) {
                    is QuestionsIntent.GetQuestions -> {
                        onStartQuestionScreen(intent.inspectionId)
                    }
                    is QuestionsIntent.SaveSelectedQuestion -> {
                        onRadioButtonSelected(intent.questionId, intent.answerId)
                    }
                }
            }
        }
    }

    private fun onStartQuestionScreen(inspectionId: Int?) {
        viewModelScope.launch(handler) {
            try {
                if (inspectionId != null) {
                    val questionItems =
                        getQuestionsUseCase.execute(GetQuestionsUseCase.Params(inspectionId))
                    updateState { QuestionsState(Questions.GetQuestions(questionItems)) }
                } else {
                    updateState {
                        QuestionsState(
                            Questions.ErrorMessage(
                                resourceManager.getString(R.string.cant_show_questions)
                            )
                        )
                    }
                }
            } catch (e: AppException) {
                updateState { QuestionsState(Questions.ErrorMessage(e.message.toString())) }
            }
        }
    }

    private fun onRadioButtonSelected(questionId: Int, answerId: Int) {
        viewModelScope.launch(handler) {
            try {
                updateSavedInspectionQuizUseCase.execute(
                    UpdateSavedInspectionQuizUseCase.Params(
                        questionId,
                        answerId
                    )
                )
                updateState { QuestionsState(Questions.SelectedQuestionsSaved) }
            } catch (e: AppException) {
                updateState { QuestionsState(Questions.ErrorMessage(e.message.toString())) }
            }
        }
    }

    private suspend fun updateState(handler: suspend (intent: QuestionsState) -> QuestionsState) {
        val value = state.value
        if (value != null) {
            _state.postValue(handler(value))
        }
    }
}