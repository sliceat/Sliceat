package com.marcoperini.sliceat.ui.launch

import com.marcoperini.sliceat.utils.BaseViewModel

sealed class LaunchEvent {

}

sealed class LaunchState {

}
class LaunchViewModel : BaseViewModel<LaunchState, LaunchEvent>() {
    override fun send(event: LaunchEvent) {
        TODO("Not yet implemented")
    }
}
