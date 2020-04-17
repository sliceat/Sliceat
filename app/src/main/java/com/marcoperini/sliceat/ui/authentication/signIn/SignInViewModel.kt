package com.marcoperini.sliceat.ui.authentication.signIn

import androidx.lifecycle.viewModelScope
import com.marcoperini.sliceat.database.UsersRepository
import com.marcoperini.sliceat.database.UsersTable
import com.marcoperini.sliceat.utils.BaseViewModel
import com.marcoperini.sliceat.utils.exhaustive
import kotlinx.coroutines.launch

sealed class SignInEvent {
    data class Name(val user: UsersTable) : SignInEvent()

}

sealed class SignInState {
    object CheckUserField : SignInState()
    object SaveUser : SignInState()
}

class SignInViewModel(private val repository: UsersRepository) : BaseViewModel<SignInState, SignInEvent>() {

    override fun send(event: SignInEvent) {
        when (event) {
            is SignInEvent.Name -> loadName(event.user)
        }.exhaustive
    }

    private fun loadName(user: UsersTable) {
        viewModelScope.launch {
            repository.insert(user)
        }
        post(SignInState.CheckUserField)
    }
}
