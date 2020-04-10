package com.marcoperini.sliceat.ui.main

import com.marcoperini.sliceat.utils.BaseViewModel

sealed class MainEvent {

}

sealed class MainState {

}

class MainViewModel : BaseViewModel<MainState, MainEvent>() {
    override fun send(event: MainEvent) {
        TODO("Not yet implemented")
    }
}
