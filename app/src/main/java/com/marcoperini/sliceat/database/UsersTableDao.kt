package com.marcoperini.sliceat.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.marcoperini.sliceat.utils.Constants.Companion.TABLE_NAME

@Dao
interface UsersTableDao {

    @Query("SELECT * FROM $TABLE_NAME")
    suspend fun findAll(): List<UsersTable>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(value: UsersTable)

    @Query("DELETE FROM $TABLE_NAME")
    suspend fun deleteAll()
}
