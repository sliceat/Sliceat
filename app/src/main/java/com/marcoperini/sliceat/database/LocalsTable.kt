package com.marcoperini.sliceat.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.marcoperini.sliceat.utils.Constants

@Entity(tableName = Constants.LOCALS_TABLE_NAME, indices = [Index(value = [Constants.ID_LOCALE], unique = true)])
data class LocalsTable(
    @ColumnInfo(name = Constants.ADESIVO) var adesivo: String?

) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}
