package com.marcoperini.sliceat.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.marcoperini.sliceat.utils.Constants

@Entity(tableName = Constants.ALLERGIE_TABLE_NAME)
data class AllergieTable(
    @ColumnInfo(name = Constants.ALLERGIA_1) var alid: String,
    @ColumnInfo(name = Constants.ALLERGIA_2) var allergia: String,
    @ColumnInfo(name = Constants.ID_LOCALE) var idLocale: String
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}