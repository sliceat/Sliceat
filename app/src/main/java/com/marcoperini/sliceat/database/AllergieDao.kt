package com.marcoperini.sliceat.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.marcoperini.sliceat.utils.Constants.Companion.ALLERGIE_TABLE_NAME
import com.marcoperini.sliceat.utils.Constants.Companion.LOCALS_TABLE_NAME
import com.marcoperini.sliceat.utils.Constants.Companion.USERS_TABLE_NAME

@Dao
interface AllergieDao {

    @Query("SELECT * FROM $ALLERGIE_TABLE_NAME")
    suspend fun findAllAllergie(): List<AllergieTable>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllergie(value: AllergieTable)

    @Query("DELETE FROM $ALLERGIE_TABLE_NAME")
    suspend fun deleteAllAllergie()
}
