package com.marcoperini.sliceat.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.marcoperini.sliceat.utils.Constants

@Entity(tableName = Constants.ALLERGIE_TABLE_NAME, indices = [Index(value = [Constants.ID_LOCALE, Constants.ID_ALLERGIA], unique = true)])
data class AllergieTable(
    @ColumnInfo(name = Constants.ID_ALLERGIA) var alid: String,
    @ColumnInfo(name = Constants.NAME_ALLERGIA) var allergia: String,
    @ColumnInfo(name = Constants.ID_LOCALE) var idLocale: String
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}
