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
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import com.marcoperini.sliceat.R
import com.marcoperini.sliceat.ui.Navigator
import com.marcoperini.sliceat.utils.Constants
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.android.ext.android.inject

class SignInScreen5 : AppCompatActivity() {

    private val navigator: Navigator by inject()
    private val signIn5ViewModel: SignIn5ViewModel by inject()
    private var fileUri: Uri? = null

    private lateinit var backButton: Button
    private lateinit var takeAPhoto: Button
    private lateinit var photo: ImageView

    companion object {
        fun getIntent(startingActivityContext: Context) = Intent(startingActivityContext, SignInScreen5::class.java)
            .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.sign_in_screen5)

        setupView()
        setOnClickListener()
        observer()
    }

    private fun setupView() {
        backButton = findViewById(R.id.backButton)
        takeAPhoto = findViewById(R.id.take_a_photo)
        photo = findViewById(R.id.photo)
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
    }

    @ExperimentalCoroutinesApi
    private fun observer() {
//        signIn3ViewModel.observe(lifecycleScope) { state ->
//            when (state) {
//
//            }.exhaustive
//        }
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

                /**
                 * Method called whenever Android asks the application to inform the user of the need for the
                 * requested permissions. The request process won't continue until the token is properly used
                 *
                 * @param permissions The permissions that has been requested. Collections of values found in
                 * [android.Manifest.permission]
                 * @param token Token used to continue or cancel the permission request process. The permission
                 * request process will remain blocked until one of the token methods is called
                 */
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
        }else if(resultCode == Activity.RESULT_OK
            && requestCode == Constants.PICK_PHOTO_REQUEST){
            //photo from gallery
            fileUri = data?.data
            photo.setImageURI(fileUri)
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
