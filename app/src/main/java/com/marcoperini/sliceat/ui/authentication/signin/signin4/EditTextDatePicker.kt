package com.marcoperini.sliceat.ui.authentication.signin.signin4

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Context
import android.view.View
import android.widget.DatePicker
import android.widget.EditText
import com.marcoperini.sliceat.R
import com.marcoperini.sliceat.utils.sharedpreferences.Key.Companion.SAVE_DATA
import com.marcoperini.sliceat.utils.sharedpreferences.KeyValueStorage
import java.text.SimpleDateFormat
import java.util.*

class EditTextDatePicker(context: Context, editTextViewID: Int, val prefs: KeyValueStorage) :
    View.OnClickListener, DatePickerDialog.OnDateSetListener {
    private var editText: EditText
    private val calendar: Calendar = Calendar.getInstance(TimeZone.getDefault())
    private val context: Context

    init {
        val act = context as Activity
        editText = act.findViewById<View>(editTextViewID) as EditText
        editText.setOnClickListener(this)
        this.context = context
    }

    override fun onClick(v: View?) {

        val pickerDialog = DatePickerDialog(
            context, R.style.style_date_picker_dialog,
            DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth -> // TODO Auto-generated method stub
                calendar.set(Calendar.YEAR, year)
                calendar.set(Calendar.MONTH, monthOfYear)
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                updateDisplay()
            }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)
        )
        pickerDialog.show()
    }

    private fun updateDisplay() {
        val myFormat = "dd/MM/yyyy" //In which you need put here
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        editText.setText(sdf.format(calendar.time))
        prefs.putString(SAVE_DATA, editText.toString())
    }

    /**
     * @param view the picker associated with the dialog
     * @param year the selected year
     * @param month the selected month (0-11 for compatibility with
     * [Calendar.MONTH])
     * @param dayOfMonth the selected day of the month (1-31, depending on
     * month)
     */
    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        TODO("Not yet implemented")
    }
}
