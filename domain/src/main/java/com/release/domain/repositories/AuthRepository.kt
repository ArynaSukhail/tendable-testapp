package com.release.domain.repositories

interface AuthRepository {

    suspend fun signin(
        email: String,
        password: String
    )

    suspend fun signUp(
        email: String,
        password: String
    )

    suspend fun getUserEnteredKey(): Boolean

    suspend fun signout()
}
