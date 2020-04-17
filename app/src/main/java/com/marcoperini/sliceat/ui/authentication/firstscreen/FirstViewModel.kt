package com.marcoperini.sliceat.ui.authentication.firstscreen

import com.marcoperini.sliceat.utils.BaseViewModel

sealed class AuthenticationEvent {
    object Init : AuthenticationEvent()
}

sealed class AuthenticationState {
    object NewUser : AuthenticationState()
    object OldUser : AuthenticationState()
}

class AuthenticationViewModel : BaseViewModel<AuthenticationState, AuthenticationEvent>() {
    override fun send(event: AuthenticationEvent) {
        TODO("Not yet implemented")
    }
}
