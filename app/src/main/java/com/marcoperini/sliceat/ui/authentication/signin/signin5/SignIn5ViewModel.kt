package com.marcoperini.sliceat.ui.authentication.signin.signin5

import androidx.lifecycle.viewModelScope
import com.marcoperini.sliceat.database.UsersRepository
import com.marcoperini.sliceat.database.UsersTable
import com.marcoperini.sliceat.utils.BaseViewModel
import com.marcoperini.sliceat.utils.exhaustive
import kotlinx.coroutines.launch

sealed class SignIn5Event {
    data class User(val user: UsersTable) : SignIn5Event()
}

sealed class SignIn5State {
    object SaveUser : SignIn5State()
}

class SignIn5ViewModel(private val repository: UsersRepository) : BaseViewModel<SignIn5State, SignIn5Event>() {

    override fun send(event: SignIn5Event) {
        when (event) {
            is SignIn5Event.User -> loadName(event.user)
        }.exhaustive
    }

    private fun loadName(user: UsersTable) {
        viewModelScope.launch {
            repository.insert(user)
        }
        post(SignIn5State.SaveUser)
    }
}
