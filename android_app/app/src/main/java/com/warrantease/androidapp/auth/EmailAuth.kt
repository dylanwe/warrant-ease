package com.warrantease.androidapp.auth

import android.util.Log
import com.google.firebase.auth.FirebaseAuth

/**
 * Firebase Authentication with email.
 * Inspired by the [Firebase examples](https://github.com/firebase/snippets-android/tree/cb15737fe61389d2b58c65ae171cf83c26119cb3/auth/app/src/main/java/com/google/firebase/quickstart/auth/kotlin)
 * For more documentation please check [email and password sign in](https://firebase.google.com/docs/auth/android/password-auth)
 *
 * @author Dylan Weijgertze
 */
class EmailAuth(
    private val auth: FirebaseAuth
) {
    fun signUp(email: String, password: String) {
        try {
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "createUserWithEmail:success")
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "createUserWithEmail:failure", task.exception)
                    }
                }
        } catch (e: Exception) {
            Log.w(TAG, "createUserWithEmail:failure", e)
        }
    }

    /**
     * Attempt to sign in with a given email and password
     */
    fun signIn(email: String, password: String) {
        try {
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "signInWithEmail:success")
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "signInWithEmail:failure", task.exception)
                    }
                }
        } catch (e: Exception) {
            Log.w(TAG, "signInWithEmail:failure", e)
        }
    }

    companion object {
        private const val TAG = "Auth"
    }
}
