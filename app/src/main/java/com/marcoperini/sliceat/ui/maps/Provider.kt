package com.marcoperini.sliceat.ui.maps

import com.marcoperini.sliceat.ui.maps.network.Backend
import com.marcoperini.sliceat.ui.maps.network.response.AllergieResponse
import com.marcoperini.sliceat.ui.maps.network.response.LocalsResponse
import io.reactivex.rxjava3.core.Single

interface Contract {
    fun getLocalsData(): Single<List<LocalsResponse>>
    fun getAllergieData(): Single<List<AllergieResponse>>
}

class Provider(private val backend: Backend) : Contract {
    override fun getLocalsData(): Single<List<LocalsResponse>> {
        return backend.getLocalsData()
    }

    override fun getAllergieData(): Single<List<AllergieResponse>> {
        return backend.getAllergieData()
    }

}
