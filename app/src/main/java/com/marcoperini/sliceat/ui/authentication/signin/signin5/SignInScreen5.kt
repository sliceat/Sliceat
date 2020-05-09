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
import com.marcoperini.sliceat.R
import com.marcoperini.sliceat.database.UsersTable
import com.marcoperini.sliceat.ui.Navigator
import com.marcoperini.sliceat.utils.Constants
import com.marcoperini.sliceat.utils.exhaustive
import com.marcoperini.sliceat.utils.sharedpreferences.Key.Companion.SAVE_DATA
import com.marcoperini.sliceat.utils.sharedpreferences.Key.Companion.SAVE_DATA_REGISTRATION
import com.marcoperini.sliceat.utils.sharedpreferences.Key.Companion.SAVE_E_MAIL
import com.marcoperini.sliceat.utils.sharedpreferences.Key.Companion.SAVE_FIRST_NAME
import com.marcoperini.sliceat.utils.sharedpreferences.Key.Companion.SAVE_LAST_NAME
import com.marcoperini.sliceat.utils.sharedpreferences.Key.Companion.SAVE_PASSWORD
import com.marcoperini.sliceat.utils.sharedpreferences.Key.Companion.SAVE_URI_PHOTO
import com.marcoperini.sliceat.utils.sharedpreferences.KeyValueStorage
import com.marcoperini.sliceat.utils.sharedpreferences.ServiceInterface
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.android.ext.android.inject
import java.text.SimpleDateFormat
import java.util.*

class SignInScreen5 : AppCompatActivity() {

    private val navigator: Navigator by inject()
    private val signIn5ViewModel: SignIn5ViewModel by inject()
    private val prefs: KeyValueStorage by inject()
    private val serviceInterface: ServiceInterface by inject()

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

            //send data to PHPserver
            serviceInterface.post()

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

    //use lambda fun. Pass this in constructor of the other function higher order in other class
    //instead use the interface like DialogPhoto class.

    private var launchCamera : () -> Unit = {
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

    private fun startCamera() {
        StartCamera().askCameraPermission(this@SignInScreen5) { launchCamera() }//here pass the lambda var(fun) in other fun
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
}
