package com.marcoperini.sliceat.ui.launch

import android.content.SharedPreferences
import com.marcoperini.sliceat.utils.BaseViewModel
import com.marcoperini.sliceat.utils.exhaustive
import com.marcoperini.sliceat.utils.sharedpreferences.FIRST_START

sealed class LaunchEvent {
    object Init : LaunchEvent()
}

sealed class LaunchState {
    object NewUser : LaunchState()
    object OldUser : LaunchState()
}

class LaunchViewModel(private val prefs: SharedPreferences) : BaseViewModel<LaunchState, LaunchEvent>() {
    override fun send(event: LaunchEvent) {

        when (event) {
            LaunchEvent.Init -> init()
        }.exhaustive
    }

    private fun init() {
        val newUser = prefs.getBoolean(FIRST_START, true)
        if (newUser) {
            post(LaunchState.NewUser)
        } else {
            post(LaunchState.OldUser)
        }
    }
}
