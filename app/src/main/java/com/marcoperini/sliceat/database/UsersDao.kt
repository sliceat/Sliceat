package com.marcoperini.sliceat.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.marcoperini.sliceat.utils.Constants.Companion.ALLERGIE_TABLE_NAME
import com.marcoperini.sliceat.utils.Constants.Companion.LOCALS_TABLE_NAME
import com.marcoperini.sliceat.utils.Constants.Companion.USERS_TABLE_NAME

@Dao
interface UsersDao {
    @Query("SELECT * FROM $USERS_TABLE_NAME")
    suspend fun findAllUsers(): List<UsersTable>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertUsers(value: UsersTable)

//    @Query("INSERT INTO $TABLE_NAME (column1, column2)")
//    suspend fun insertTwoElement(value: UsersTable)

    @Query("DELETE FROM $USERS_TABLE_NAME")
    suspend fun deleteAllUsers()
}
