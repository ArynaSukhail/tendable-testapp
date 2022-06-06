package com.release.inspectionapp.inspection.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.release.core_di.factory.ViewModelFactory
import com.release.core_di.factory.ViewModelKey
import com.release.inspectionapp.inspection.ui.InspectionsViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class InspectionModule {

    @Binds
    abstract fun bindViewModelFactory(
        factoryApp: ViewModelFactory
    ): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(InspectionsViewModel::class)
    abstract fun bindInspectionsViewModel(
        viewModel: InspectionsViewModel
    ): ViewModel
}