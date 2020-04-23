package com.marcoperini.sliceat.ui.authentication.signIn.signin3

import androidx.lifecycle.viewModelScope
import com.marcoperini.sliceat.database.UsersRepository
import com.marcoperini.sliceat.database.UsersTable
import com.marcoperini.sliceat.utils.BaseViewModel
import com.marcoperini.sliceat.utils.exhaustive
import kotlinx.coroutines.launch

sealed class SignIn3Event {
    data class Name(val user: UsersTable) : SignIn3Event()

}

sealed class SignIn3State {
    object CheckUserField : SignIn3State()
    object SaveUser : SignIn3State()
}

class SignIn3ViewModel(private val repository: UsersRepository) : BaseViewModel<SignIn3State, SignIn3Event>() {

    override fun send(event: SignIn3Event) {
        when (event) {
            is SignIn3Event.Name -> loadName(event.user)
        }.exhaustive
    }

    private fun loadName(user: UsersTable) {
        viewModelScope.launch {
            repository.insert(user)
        }
        post(SignIn3State.CheckUserField)
    }
}
