package com.marcoperini.sliceat.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [UsersTable::class], version = 1, exportSchema = false)
abstract class UsersDatabase: RoomDatabase() {
    abstract fun userDao(): UsersTableDao
}
