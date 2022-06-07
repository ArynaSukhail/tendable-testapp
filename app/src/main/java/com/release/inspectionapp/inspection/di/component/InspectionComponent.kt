package com.release.inspectionapp.inspection.di.component

import com.release.core_di.component.CoreComponent
import com.release.core_di.factory.FeatureScope
import com.release.inspectionapp.inspection.di.module.InspectionModule
import com.release.inspectionapp.inspection.ui.InspectionsFragment
import dagger.Component

@Component(
    modules = [
        InspectionModule::class
    ], dependencies = [CoreComponent::class]
)
@FeatureScope
interface InspectionComponent {
    fun inject(fragment: InspectionsFragment)
}