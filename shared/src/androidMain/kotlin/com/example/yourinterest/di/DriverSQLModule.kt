package com.example.yourinterest.di



import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.example.yourinterest.db.InterestsDB
import org.koin.dsl.module

actual  val  driverSQLModule  = module {

    single<SqlDriver> {
        AndroidSqliteDriver(InterestsDB.Schema, get(), "InterestsDB.db")
    }

}