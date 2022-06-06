package com.release.core_di.module

import com.release.data.model.StartResponse
import com.release.data.repositories.AuthRepositoryImpl
import com.release.data.repositories.InspectionsRepositoryImpl
import com.release.data.utils.mapper.DataEntitiesMapper
import com.release.data.utils.mapper.InspectionNetworkEntitiesMapper
import com.release.domain.model.InspectionItem
import com.release.domain.repositories.AuthRepository
import com.release.domain.repositories.InspectionsRepository
import dagger.Binds
import dagger.Module

@Module
abstract class DataModule {

    @Binds
    abstract fun bindAuthRepository(
        authRepositoryImpl: AuthRepositoryImpl
    ): AuthRepository

    @Binds
    abstract fun bindInspectionsRepository(
        inspectionsRepositoryImpl: InspectionsRepositoryImpl
    ): InspectionsRepository

    @Binds
    abstract fun provideInspectionEntitiesMapper(
        inspectionNetworkEntitiesMapper: InspectionNetworkEntitiesMapper
    ): DataEntitiesMapper<StartResponse, InspectionItem>
}
