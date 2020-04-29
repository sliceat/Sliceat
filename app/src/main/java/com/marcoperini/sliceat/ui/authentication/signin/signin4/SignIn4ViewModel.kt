package com.marcoperini.sliceat.ui.authentication.signin.signin4

import com.marcoperini.sliceat.utils.BaseViewModel
import com.marcoperini.sliceat.utils.exhaustive
import com.marcoperini.sliceat.utils.sharedpreferences.Key.Companion.SAVE_DATA
import com.marcoperini.sliceat.utils.sharedpreferences.KeyValueStorage

sealed class SignIn4Event {
    data class Data(val data: String) : SignIn4Event()

}

sealed class SignIn4State {
    object SavedData : SignIn4State()
}

class SignIn4ViewModel(private val prefs: KeyValueStorage) : BaseViewModel<SignIn4State, SignIn4Event>() {

    override fun send(event: SignIn4Event) {
        when (event) {
            is SignIn4Event.Data -> loadName(event.data)
        }.exhaustive
    }

    private fun loadName(data: String) {
        prefs.putString(SAVE_DATA, data)
        post(SignIn4State.SavedData)
    }
}
