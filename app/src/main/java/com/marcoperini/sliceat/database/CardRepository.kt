package com.marcoperini.sliceat.database

class CardRepository(private val cardDao: TableDao) {

    suspend fun insert(value: FilterTable) = cardDao.insertAll(listOf(value))

}
