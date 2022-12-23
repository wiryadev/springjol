package com.wiryadev.springjol.user.service

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
            val token = JwtConfig.generateToken(it)
            LoginResponse(token)
        }
    }

    override fun register(user: User): Result<Boolean> {
        return userRepository.insertUser(user)
    }

    override fun getUserByUsername(username: String): Result<User> {
        return userRepository.getUserByUsername(username)
    }
}