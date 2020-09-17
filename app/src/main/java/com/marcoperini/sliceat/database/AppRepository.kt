package com.marcoperini.sliceat.database

class AppRepository(private val userDao: TableDao) {

    suspend fun insertUser(value: UsersTable) = userDao.insertUsers(value)

    suspend fun insertLocals(value: LocalsTable) = userDao.insertLocals(value)

    suspend fun insertAllergie(value: AllergieTable) = userDao.insertAllergie(value)

    suspend fun deleteAllUsers() = userDao.deleteAllUsers()

    suspend fun deleteAllLocals() = userDao.deleteAllLocals()

    suspend fun deleteAllAllergie() = userDao.deleteAllAllergie()

    suspend fun getUsers() = userDao.findAllUsers()

    suspend fun getLocals() = userDao.findAllLocals()

    suspend fun getAllergie() = userDao.findAllAllergie()
}
