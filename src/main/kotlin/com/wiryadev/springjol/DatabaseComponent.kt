package com.wiryadev.springjol

import org.litote.kmongo.KMongo
import org.springframework.stereotype.Component

@Component
class DatabaseComponent {

    private val dbUrl = System.getenv("DATABASE_URL")
    val db = KMongo.createClient(dbUrl)
}