package com.marcoperini.sliceat.ui.maps.ui

import androidx.lifecycle.viewModelScope
import com.marcoperini.sliceat.database.AllergieTable
import com.marcoperini.sliceat.database.AppRepository
import com.marcoperini.sliceat.ui.maps.Contract
import com.marcoperini.sliceat.ui.maps.network.response.AllergieResponse
import com.marcoperini.sliceat.ui.maps.network.response.LocalsResponse
import com.marcoperini.sliceat.utils.BaseViewModel
import com.marcoperini.sliceat.utils.exhaustive
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.disposables.Disposable
import kotlinx.coroutines.launch

sealed class MapsEvent {
    object LoadLocals : MapsEvent()
    object LoadAllergie : MapsEvent()
}

sealed class MapsState {
    object InProgress : MapsState()
    data class LoadedLocals(val news: List<LocalsResponse>) : MapsState()
    data class LoadedAllergie(val store: List<AllergieResponse>) : MapsState()
    data class Error(val error: Throwable) : MapsState()
}

//TODO -> Create newsContract class and newsbackend Class

class MapsViewModel(private val scheduler: Scheduler, private val contract: Contract, private val repository: AppRepository) :
    BaseViewModel<MapsState, MapsEvent>() {
    private var newsSubscription = Disposable.disposed()
    private var storeSubscription = Disposable.disposed()

    override fun send(event: MapsEvent) {
        when (event) {
            is MapsEvent.LoadLocals -> loadLocals()
            is MapsEvent.LoadAllergie -> loadAllergie()
        }.exhaustive
    }

    private fun loadLocals() {
        if (newsSubscription.isDisposed) {
            post(MapsState.InProgress)
            newsSubscription = contract.getLocalsData()
                .observeOn(scheduler)
                .subscribe(
                    { locals -> post(MapsState.LoadedLocals(locals)) },
                    { error -> post(MapsState.Error(error)) }
                )
            disposables.add(newsSubscription)
        }
    }

    private fun loadAllergie() {
        if (storeSubscription.isDisposed) {
            post(MapsState.InProgress)
            storeSubscription = contract.getAllergieData()
                .observeOn(scheduler)
                .subscribe(
                    { allergie -> loadAllergie(allergie) },
                    { error -> post(MapsState.Error(error)) }
                )
            disposables.add(storeSubscription)
        }
    }

    private fun loadAllergie(allergie: List<AllergieResponse>) {
        post(MapsState.LoadedAllergie(allergie))
        lateinit var allergieTable: AllergieTable
        viewModelScope.launch {
            allergie.forEach { allergia -> allergieTable = AllergieTable(allergia.alid, allergia.allergia, allergia.idLocale) }
                .also { repository.insertAllergie(allergieTable) }
        }
    }
}
