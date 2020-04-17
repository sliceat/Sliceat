package com.marcoperini.sliceat.ui.authentication.signIn

import androidx.lifecycle.viewModelScope
import com.marcoperini.sliceat.database.UsersRepository
import com.marcoperini.sliceat.database.UsersTable
import com.marcoperini.sliceat.utils.BaseViewModel
import com.marcoperini.sliceat.utils.exhaustive
import kotlinx.coroutines.launch

sealed class SignInEvent {
    data class Name(val name: String, val lastName: String, val eMail: String) : SignInEvent()

}

sealed class SignInState {
    data class SaveUser(val name: String) : SignInState()
}

class SignInViewModel(private val repository: UsersRepository) : BaseViewModel<SignInState, SignInEvent>() {

    override fun send(event: SignInEvent) {
        when (event) {
            is SignInEvent.Name -> loadName(event.name, event.lastName, event.eMail)
        }.exhaustive
    }

    private fun loadName(name: String, lastName: String, eMail: String) {
        viewModelScope.launch {
            repository.insert(UsersTable(name, lastName, eMail))
        }
    }
}
