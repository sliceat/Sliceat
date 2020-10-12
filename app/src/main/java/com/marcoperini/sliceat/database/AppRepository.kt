package com.marcoperini.sliceat.database

class AppRepository(private val userDao: UsersDao, private val localsDao: LocalsDao, private val allergieDao: AllergieDao) {

    suspend fun insertUser(value: UsersTable) = userDao.insertUsers(value)

    suspend fun insertLocals(value: LocalsTable) = localsDao.insertLocals(value)

    suspend fun insertAllergie(value: AllergieTable) = allergieDao.insertAllergie(value)

    suspend fun deleteAllUsers() = userDao.deleteAllUsers()

    suspend fun deleteAllLocals() = localsDao.deleteAllLocals()

    suspend fun deleteAllAllergie() = allergieDao.deleteAllAllergie()

    suspend fun getUsers() = userDao.findAllUsers()

    suspend fun getLocals(city: String) = localsDao.findAllLocals(city)

    suspend fun getAllergie() = allergieDao.findAllAllergie()
}
