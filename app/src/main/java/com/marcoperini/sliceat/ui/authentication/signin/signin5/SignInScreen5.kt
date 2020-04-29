package com.marcoperini.sliceat.ui.authentication.signin.signin5

import android.app.Activity
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.lifecycle.lifecycleScope
import com.android.volley.AuthFailureError
import com.android.volley.Response
import com.android.volley.VolleyLog
import com.android.volley.toolbox.JsonObjectRequest
import com.marcoperini.sliceat.R
import com.marcoperini.sliceat.database.UsersTable
import com.marcoperini.sliceat.ui.Navigator
import com.marcoperini.sliceat.ui.authentication.signin.signin5.StartCamera.StartCameraReceiver
import com.marcoperini.sliceat.utils.Constants
import com.marcoperini.sliceat.utils.Constants.Companion.CODICE_RECUPERO
import com.marcoperini.sliceat.utils.Constants.Companion.COGNOME
import com.marcoperini.sliceat.utils.Constants.Companion.DATA_DI_NASCITA
import com.marcoperini.sliceat.utils.Constants.Companion.DATA_REGISTRAZIONE
import com.marcoperini.sliceat.utils.Constants.Companion.E_MAIL
import com.marcoperini.sliceat.utils.Constants.Companion.NOME
import com.marcoperini.sliceat.utils.Constants.Companion.PASSWORD
import com.marcoperini.sliceat.utils.Constants.Companion.TIPO_REGISTRAZIONE
import com.marcoperini.sliceat.utils.Constants.Companion.URL
import com.marcoperini.sliceat.utils.VolleyRequest
import com.marcoperini.sliceat.utils.exhaustive
import com.marcoperini.sliceat.utils.sharedpreferences.Key.Companion.SAVE_DATA
import com.marcoperini.sliceat.utils.sharedpreferences.Key.Companion.SAVE_DATA_REGISTRATION
import com.marcoperini.sliceat.utils.sharedpreferences.Key.Companion.SAVE_E_MAIL
import com.marcoperini.sliceat.utils.sharedpreferences.Key.Companion.SAVE_FIRST_NAME
import com.marcoperini.sliceat.utils.sharedpreferences.Key.Companion.SAVE_LAST_NAME
import com.marcoperini.sliceat.utils.sharedpreferences.Key.Companion.SAVE_PASSWORD
import com.marcoperini.sliceat.utils.sharedpreferences.Key.Companion.SAVE_URI_PHOTO
import com.marcoperini.sliceat.utils.sharedpreferences.KeyValueStorage
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.json.JSONObject
import org.koin.android.ext.android.inject
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.*

class SignInScreen5 : AppCompatActivity() {

    private val navigator: Navigator by inject()
    private val signIn5ViewModel: SignIn5ViewModel by inject()
    private val prefs: KeyValueStorage by inject()
    private val volleyRequest: VolleyRequest by inject()

    private var fileUri: Uri? = null
    private lateinit var backButton: Button
    private lateinit var takeAPhoto: Button
    private lateinit var photo: ImageView
    private lateinit var cardPhoto: CardView
    private lateinit var access: Button
    private lateinit var simpleDateFormat: SimpleDateFormat
    private lateinit var currentDateAndTime: String

    companion object {
        fun getIntent(startingActivityContext: Context) = Intent(startingActivityContext, SignInScreen5::class.java)
            .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.sign_in_screen5)

        prefs.putString(SAVE_URI_PHOTO, " ")
        setupView()
        setOnClickListener()
        observer()
    }

    private fun setupView() {
        backButton = findViewById(R.id.backButton)
        takeAPhoto = findViewById(R.id.take_a_photo)
        photo = findViewById(R.id.photo)
        cardPhoto = findViewById(R.id.card_photo)
        access = findViewById(R.id.access)
    }

    private fun setOnClickListener() {
        backButton.setOnClickListener {
            navigator.goToSignInScreen4()
        }
        takeAPhoto.setOnClickListener {
            alertForPhoto()
        }
        access.setOnClickListener {
            simpleDateFormat = SimpleDateFormat("dd MM yyyy", Locale.ITALY)
            currentDateAndTime = simpleDateFormat.format(Date())
            prefs.putString(SAVE_DATA_REGISTRATION, currentDateAndTime)

            val params = JSONObject()
            params.put(NOME, prefs.getString(SAVE_FIRST_NAME, ""))
            params.put(COGNOME, prefs.getString(SAVE_LAST_NAME, ""))
            params.put(E_MAIL, prefs.getString(SAVE_E_MAIL, ""))
            params.put(PASSWORD, prefs.getString(SAVE_PASSWORD, ""))
            params.put(DATA_DI_NASCITA, prefs.getString(SAVE_DATA, ""))
            params.put(TIPO_REGISTRAZIONE, "CL")
            params.put(CODICE_RECUPERO, "123456")
            params.put(DATA_REGISTRAZIONE, prefs.getString(SAVE_DATA_REGISTRATION, ""))
            sendDataVolley(params)  {
                // Parse the result
            }

            signIn5ViewModel.send(
                SignIn5Event.User(
                    UsersTable(
                        prefs.getString(SAVE_FIRST_NAME, ""),
                        prefs.getString(SAVE_LAST_NAME, ""),
                        prefs.getString(SAVE_E_MAIL, ""),
                        prefs.getString(SAVE_PASSWORD, ""),
                        prefs.getString(SAVE_DATA, ""),
                        "CL",
                        "123456",
                        prefs.getString(SAVE_DATA_REGISTRATION, "")
                    )
                )
            )
        }
    }

    @ExperimentalCoroutinesApi
    private fun observer() {
        signIn5ViewModel.observe(lifecycleScope) { state ->
            when (state) {
                SignIn5State.SaveUser -> navigator.goToMainScreen()
            }.exhaustive
        }
    }

    //pick a photo from gallery
    private fun pickPhotoFromGallery() {
        val pickImageIntent = Intent(
            Intent.ACTION_PICK,
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        )

        startActivityForResult(pickImageIntent, Constants.PICK_PHOTO_REQUEST)
    }

    private fun startCamera() {
        StartCamera().askCameraPermission(this@SignInScreen5, object : StartCameraReceiver {
            override fun launchCamera() {
                val values = ContentValues(1)
                values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpg")
                fileUri = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
                val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                if (intent.resolveActivity(packageManager) != null) {
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri)
                    intent.addFlags(
                        Intent.FLAG_GRANT_READ_URI_PERMISSION
                                or Intent.FLAG_GRANT_WRITE_URI_PERMISSION
                    )
                    startActivityForResult(intent, Constants.TAKE_PHOTO_REQUEST)
                }
            }
        })
    }

    override fun onActivityResult(
        requestCode: Int, resultCode: Int,
        data: Intent?
    ) {
        if (resultCode == Activity.RESULT_OK
            && requestCode == Constants.TAKE_PHOTO_REQUEST
        ) {
            //photo from camera and display the photo on the  photo
            savePhoto()
        } else if (resultCode == Activity.RESULT_OK
            && requestCode == Constants.PICK_PHOTO_REQUEST
        ) {
            //photo from gallery
            fileUri = data?.data
            savePhoto()
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    private fun alertForPhoto() {
        DialogPhoto().start(this@SignInScreen5, object : DialogPhoto.ResultReceiver {
            override fun takePhoto() {
                startCamera()
            }

            override fun choosePhoto() {
                pickPhotoFromGallery()
            }
        })
    }

    private fun savePhoto() {
        cardPhoto.visibility = View.VISIBLE
        takeAPhoto.visibility = View.GONE
        prefs.putString(SAVE_URI_PHOTO, fileUri.toString())
        photo.setImageURI(fileUri)
    }

    private fun sendDataVolley(params: JSONObject, completionHandler: (response: JSONObject?) -> Unit) {
        val jsonObjReq = object : JsonObjectRequest(Method.POST, URL, params,
            Response.Listener<JSONObject> { response ->
                Timber.i("TAG %s", "/post request OK! Response: $response")
                completionHandler(response)
            },
            Response.ErrorListener { error ->
                VolleyLog.e("TAG", "/post request fail! Error: ${error.message}")
                completionHandler(null)
            }) {
            @Throws(AuthFailureError::class)
            override fun getHeaders(): Map<String, String> {
                val headers = HashMap<String, String>()
                headers.put("Content-Type", "application/json")
                return headers
            }
        }
        volleyRequest.addToRequestQueue(jsonObjReq)
    }
}
