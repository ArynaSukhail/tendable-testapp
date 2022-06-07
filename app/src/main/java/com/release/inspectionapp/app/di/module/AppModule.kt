package com.release.inspectionapp.app.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.release.core_di.factory.ViewModelFactory
import com.release.core_di.factory.ViewModelKey
import com.release.inspectionapp.app.ui.AppActivityViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class AppModule {

    @Binds
    abstract fun bindViewModelFactory(
        factoryApp: ViewModelFactory
    ): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(AppActivityViewModel::class)
    abstract fun bindAppActivityViewModel(
        viewModel: AppActivityViewModel
    ): ViewModel
}
