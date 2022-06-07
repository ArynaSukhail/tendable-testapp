package com.release.inspectionapp.app.di.component

import com.release.core_di.component.CoreComponent
import com.release.core_di.factory.FeatureScope
import com.release.inspectionapp.app.di.module.AppModule
import com.release.inspectionapp.app.ui.AppActivity
import dagger.Component

@Component(
    modules = [
        AppModule::class
    ], dependencies = [CoreComponent::class]
)
@FeatureScope
interface AppComponent {
    fun inject(activity: AppActivity)
}

