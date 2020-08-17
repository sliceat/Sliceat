package com.marcoperini.sliceat.utils

class Constants private constructor(){

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
        const val TABLE_NAME: String = "UserTable"
        const val USER_DATABASE_NAME: String = "Users.db"
        const val NOME: String = "item1"
        const val COGNOME: String = "item2"
        const val E_MAIL: String = "item3"
        const val PASSWORD: String = "item4"
        const val DATA_DI_NASCITA: String = "item5"
        const val TIPO_REGISTRAZIONE: String = "item6"
        const val CODICE_RECUPERO: String = "item7"
        const val DATA_REGISTRAZIONE: String = "item8"

        //foto
        const val PERCORSO_FOTO: String = "PercorsoFoto"

        //password Rule
        const val PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[A-Z])(?=.*[a-z])(?=.*[@#$%^&+=!£])(?=\\S+$).{4,}$"

        //URL SERVER
        const val URL = "https://www.sliceat.it/WebService/"
        const val POST_USER_REQUEST = "inseriscoUtente.php"
    }
}
