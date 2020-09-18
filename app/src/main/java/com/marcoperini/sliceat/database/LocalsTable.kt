package com.marcoperini.sliceat.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.marcoperini.sliceat.utils.Constants

@Entity(tableName = Constants.LOCALS_TABLE_NAME, indices = [Index(value = [Constants.ID_LOCALE], unique = true)])
data class LocalsTable(
    @ColumnInfo(name = Constants.ADESIVO) val adesivo: String?,
    @ColumnInfo(name = Constants.CAP)val cap: String,
    @ColumnInfo(name = Constants.CAT_ID)val catid: String,
    @ColumnInfo(name = Constants.CITTA)val citta: String,
    @ColumnInfo(name = Constants.CIVICO)val civico: String,
    @ColumnInfo(name = Constants.CONFIRMED) val confirmed: String,
    @ColumnInfo(name = Constants.FACEBOOK) val facebook: String,
    @ColumnInfo(name = Constants.INSTAGRAM)val instagram: String,
    @ColumnInfo(name = Constants.KEYHASH) val keyhash: String,
    @ColumnInfo(name = Constants.LAT) val lat: String,
    @PrimaryKey @ColumnInfo(name = Constants.ID_LOCALE)  val locid: String,
    @ColumnInfo(name = Constants.LON)  val lon: String,
    @ColumnInfo(name = Constants.MAIL)  val mail: String,
    @ColumnInfo(name = Constants.NAZIONE)  val nazione: String,
    @ColumnInfo(name = Constants.NOME_LOCALE)  val nome: String,
    @ColumnInfo(name = Constants.PERCORSO_FOTO_LOCALE)  val percorsoFoto: String,
    @ColumnInfo(name = Constants.PORTATE)  val portate: String,
    @ColumnInfo(name = Constants.PRENOTAZIONE)val prenotazione: String,
    @ColumnInfo(name = Constants.PREZZO)val prezzo: String,
    @ColumnInfo(name = Constants.PROVINCIA)val provincia: String,
    @ColumnInfo(name = Constants.TELEFONO)val telefono: String,
    @ColumnInfo(name = Constants.TWITTER) val twitter: String,
    @ColumnInfo(name = Constants.VIA_LOCALE) val via: String,
    @ColumnInfo(name = Constants.WEBSITE) val website: String
)
