package com.wiryadev.springjol.user.service

import com.wiryadev.springjol.CustomException
import com.wiryadev.springjol.auth.JwtConfig
import com.wiryadev.springjol.user.model.LoginRequest
import com.wiryadev.springjol.user.model.LoginResponse
import com.wiryadev.springjol.user.model.User
import com.wiryadev.springjol.user.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class UserServiceImpl(
    @Autowired private val userRepository: UserRepository
) : UserService {

    override fun login(loginRequest: LoginRequest): Result<LoginResponse> {
        val resultUser = userRepository.getUserByUsername(loginRequest.username)
        return resultUser.map {
            val passwordInDb = it.password
            if (passwordInDb == loginRequest.password) {
                val token = JwtConfig.generateToken(it)
                return@map LoginResponse(token)
            } else {
                throw CustomException("Wrong Username or Password")
            }
        }
    }

    override fun register(user: User): Result<Boolean> {
        return userRepository.insertUser(user)
    }

    override fun getUserByUsername(username: String): Result<User> {
        return userRepository.getUserByUsername(username)
    }

    override fun getUserById(id: String): Result<User> {
        return userRepository.getUserById(id)
    }
}