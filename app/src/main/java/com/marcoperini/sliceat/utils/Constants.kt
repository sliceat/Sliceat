package com.marcoperini.sliceat.utils

class Constants {

    companion object {

        //Camera
        const val PICK_PHOTO_REQUEST = 2
        const val TAKE_PHOTO_REQUEST = 1

        // Launch Screen
        const val DELAY_START_SCREEN = 1500

        // Maps
        const val TIME_UPDATE_LOCALIZATION = 10000L
        const val TIME_UPDATE_LOCALIZATION_FAST = 2000L
        const val ZOOM_CAMERA = 17f

        //Database
        const val TABLE_NAME : String = "UserTable"
        const val USER_DATABASE_NAME : String = "Users.db"
        const val NOME : String = "Nome"
        const val COGNOME : String = "Cognome"
        const val E_MAIL : String = "Email"
        const val PASSWORD : String = "Password"
        const val DATA_DI_NASCITA: String = "DataDiNascita"
        const val TIPO_REGISTRAZIONE: String = "TipoRegistrazione"
        const val CODICE_RECUPERO: String = "CodideRecupero"
        const val DATA_REGISTRAZIONE: String = "DataRegistrazione"

        //foto
        const val PERCORSO_FOTO : String = "PercorsoFoto"

        //password Rule
        const val PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[A-Z])(?=.*[a-z])(?=.*[@#$%^&+=!£])(?=\\S+$).{4,}$"

        //URL SERVER
        const val URL = "https://www.sliceat.it/WebService/inseriscoUtente.php"
    }
}
