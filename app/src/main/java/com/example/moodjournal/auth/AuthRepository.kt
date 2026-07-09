package com.example.moodjournal.auth

import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await

class AuthRepository {
    private val auth = FirebaseAuth.getInstance()

    val currentUserUId: String? get() = auth.currentUser?.uid
    val isLoggedIn: Boolean get() = auth.currentUser != null

    suspend fun signUp(username: String, password: String): Result<Unit> {
        return try {
            val email = "$username@moodjournal.app"
            auth.createUserWithEmailAndPassword(email, password).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun logIn(username: String, password: String): Result<Unit> {
        return try {
            val email = "$username@moodjournal.app"
            auth.signInWithEmailAndPassword(email, password).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    fun logOut() {
        auth.signOut()
    }
}
