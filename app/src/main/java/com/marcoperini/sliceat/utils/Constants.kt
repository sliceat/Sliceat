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

        //Database Users
        const val USERS_TABLE_NAME: String = "UserTable"
        const val DATABASE_NAME: String = "db"
        const val NOME: String = "item1"
        const val COGNOME: String = "item2"
        const val E_MAIL: String = "item3"
        const val PASSWORD: String = "item4"
        const val DATA_DI_NASCITA: String = "item5"
        const val TIPO_REGISTRAZIONE: String = "item6"
        const val CODICE_RECUPERO: String = "item7"
        const val DATA_REGISTRAZIONE: String = "item8"

        //Database Locals
        const val LOCALS_TABLE_NAME: String = "LocalsTable"
        const val ADESIVO: String = "Adesivo"

        //Database Allergie
        const val ALLERGIE_TABLE_NAME: String = "AllergieTable"
        const val ALLERGIA_1: String = "Allergia 1"
        const val ALLERGIA_2: String = "Allergia 2"
        const val ID_LOCALE: String = "Id locale"


        //foto
        const val PERCORSO_FOTO: String = "PercorsoFoto"

        //password Rule
        const val PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[A-Z])(?=.*[a-z])(?=.*[@#$%^&+=!£])(?=\\S+$).{4,}$"

        //URL SERVER
        const val URL = "https://www.sliceat.it/WebService/"
        const val POST_USER_REQUEST = "inseriscoUtente.php"
    }
}
