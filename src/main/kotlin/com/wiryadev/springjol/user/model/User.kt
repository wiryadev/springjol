package com.wiryadev.springjol.user.model

import java.util.*

data class User(
    val id: String = "",
    val username: String = "",
    val password: String = "",
) {
    companion object {
        fun createUser(
            username: String,
            password: String,
        ): User = User(
            id = UUID.randomUUID().toString(),
            username = username,
            password = password,
        )
    }
}
