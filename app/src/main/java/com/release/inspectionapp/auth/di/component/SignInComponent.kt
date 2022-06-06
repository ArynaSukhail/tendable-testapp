package com.release.inspectionapp.auth.di.component

import com.release.core_di.component.CoreComponent
import com.release.core_di.factory.FeatureScope
import com.release.inspectionapp.auth.di.module.SignInModule
import com.release.inspectionapp.auth.ui.signin.SignInFragment
import dagger.Component

@Component(
    modules = [
        SignInModule::class
    ], dependencies = [CoreComponent::class]
)
@FeatureScope
interface SignInComponent {
    fun inject(fragment: SignInFragment)
}
