package com.marcoperini.sliceat.ui.maps.ui

import com.marcoperini.sliceat.ui.maps.Contract
import com.marcoperini.sliceat.ui.maps.network.response.AllergieResponse
import com.marcoperini.sliceat.ui.maps.network.response.LocalsResponse
import com.marcoperini.sliceat.utils.BaseViewModel
import com.marcoperini.sliceat.utils.exhaustive
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.disposables.Disposable

sealed class MapsEvent {
    object LoadNews : MapsEvent()
    object LoadStore : MapsEvent()
}

sealed class MapsState {
    object InProgressNews : MapsState()
    object InProgressStore : MapsState()
    data class LoadedNews(val news: List<LocalsResponse>) : MapsState()
    data class LoadedStore(val store: List<AllergieResponse>) : MapsState()
    data class Error(val error: Throwable) : MapsState()
}

//TODO -> Create newsContract class and newsbackend Class

class MapsViewModel(
    private val scheduler: Scheduler,
    private val contract: Contract
) : BaseViewModel<MapsState, MapsEvent>() {
    private var newsSubscription = Disposable.disposed()
    private var storeSubscription = Disposable.disposed()

    override fun send(event: MapsEvent) {
        when(event) {
            is MapsEvent.LoadNews -> loadLocals()
            is MapsEvent.LoadStore -> loadAllergie()
        }.exhaustive
    }

    private fun loadLocals() {
        if (newsSubscription.isDisposed) {
            post(MapsState.InProgressNews)
            newsSubscription = contract.getLocalsData()
                .observeOn(scheduler)
                .subscribe(
                    { news -> post(MapsState.LoadedNews(news)) },
                    { error -> post(MapsState.Error(error)) }
                )
            disposables.add(newsSubscription)
        }
    }

    private fun loadAllergie() {
        if (storeSubscription.isDisposed) {
            post(MapsState.InProgressStore)
            storeSubscription = contract.getAllergieData()
                .observeOn(scheduler)
                .subscribe(
                    { store -> post(MapsState.LoadedStore(store)) },
                    { error -> post(MapsState.Error(error)) }
                )
            disposables.add(storeSubscription)
        }
    }
}
