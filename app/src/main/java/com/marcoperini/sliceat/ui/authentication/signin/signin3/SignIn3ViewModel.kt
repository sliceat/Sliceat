package com.marcoperini.sliceat.ui.authentication.signin.signin3

import com.marcoperini.sliceat.utils.BaseViewModel
import com.marcoperini.sliceat.utils.exhaustive
import com.marcoperini.sliceat.utils.sharedpreferences.KeyValueStorage

sealed class SignIn3Event {
    data class SavePassword(val password: String) : SignIn3Event()

}

sealed class SignIn3State {
    object SavedPassword : SignIn3State()
}

class SignIn3ViewModel(private val prefs: KeyValueStorage) : BaseViewModel<SignIn3State, SignIn3Event>() {

    override fun send(event: SignIn3Event) {
        when (event) {
            is SignIn3Event.SavePassword -> savePassword(event.password)
        }.exhaustive
    }

    private fun savePassword(password: String) {
        prefs.putString("save_password", password)
        post(SignIn3State.SavedPassword)
    }
}
