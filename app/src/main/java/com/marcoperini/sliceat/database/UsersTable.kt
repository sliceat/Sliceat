package com.marcoperini.sliceat.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/*id - firstName - lastName... */

@Entity(tableName = "users_table")
data class UsersTable(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    @ColumnInfo(name = "owner_id")
    val firstName: String,
    val lastName: String,
    val email: String,
    val password: String,
    val gender: Char
)
