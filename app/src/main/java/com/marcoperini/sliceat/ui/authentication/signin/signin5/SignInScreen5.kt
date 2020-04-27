package com.marcoperini.sliceat.ui.authentication.signin.signin5

import android.Manifest
import android.app.Activity
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.cardview.widget.CardView
import androidx.lifecycle.lifecycleScope
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import com.marcoperini.sliceat.R
import com.marcoperini.sliceat.database.UsersTable
import com.marcoperini.sliceat.ui.Navigator
import com.marcoperini.sliceat.utils.Constants
import com.marcoperini.sliceat.utils.exhaustive
import com.marcoperini.sliceat.utils.sharedpreferences.Key.Companion.SAVE_E_MAIL
import com.marcoperini.sliceat.utils.sharedpreferences.Key.Companion.SAVE_FIRST_NAME
import com.marcoperini.sliceat.utils.sharedpreferences.Key.Companion.SAVE_LAST_NAME
import com.marcoperini.sliceat.utils.sharedpreferences.Key.Companion.SAVE_PASSWORD
import com.marcoperini.sliceat.utils.sharedpreferences.Key.Companion.SAVE_URI_PHOTO
import com.marcoperini.sliceat.utils.sharedpreferences.KeyValueStorage
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.android.ext.android.inject

class SignInScreen5 : AppCompatActivity() {

    private val navigator: Navigator by inject()
    private val signIn5ViewModel: SignIn5ViewModel by inject()
    private val prefs: KeyValueStorage by inject()

    private var fileUri: Uri? = null
    private lateinit var backButton: Button
    private lateinit var takeAPhoto: Button
    private lateinit var photo: ImageView
    private lateinit var cardPhoto: CardView
    private lateinit var access: Button

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
//            askCameraPermission()
//            navigator.goToSignInScreen4()
        }
        access.setOnClickListener {
            signIn5ViewModel.send(SignIn5Event.User(UsersTable(
                prefs.getString(SAVE_FIRST_NAME, ""),
                prefs.getString(SAVE_LAST_NAME, ""),
                prefs.getString(SAVE_E_MAIL, ""),
                prefs.getString(SAVE_PASSWORD, ""),
                "11/11/1111",
                "CL",
                prefs.getString(SAVE_URI_PHOTO, "")
            )))
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
        val pickImageIntent = Intent(Intent.ACTION_PICK,
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI)

        startActivityForResult(pickImageIntent, Constants.PICK_PHOTO_REQUEST)
    }

    //ask for permission to take photo
    private fun askCameraPermission(){
        Dexter.withActivity(this)
            .withPermissions(
                Manifest.permission.CAMERA,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ).withListener(object : MultiplePermissionsListener {
                override fun onPermissionsChecked(report: MultiplePermissionsReport) {/* ... */
                    if(report.areAllPermissionsGranted()){
                        //once permissions are granted, launch the camera
                        launchCamera()
                    }else{
                        Toast.makeText(this@SignInScreen5, "All permissions need to be granted to take photo", Toast.LENGTH_LONG).show()
                    }
                }

                override fun onPermissionRationaleShouldBeShown(
                    permissions: MutableList<com.karumi.dexter.listener.PermissionRequest>?,
                    token: PermissionToken?
                ) {
                    AlertDialog.Builder(this@SignInScreen5)
                        .setTitle("Permissions Error!")
                        .setMessage("Please allow permissions to take photo with camera")
                        .setNegativeButton(android.R.string.cancel) { dialog, _ ->
                            dialog.dismiss()
                            token?.cancelPermissionRequest()
                        }
                        .setPositiveButton(android.R.string.ok) { dialog, _ ->
                            dialog.dismiss()
                            token?.continuePermissionRequest()
                        }
                        .setOnDismissListener {
                            token?.cancelPermissionRequest() }
                        .show()
                }
            }).check()

    }

    private fun launchCamera() {
        val values = ContentValues(1)
        values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpg")
        fileUri = contentResolver
            .insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                values)
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if(intent.resolveActivity(packageManager) != null) {
            intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri)
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION
                    or Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
            startActivityForResult(intent, Constants.TAKE_PHOTO_REQUEST)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int,
                                  data: Intent?) {
        if (resultCode == Activity.RESULT_OK
            && requestCode == Constants.TAKE_PHOTO_REQUEST) {
            //photo from camera
            //display the photo on the  photo
            photo.setImageURI(fileUri)
            prefs.putString(SAVE_URI_PHOTO, fileUri.toString())
            cardPhoto.visibility = View.VISIBLE
            takeAPhoto.visibility = View.GONE
        }else if(resultCode == Activity.RESULT_OK
            && requestCode == Constants.PICK_PHOTO_REQUEST){
            //photo from gallery
            fileUri = data?.data
            prefs.putString(SAVE_URI_PHOTO, fileUri.toString())
            photo.setImageURI(fileUri)
            cardPhoto.visibility = View.VISIBLE
            takeAPhoto.visibility = View.GONE
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    private fun alertForPhoto() {
        DialogPhoto().start(this@SignInScreen5, object : DialogPhoto.ResultReceiver {
            override fun takePhoto() {
                askCameraPermission()
            }

            override fun choisePhoto() {
                pickPhotoFromGallery()
            }
        })
    }
}
