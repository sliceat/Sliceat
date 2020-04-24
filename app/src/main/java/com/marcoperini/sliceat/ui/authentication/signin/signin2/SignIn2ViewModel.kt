package com.marcoperini.sliceat.ui.authentication.signin.signin1

import androidx.lifecycle.viewModelScope
import com.marcoperini.sliceat.database.UsersRepository
import com.marcoperini.sliceat.database.UsersTable
import com.marcoperini.sliceat.utils.BaseViewModel
import com.marcoperini.sliceat.utils.exhaustive
import kotlinx.coroutines.launch

sealed class SignIn2Event {
    data class Name(val user: UsersTable) : SignIn2Event()

}

sealed class SignIn2State {
    object CheckUserField : SignIn2State()
    object SaveUser : SignIn2State()
}

class SignIn2ViewModel(private val repository: UsersRepository) : BaseViewModel<SignIn2State, SignIn2Event>() {

    override fun send(event: SignIn2Event) {
        when (event) {
            is SignIn2Event.Name -> loadName(event.user)
        }.exhaustive
    }

    private fun loadName(user: UsersTable) {
        viewModelScope.launch {
            repository.insert(user)
        }
        post(SignIn2State.CheckUserField)
    }
}
