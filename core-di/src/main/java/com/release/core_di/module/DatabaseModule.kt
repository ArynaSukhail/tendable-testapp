package com.release.core_di.module

import com.release.data.database.dbrepository.InspectionsRealm
import com.release.data.database.dbrepository.InspectionsRealmImpl
import com.release.data.database.InspectionsDatabase
import com.release.data.database.InspectionsDatabaseImpl
import com.release.data.database.entity.InspectionsEntity
import com.release.data.database.entity.QuestionEntity
import com.release.data.model.Inspection
import com.release.data.utils.mapper.DataEntitiesMapper
import com.release.data.utils.mapper.InspectionDbEntitiesMapper
import com.release.data.utils.mapper.InspectionNetworkDBMapper
import com.release.data.utils.mapper.QuestionDbEntitiesMapper
import com.release.domain.model.InspectionItem
import com.release.domain.model.QuestionItem
import dagger.Binds
import dagger.Module

@Module
abstract class DatabaseModule {

    @Binds
    abstract fun provideInspectionsDatabase(
        inspectionsDatabaseImpl: InspectionsDatabaseImpl
    ): InspectionsDatabase

    @Binds
    abstract fun provideInspectionsRealm(
        inspectionsRealm: InspectionsRealmImpl
    ): InspectionsRealm

    @Binds
    abstract fun provideInspectionMapperDB(
        inspectionsMapperNetwork: InspectionNetworkDBMapper
    ): DataEntitiesMapper<Inspection, InspectionsEntity>

    @Binds
    abstract fun provideInspectionDbUiMapper(
        inspectionsDbEntitiesMapper: InspectionDbEntitiesMapper
    ): DataEntitiesMapper<InspectionsEntity, InspectionItem>

    @Binds
    abstract fun provideQuestionDbUiMapper(
        questionDbEntitiesMapper: QuestionDbEntitiesMapper
    ): DataEntitiesMapper<QuestionEntity, QuestionItem>

}
