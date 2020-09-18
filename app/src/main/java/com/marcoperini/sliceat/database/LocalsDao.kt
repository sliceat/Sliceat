package com.marcoperini.sliceat.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.marcoperini.sliceat.utils.Constants.Companion.ALLERGIE_TABLE_NAME
import com.marcoperini.sliceat.utils.Constants.Companion.LOCALS_TABLE_NAME
import com.marcoperini.sliceat.utils.Constants.Companion.USERS_TABLE_NAME

@Dao
interface LocalsDao {
    @Query("SELECT * FROM $LOCALS_TABLE_NAME")
    suspend fun findAllLocals(): List<LocalsTable>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLocals(value: LocalsTable)

    @Query("DELETE FROM $LOCALS_TABLE_NAME")
    suspend fun deleteAllLocals()
}
