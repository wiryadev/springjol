package com.wiryadev.springjol.user.service

import com.wiryadev.springjol.user.model.LoginRequest
import com.wiryadev.springjol.user.model.LoginResponse
import com.wiryadev.springjol.user.model.User

interface UserService {
    fun login(loginRequest: LoginRequest): Result<LoginResponse>
    fun register(user: User): Result<Boolean>
    fun getUserById(id: String): Result<User>
    fun getUserByUsername(username: String): Result<User>
}