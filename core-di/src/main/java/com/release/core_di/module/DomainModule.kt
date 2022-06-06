package com.release.core_di.module

import com.release.domain.repositories.AuthRepository
import com.release.domain.repositories.InspectionsRepository
import com.release.domain.usecase.auth.*
import com.release.domain.usecase.inspection.*
import com.release.domain.usecase.questions.GetQuestionsUseCase
import com.release.domain.usecase.questions.GetQuestionsUseCaseImpl
import dagger.Module
import dagger.Provides

@Module
open class DomainModule {

    @Provides
    fun provideIsUserSignedInUseCaseImpl(
        authRepository: AuthRepository
    ): IsUserSignedInUseCase {
        return IsUserSignedInUseCaseImpl(authRepository)
    }

    @Provides
    fun provideSignInUseCaseImpl(
        authRepository: AuthRepository
    ): SignInUseCase {
        return SignInUseCaseImpl(authRepository)
    }

    @Provides
    fun provideSignUpUseCaseImpl(
        authRepository: AuthRepository
    ): SignUpUseCase {
        return SignUpUseCaseImpl(authRepository)
    }

    @Provides
    fun provideStartInspectionUseCaseImpl(
        inspectionsRepository: InspectionsRepository
    ): StartInspectionUseCase {
        return StartInspectionUseCaseImpl(inspectionsRepository)
    }

    @Provides
    fun provideGetSavedInspectionUseCaseImpl(
        inspectionsRepository: InspectionsRepository
    ): GetSavedInspectionUseCase {
        return GetSavedInspectionUseCaseImpl(inspectionsRepository)
    }

    @Provides
    fun provideGetQuestionsUseCaseImpl(
        inspectionsRepository: InspectionsRepository
    ): GetQuestionsUseCase {
        return GetQuestionsUseCaseImpl(inspectionsRepository)
    }

    @Provides
    fun provideUpdateSavedInspectionQuizUseCaseImpl(
        inspectionsRepository: InspectionsRepository
    ): UpdateSavedInspectionQuizUseCase {
        return UpdateSavedInspectionQuizUseCaseImpl(inspectionsRepository)
    }

    @Provides
    fun provideSendInspectionUseCaseImpl(
        inspectionsRepository: InspectionsRepository
    ): SendInspectionUseCase {
        return SendInspectionUseCaseImpl(inspectionsRepository)
    }

    @Provides
    fun provideSignOutUseCaseImpl(
        authRepository: AuthRepository
    ): SignOutUseCase {
        return SignOutUseCaseImpl(authRepository)
    }
}