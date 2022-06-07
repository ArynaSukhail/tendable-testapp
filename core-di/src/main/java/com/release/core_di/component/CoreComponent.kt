package com.release.core_di.component

import android.content.Context
import com.release.core_di.module.*
import com.release.data.model.StartResponse
import com.release.data.utils.mapper.DataEntitiesMapper
import com.release.domain.model.InspectionItem
import com.release.domain.usecase.auth.IsUserSignedInUseCase
import com.release.domain.usecase.auth.SignInUseCase
import com.release.domain.usecase.auth.SignOutUseCase
import com.release.domain.usecase.auth.SignUpUseCase
import com.release.domain.usecase.inspection.*
import com.release.domain.usecase.questions.GetQuestionsUseCase
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AppModule::class,
        DatabaseModule::class,
        NetworkModule::class,
        DataModule::class,
        DomainModule::class
    ]
)
interface CoreComponent {
    val context: Context

    val isUserSignedInUseCase: IsUserSignedInUseCase
    val signInUseCase: SignInUseCase
    val signUpUseCase: SignUpUseCase
    val signOutUseCase: SignOutUseCase
    val getQuestionsUseCase: GetQuestionsUseCase
    val getSavedInspectionsUseCase: GetSavedInspectionUseCase
    val sendInspectionUseCase: SendInspectionUseCase
    val startInspectionUseCase: StartInspectionUseCase
    val updateSavedInspectionQuizUseCase: UpdateSavedInspectionQuizUseCase

    val inspectionMapper: DataEntitiesMapper<StartResponse, InspectionItem>
}