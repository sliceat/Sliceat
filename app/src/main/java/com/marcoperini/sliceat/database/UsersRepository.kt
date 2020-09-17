package com.marcoperini.sliceat.database

class UsersRepository(private val userDao: TableDao) {

    suspend fun insert(value: UsersTable) = userDao.insertUsers(value)

    suspend fun insert(value: LocalsTable) = userDao.insertLocals(value)

    suspend fun insert(value: AllergieTable) = userDao.insertAllergie(value)

    suspend fun deleteAllUsers() = userDao.deleteAllUsers()

    suspend fun deleteAllLocals() = userDao.deleteAllLocals()

    suspend fun deleteAllAllergie() = userDao.deleteAllAllergie()

    suspend fun getUsers() = userDao.findAllUsers()

    suspend fun getLocals() = userDao.findAllLocals()

    suspend fun getAllergie() = userDao.findAllAllergie()
}
