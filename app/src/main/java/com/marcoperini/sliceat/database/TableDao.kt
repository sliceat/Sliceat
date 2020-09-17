package com.marcoperini.sliceat.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.marcoperini.sliceat.utils.Constants.Companion.ALLERGIE_TABLE_NAME
import com.marcoperini.sliceat.utils.Constants.Companion.LOCALS_TABLE_NAME
import com.marcoperini.sliceat.utils.Constants.Companion.USERS_TABLE_NAME

@Dao
interface TableDao {

    @Query("SELECT * FROM $USERS_TABLE_NAME")
    suspend fun findAllUsers(): List<UsersTable>

    @Query("SELECT * FROM $LOCALS_TABLE_NAME")
    suspend fun findAllLocals(): List<LocalsTable>

    @Query("SELECT * FROM $ALLERGIE_TABLE_NAME")
    suspend fun findAllAllergie(): List<LocalsTable>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertUsers(value: UsersTable)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertLocals(value: LocalsTable)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAllergie(value: AllergieTable)

//    @Query("INSERT INTO $TABLE_NAME (column1, column2)")
//    suspend fun insertTwoElement(value: UsersTable)

    @Query("DELETE FROM $USERS_TABLE_NAME")
    suspend fun deleteAllUsers()

    @Query("DELETE FROM $LOCALS_TABLE_NAME")
    suspend fun deleteAllLocals()

    @Query("DELETE FROM $ALLERGIE_TABLE_NAME")
    suspend fun deleteAllAllergie()
}
