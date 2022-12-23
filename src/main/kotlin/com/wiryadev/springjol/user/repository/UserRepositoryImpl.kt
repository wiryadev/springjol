package com.wiryadev.springjol.user.repository

import com.mongodb.client.MongoCollection
import com.wiryadev.springjol.DatabaseComponent
import com.wiryadev.springjol.toResult
import com.wiryadev.springjol.CustomException
import com.wiryadev.springjol.user.model.User
import org.litote.kmongo.eq
import org.litote.kmongo.findOne
import org.litote.kmongo.getCollection
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository

@Repository
class UserRepositoryImpl(
    @Autowired private val dbComponent: DatabaseComponent,
) : UserRepository {

    private fun getCollection(): MongoCollection<User> {
        return dbComponent.db.getDatabase("springjol").getCollection()
    }

    override fun insertUser(user: User): Result<Boolean> {
        val existingUser = getUserByUsername(user.username)
        return if (existingUser.isSuccess) {
            throw CustomException("User exist")
        } else {
            getCollection().insertOne(user).wasAcknowledged().toResult()
        }
    }

    override fun getUserById(id: String): Result<User> {
        return getCollection().findOne { User::id eq id }.toResult()
    }

    override fun getUserByUsername(username: String): Result<User> {
        return getCollection().findOne { User::username eq username }.toResult()
    }
}