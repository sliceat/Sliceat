package com.marcoperini.sliceat.database

class UsersRepository(private val userDao: TableDao) {

    suspend fun insert(value: UsersTable) = userDao.insert(value)

    suspend fun deleteAll() = userDao.deleteAll()

    suspend fun getUsers() = userDao.findAll()
}
