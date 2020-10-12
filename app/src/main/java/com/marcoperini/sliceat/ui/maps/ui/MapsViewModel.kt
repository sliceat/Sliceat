package com.marcoperini.sliceat.ui.maps.ui

import androidx.lifecycle.viewModelScope
import com.marcoperini.sliceat.database.AllergieTable
import com.marcoperini.sliceat.database.AppRepository
import com.marcoperini.sliceat.database.LocalsTable
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
    data class LoadedLocals(val restaurants: List<LocalsResponse>) : MapsState()
    data class LoadedAllergie(val allergies: List<AllergieResponse>) : MapsState()
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
            is MapsEvent.LoadAllergie -> loadAllergies()
        }.exhaustive
    }

    private fun loadLocals() {
        if (newsSubscription.isDisposed) {
            post(MapsState.InProgress)
            newsSubscription = contract.getLocalsData()
                .observeOn(scheduler)
                .subscribe(
                    { locals -> saveLocals(locals) },
                    { error -> post(MapsState.Error(error)) }
                )
            disposables.add(newsSubscription)
        }
    }

    private fun saveLocals(locals: List<LocalsResponse>) {
        post(MapsState.LoadedLocals(locals))
        lateinit var localsTable: LocalsTable
        locals.forEach { local ->
            viewModelScope.launch {
                localsTable = LocalsTable(
                    adesivo = local.adesivo,
                    cap = local.cap,
                    catid = local.catid,
                    citta = local.citta,
                    civico = local.civico,
                    confirmed = local.confirmed,
                    facebook = local.facebook,
                    instagram = local.instagram,
                    keyhash = local.keyhash,
                    lat = local.lat,
                    locid = local.locid,
                    lon = local.lon,
                    mail = local.mail,
                    nazione = local.nazione,
                    nome = local.nome,
                    percorsoFoto = local.percorsoFoto,
                    portate = local.portate,
                    prenotazione = local.prenotazione,
                    prezzo = local.prezzo,
                    provincia = local.provincia,
                    telefono = local.telefono,
                    twitter = local.twitter,
                    via = local.via,
                    website = local.website
                )
                repository.insertLocals(localsTable)
            }
        }
    }

    private fun loadAllergies() {
        if (storeSubscription.isDisposed) {
            post(MapsState.InProgress)
            storeSubscription = contract.getAllergieData()
                .observeOn(scheduler)
                .subscribe(
                    { allergie -> saveAllergies(allergie) },
                    { error -> post(MapsState.Error(error)) }
                )
            disposables.add(storeSubscription)
        }
    }

    private fun saveAllergies(allergies: List<AllergieResponse>) {
        post(MapsState.LoadedAllergie(allergies))
        lateinit var allergiaTable: AllergieTable
        allergies.forEach { allergy ->
            viewModelScope.launch {
                allergiaTable = AllergieTable(
                    alid = allergy.alid,
                    allergia = allergy.allergia,
                    idLocale = allergy.idLocale
                )
                repository.insertAllergie(allergiaTable)
            }
        }
    }
}
