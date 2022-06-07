package com.release.inspectionapp.app

import android.app.Application
import com.release.core_di.component.CoreComponent
import com.release.core_di.component.CoreComponentProvider
import com.release.core_di.component.DaggerCoreComponent
import com.release.core_di.module.AppModule
import io.realm.Realm

class App : Application(), CoreComponentProvider {

    private lateinit var coreComponent: CoreComponent

    override fun provideCoreComponent(): CoreComponent {
        if (!this::coreComponent.isInitialized) {
            coreComponent = DaggerCoreComponent
                .builder()
                .appModule(AppModule(this))
                .build()
        }
        return coreComponent
    }

    override fun onCreate() {
        super.onCreate()
        Realm.init(this)
    }
}
