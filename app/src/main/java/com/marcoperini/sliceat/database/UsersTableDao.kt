package com.marcoperini.sliceat.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UsersTableDao {

    @Query("SELECT * FROM users_table")
    suspend fun findAll(): List<UsersTable>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(value: UsersTable)

    @Query("DELETE FROM users_table")
    suspend fun deleteAll()
}
