package com.marcoperini.sliceat.utils

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.launch
import timber.log.Timber

abstract class BaseViewModel<S, E> : ViewModel() {

    protected open val channel: Channel<S> = Channel()

    override fun onCleared() {
        super.onCleared()
        channel.close(Throwable("channel closed in $this"))
    }

    @ExperimentalCoroutinesApi
    fun observe(scope: CoroutineScope, observer: (S) -> Unit) {
        scope.launch {
            channel.consumeEach { it?.let(observer::invoke) }
        }
    }

    fun post(state: S) {
        try {
            channel.offer(state)
        } catch (e: Exception) {
            Timber.e(e, "error when trying to post state $state in $this")
        }
    }

    abstract fun send(event: E)
}

