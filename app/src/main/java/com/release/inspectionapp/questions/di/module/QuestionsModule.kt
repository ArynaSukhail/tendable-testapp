package com.release.inspectionapp.questions.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.release.core_di.factory.ViewModelFactory
import com.release.core_di.factory.ViewModelKey
import com.release.inspectionapp.questions.ui.QuestionsViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class QuestionsModule {

    @Binds
    abstract fun bindViewModelFactory(
        factoryApp: ViewModelFactory
    ): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(QuestionsViewModel::class)
    abstract fun bindQuestionsViewModel(
        viewModel: QuestionsViewModel
    ): ViewModel
}