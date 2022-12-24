package com.wiryadev.springjol.user.model

data class UserRequest(
    val username: String,
    val password: String,
) {
    fun mapToNewUser(): User = User.createUser(username, password)
}
