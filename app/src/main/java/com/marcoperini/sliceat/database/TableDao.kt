package com.marcoperini.sliceat.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.marcoperini.sliceat.utils.Constants.Companion.TABLE_NAME

@Dao
interface TableDao {

    @Query("SELECT * FROM $TABLE_NAME")
    suspend fun findAll(): List<UsersTable>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(value: UsersTable)

//    @Query("INSERT INTO $TABLE_NAME (column1, column2)")
//    suspend fun insertTwoElement(value: UsersTable)

    @Query("DELETE FROM $TABLE_NAME")
    suspend fun deleteAll()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    @JvmSuppressWildcards
    abstract fun insertAll(kist: List<Any>)
}
