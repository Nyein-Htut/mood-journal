// app/src/main/java/com/example/moodjournal/auth/AuthRepository.kt
package com.example.moodjournal.auth

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
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
        } catch (e: FirebaseAuthUserCollisionException) {
            Result.failure(Exception("That username is already taken. Try logging in instead."))
        } catch (e: FirebaseAuthWeakPasswordException) {
            Result.failure(Exception("Please use a stronger password (at least 6 characters)."))
        } catch (e: Exception) {
            Result.failure(Exception("Couldn't create your account. Please check your connection and try again."))
        }
    }

    suspend fun logIn(username: String, password: String): Result<Unit> {
        return try {
            val email = "$username@moodjournal.app"
            auth.signInWithEmailAndPassword(email, password).await()
            Result.success(Unit)
        } catch (e: FirebaseAuthInvalidUserException) {
            Result.failure(Exception("Please sign up first to log in."))
        } catch (e: FirebaseAuthInvalidCredentialsException) {
            Result.failure(Exception("Incorrect username or password."))
        } catch (e: Exception) {
            Result.failure(Exception("Couldn't log in right now. Please check your connection and try again."))
        }
    }

    fun logOut() {
        auth.signOut()
    }
}
