package com.marcoperini.sliceat.ui.authentication.signin.signin5

import android.app.Activity
import android.app.AlertDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Gravity
import android.widget.Button
import com.marcoperini.sliceat.R

class DialogPhoto  {
    interface ResultReceiver {
        fun takePhoto()
        fun choosePhoto()
    }

    fun start(pActivity: Activity, callback: ResultReceiver) {
        val dialog = AlertDialog.Builder(pActivity)
        val dialogLayout = pActivity.layoutInflater.inflate(R.layout.choose_photo_dialog, null)
        val takePhoto = dialogLayout.findViewById<Button>(R.id.take_a_photo)
        val choisePhoto = dialogLayout.findViewById<Button>(R.id.choise_photo)

        val alertDialog = dialog.setView(dialogLayout).create()

        alertDialog.window?.setBackgroundDrawable(ColorDrawable(Color.WHITE))
        alertDialog.window?.setGravity(Gravity.BOTTOM)
        alertDialog.setCancelable(false)
        alertDialog.setCanceledOnTouchOutside(true)
        alertDialog.show()

        takePhoto.setOnClickListener {
            alertDialog.dismiss()
            callback.takePhoto()
        }

        choisePhoto.setOnClickListener {
            alertDialog.dismiss()
            callback.choosePhoto()
        }
    }
}
