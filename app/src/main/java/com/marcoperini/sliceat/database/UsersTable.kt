package com.marcoperini.sliceat.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.marcoperini.sliceat.utils.Constants.Companion.DATA_DI_NASCITA
import com.marcoperini.sliceat.utils.Constants.Companion.E_MAIL
import com.marcoperini.sliceat.utils.Constants.Companion.NOME
import com.marcoperini.sliceat.utils.Constants.Companion.COGNOME
import com.marcoperini.sliceat.utils.Constants.Companion.PASSWORD
import com.marcoperini.sliceat.utils.Constants.Companion.PERCORSO_FOTO
import com.marcoperini.sliceat.utils.Constants.Companion.REGISTRAZIONE
import com.marcoperini.sliceat.utils.Constants.Companion.TABLE_NAME

/*(nome, cognome, email, password, datanascita,registrazione,percorsofoto,libero1,uscita,privacy )
VALUES ('\(nomeVar)', '\(cognomeVar)', '\(emailVar)', '\(passwordVar)', '\(dataNascitaVar)',
'\("SI")', '\(percorsoFotoVar)', '\("CL")', '\("NO")', '\("SI")')"
*/
@Entity(tableName = TABLE_NAME)
data class UsersTable(
    @ColumnInfo(name = NOME) var firstName: String,
    @ColumnInfo(name = COGNOME) var lastName: String,
    @ColumnInfo(name = E_MAIL) var email: String,
    @ColumnInfo(name = PASSWORD) val password: String,
    @ColumnInfo(name = DATA_DI_NASCITA) val dataOfBirth: String,
    @ColumnInfo(name = REGISTRAZIONE) val typeAuthentication: String,
    @ColumnInfo(name = PERCORSO_FOTO) val urlFoto: String
) {
    @PrimaryKey(autoGenerate = true) var id: Int = 0
}
