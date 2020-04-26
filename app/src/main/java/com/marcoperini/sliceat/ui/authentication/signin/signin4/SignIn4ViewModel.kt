package com.marcoperini.sliceat.ui.authentication.signin.signin4

import androidx.lifecycle.viewModelScope
import com.marcoperini.sliceat.database.UsersRepository
import com.marcoperini.sliceat.database.UsersTable
import com.marcoperini.sliceat.utils.BaseViewModel
import com.marcoperini.sliceat.utils.exhaustive
import com.marcoperini.sliceat.utils.sharedpreferences.KeyValueStorage
import kotlinx.coroutines.launch

sealed class SignIn4Event {
    data class Name(val user: UsersTable) : SignIn4Event()

}

sealed class SignIn4State {
    object CheckUserField : SignIn4State()
    object SaveUser : SignIn4State()
}

class SignIn4ViewModel(private val prefs: KeyValueStorage) : BaseViewModel<SignIn4State, SignIn4Event>() {

    override fun send(event: SignIn4Event) {
        when (event) {
            is SignIn4Event.Name -> loadName(event.user)
        }.exhaustive
    }

    private fun loadName(user: UsersTable) {
//        viewModelScope.launch {
//            repository.insert(user)
//        }
        post(SignIn4State.CheckUserField)
    }
}
