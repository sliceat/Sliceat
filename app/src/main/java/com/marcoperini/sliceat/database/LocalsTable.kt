package com.marcoperini.sliceat.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.marcoperini.sliceat.utils.Constants

@Entity(tableName = Constants.LOCALS_TABLE_NAME)
data class LocalsTable(
    @ColumnInfo(name = Constants.NOME) var firstName: String?

) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}