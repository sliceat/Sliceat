package com.marcoperini.sliceat.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.marcoperini.sliceat.utils.Constants.Companion.E_MAIL
import com.marcoperini.sliceat.utils.Constants.Companion.FIRST_NAME
import com.marcoperini.sliceat.utils.Constants.Companion.LAST_NAME
import com.marcoperini.sliceat.utils.Constants.Companion.TABLE_NAME

/*id - firstName - lastName... */

@Entity(tableName = TABLE_NAME)
data class UsersTable(
    @ColumnInfo(name = FIRST_NAME) val firstName: String,
    @ColumnInfo(name = LAST_NAME) val lastName: String,
    @ColumnInfo(name = E_MAIL) val email: String
//    @ColumnInfo(name = PASSWORD) val password: String,
//    @ColumnInfo(name = GENDER) val gender: Char
) {
    @PrimaryKey(autoGenerate = true) val id: Int = 0
}
