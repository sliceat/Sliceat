package com.marcoperini.sliceat.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [UsersTable::class, LocalsTable::class, AllergieTable::class], version = 2, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UsersDao

    abstract fun localsDao(): LocalsDao

    abstract fun allergieDao(): AllergieDao
}
