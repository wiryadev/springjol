package com.wiryadev.springjol.user.model

import com.fasterxml.jackson.annotation.JsonIgnore

data class User(
    @JsonIgnore val id: String = "",
    val username: String = "",
    val password: String = "",
)
