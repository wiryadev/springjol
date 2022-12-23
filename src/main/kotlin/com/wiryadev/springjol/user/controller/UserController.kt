package com.wiryadev.springjol.user.controller

import com.wiryadev.springjol.BaseResponse
import com.wiryadev.springjol.toResponse
import com.wiryadev.springjol.user.model.LoginRequest
import com.wiryadev.springjol.user.model.LoginResponse
import com.wiryadev.springjol.user.model.User
import com.wiryadev.springjol.user.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/user")
class UserController {

    @Autowired
    private lateinit var userService: UserService

    @PostMapping("/login")
     fun login(
         @RequestBody loginRequest: LoginRequest
     ): BaseResponse<LoginResponse> = userService.login(loginRequest).toResponse()

    @PostMapping("/register")
    fun register(
        @RequestBody user: User
    ): BaseResponse<Boolean> = userService.register(user).toResponse()

    @GetMapping
    fun getUser(
        @RequestParam("username") username: String
    ): BaseResponse<User> = userService.getUserByUsername(username).toResponse()

}