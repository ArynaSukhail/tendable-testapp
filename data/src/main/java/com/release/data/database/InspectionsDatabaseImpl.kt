package com.release.data.database

import io.realm.Realm
import io.realm.RealmConfiguration
import javax.inject.Inject

class InspectionsDatabaseImpl @Inject constructor() : InspectionsDatabase {

    override val realm: Realm by lazy {
        Realm.getInstance(config)
    }

    private val config: RealmConfiguration = RealmConfiguration.Builder()
        .schemaVersion(realmVersion)
        .name(DB_NAME)
        .build()

    companion object {
        private const val DB_NAME = "inspection.db"
        private const val realmVersion = 1L
    }
}
