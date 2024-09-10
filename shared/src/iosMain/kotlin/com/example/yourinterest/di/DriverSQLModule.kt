package com.example.yourinterest.di

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.NativeSqliteDriver
import com.example.yourinterest.db.InterestsDB
import org.koin.dsl.module


actual  val  driverSQLModule  = module {
    single<SqlDriver> {
        NativeSqliteDriver(InterestsDB.Schema, "InterestsDB.db")
    }
}