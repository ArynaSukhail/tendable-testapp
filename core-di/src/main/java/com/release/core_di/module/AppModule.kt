package com.release.core_di.module

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(
    private val application: Application
) {

    @Provides
    @Singleton
    fun provideApplication(): Application = this.application

    @Provides
    @Singleton
    fun provideContext(): Context {
        return this.application
    }
}