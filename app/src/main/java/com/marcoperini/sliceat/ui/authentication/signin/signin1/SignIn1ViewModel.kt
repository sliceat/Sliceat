package com.marcoperini.sliceat.ui.authentication.signin.signin1

import androidx.lifecycle.viewModelScope
import com.marcoperini.sliceat.database.UsersRepository
import com.marcoperini.sliceat.database.UsersTable
import com.marcoperini.sliceat.utils.BaseViewModel
import com.marcoperini.sliceat.utils.exhaustive
import kotlinx.coroutines.launch

sealed class SignIn1Event {
    data class Name(val user: UsersTable) : SignIn1Event()

}

sealed class SignIn1State {
    object CheckUserField : SignIn1State()
    object SaveUser : SignIn1State()
}

class SignInViewModel(private val repository: UsersRepository) : BaseViewModel<SignIn1State, SignIn1Event>() {

    override fun send(event: SignIn1Event) {
        when (event) {
            is SignIn1Event.Name -> loadName(event.user)
        }.exhaustive
    }

    private fun loadName(user: UsersTable) {
        viewModelScope.launch {
            repository.insert(user)
        }
        post(SignIn1State.CheckUserField)
    }
}
