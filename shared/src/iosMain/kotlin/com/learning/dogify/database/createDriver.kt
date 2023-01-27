package com.learning.dogify.database

import com.learning.dogify.db.DogifyDatabase
import com.squareup.sqldelight.db.SqlDriver
import org.koin.core.scope.Scope

internal actual fun Scope.createDriver(databaseName: String): SqlDriver =
    NativeSqliteDriver(DogifyDatabase.Schema, databaseName)