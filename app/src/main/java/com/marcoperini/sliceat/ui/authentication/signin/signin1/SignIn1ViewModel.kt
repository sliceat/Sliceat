package com.marcoperini.sliceat.ui.authentication.signin.signin1

import com.marcoperini.sliceat.database.AppRepository
import com.marcoperini.sliceat.utils.BaseViewModel
import com.marcoperini.sliceat.utils.exhaustive
import com.marcoperini.sliceat.utils.sharedpreferences.Key.Companion.SAVE_FIRST_NAME
import com.marcoperini.sliceat.utils.sharedpreferences.Key.Companion.SAVE_LAST_NAME
import com.marcoperini.sliceat.utils.sharedpreferences.KeyValueStorage

sealed class SignIn1Event {
    data class SaveFirstAndLastName(val firstName: String, val lastName: String) : SignIn1Event()
    object LoginWithGoogle : SignIn1Event()
    object LoginWithFacebook : SignIn1Event()
//    data class SaveUser(val user: UsersTable) : SignIn1Event()

}

sealed class SignIn1State {
    object SavedFirstAndLastName : SignIn1State()
//    object SaveUser : SignIn1State()
}

class SignIn1ViewModel(private val repository: AppRepository, val prefs: KeyValueStorage) : BaseViewModel<SignIn1State, SignIn1Event>() {

    override fun send(event: SignIn1Event) {
        when (event) {
            is SignIn1Event.SaveFirstAndLastName -> saveFirstAndLAstName(event.firstName, event.lastName)
            is SignIn1Event.LoginWithGoogle -> TODO()
            is SignIn1Event.LoginWithFacebook -> TODO()
//            is SignIn1Event.SaveUser -> saveUser(event.user)
        }.exhaustive
    }

    private fun saveFirstAndLAstName(firstName: String, lastName: String) {
        prefs.putString(SAVE_FIRST_NAME, firstName)
        prefs.putString(SAVE_LAST_NAME, lastName)

//        viewModelScope.launch {
//            repository.insert(user)
//        }
        post(SignIn1State.SavedFirstAndLastName)
    }

//    private fun saveUser(user: UsersTable) {
//
//        viewModelScope.launch {
//            repository.insert(user)
//        }
//        post(SignIn1State.SaveUser)
//    }
}
