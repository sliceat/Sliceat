package com.marcoperini.sliceat.utils

import com.android.volley.AuthFailureError
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.marcoperini.sliceat.utils.sharedpreferences.Key
import com.marcoperini.sliceat.utils.sharedpreferences.Key.Companion.SAVE_PASSWORD
import com.marcoperini.sliceat.utils.sharedpreferences.KeyValueStorage
import org.json.JSONException
import org.json.JSONObject
import timber.log.Timber
import java.util.*

interface ServiceInterface {
    fun post()
}

class APIController constructor(private val volleyRequest: VolleyRequest, val prefs: KeyValueStorage) :
    ServiceInterface {

    override fun post() {
        val postRequest: StringRequest = object : StringRequest(
            Method.POST, Constants.URL + Constants.POST_USER_REQUEST, Response.Listener { s ->
                Timber.i("TAG %s", "Success $s")
                try {
                    val data = JSONObject(s)
                    val dir = data.getString("dir")
                    Timber.i("DIR %s", dir)
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            },
            Response.ErrorListener { error -> Timber.i("TAG %s", "Error response ") }) {
            override fun getParams(): Map<String, String> {
                val params: MutableMap<String, String> = HashMap()
                params[Constants.NOME] = prefs.getString(Key.SAVE_FIRST_NAME, "").toString()
                params[Constants.COGNOME] = prefs.getString(Key.SAVE_LAST_NAME, "").toString()
                params[Constants.E_MAIL] = prefs.getString(Key.SAVE_E_MAIL, "").toString()
                val hashPassword = HashClass.transformStringToHash(prefs.getString(SAVE_PASSWORD, "")!!)
                params[Constants.PASSWORD] = hashPassword.toString()
                params[Constants.DATA_DI_NASCITA] = prefs.getString(Key.SAVE_DATA, "").toString()
                params[Constants.TIPO_REGISTRAZIONE] = "CL"
                params[Constants.CODICE_RECUPERO] = "123456"
                params[Constants.DATA_REGISTRAZIONE] = prefs.getString(Key.SAVE_DATA_REGISTRATION, "").toString()
                return params
            }

            @Throws(AuthFailureError::class)
            override fun getHeaders(): Map<String, String> {
                val params: MutableMap<String, String> = HashMap()
                params["Content-Type"] = "application/x-www-form-urlencoded"
                return params
            }
        }
        volleyRequest.addToRequestQueue(postRequest)
    }
}



