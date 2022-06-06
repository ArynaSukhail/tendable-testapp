package com.release.inspectionapp.questions.di.component

import com.release.core_di.component.CoreComponent
import com.release.core_di.factory.FeatureScope
import com.release.inspectionapp.questions.di.module.QuestionsModule
import com.release.inspectionapp.questions.ui.QuestionsFragment
import dagger.Component

@Component(
    modules = [
        QuestionsModule::class
    ], dependencies = [CoreComponent::class]
)
@FeatureScope
interface QuestionsComponent {
    fun inject(fragment: QuestionsFragment)
}