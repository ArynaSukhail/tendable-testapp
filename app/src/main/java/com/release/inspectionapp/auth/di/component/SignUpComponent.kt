package com.release.inspectionapp.auth.di.component

import com.release.core_di.component.CoreComponent
import com.release.core_di.factory.FeatureScope
import com.release.inspectionapp.auth.di.module.SignUpModule
import com.release.inspectionapp.auth.ui.signup.SignUpFragment
import dagger.Component

@Component(
    modules = [
        SignUpModule::class
    ], dependencies = [CoreComponent::class]
)
@FeatureScope
interface SignUpComponent {
    fun inject(fragment: SignUpFragment)
}