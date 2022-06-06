package com.release.inspectionapp.auth.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.release.core_di.factory.ViewModelFactory
import com.release.core_di.factory.ViewModelKey
import com.release.inspectionapp.auth.ui.signup.SignUpViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class SignUpModule {

    @Binds
    abstract fun bindViewModelFactory(
        factoryApp: ViewModelFactory
    ): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(SignUpViewModel::class)
    abstract fun bindSignUpViewModel(
        viewModel: SignUpViewModel
    ): ViewModel
}