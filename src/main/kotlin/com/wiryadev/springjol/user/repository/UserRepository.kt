package com.wiryadev.springjol.user.repository

import com.wiryadev.springjol.user.model.User

interface UserRepository {

    fun insertUser(user: User): Result<Boolean>

    fun getUserById(id: String): Result<User>

    fun getUserByUsername(username: String): Result<User>
}