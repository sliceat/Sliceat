package com.marcoperini.sliceat.ui.authentication.signin.signin2

import com.marcoperini.sliceat.utils.BaseViewModel
import com.marcoperini.sliceat.utils.exhaustive
import com.marcoperini.sliceat.utils.sharedpreferences.Key.Companion.SAVE_E_MAIL
import com.marcoperini.sliceat.utils.sharedpreferences.KeyValueStorage

sealed class SignIn2Event {
    data class SaveEmail(internal val email: String) : SignIn2Event()
}

sealed class SignIn2State {
    object SavedEmail : SignIn2State()
}

class SignIn2ViewModel(private val prefs: KeyValueStorage) : BaseViewModel<SignIn2State, SignIn2Event>() {

    override fun send(event: SignIn2Event) {
        when (event) {
            is SignIn2Event.SaveEmail -> saveEmail(event.email)
        }.exhaustive
    }

    private fun saveEmail(email: String) {
        prefs.putString(SAVE_E_MAIL, email)
        post(SignIn2State.SavedEmail)
    }
}
