package com.example.androidapp.auth

import android.app.Activity
import android.content.Intent
import android.util.Log
import androidx.lifecycle.DefaultLifecycleObserver
import com.example.androidapp.R
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

/**
 * Firebase Authentication with Google.
 * Inspired by the [Firebase examples](https://github.com/firebase/snippets-android/tree/cb15737fe61389d2b58c65ae171cf83c26119cb3/auth/app/src/main/java/com/google/firebase/quickstart/auth/kotlin)
 * For more documentation please check [Google sign in](https://firebase.google.com/docs/auth/android/google-signin)
 *
 * @author Dylan Weijgertze
 */
class GoogleAuth(
    private val auth: FirebaseAuth,
    private val activity: Activity,
) : DefaultLifecycleObserver {
    private var googleSignInClient: GoogleSignInClient

    init {
        // configuration of the googleSignInClient
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(activity.getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        // init a googleSignInClient for this app
        googleSignInClient = GoogleSignIn.getClient(activity, gso)
    }

    /**
     * Runs when an activity is completed and attempts to log the user in with a Google ID token
     */
    fun onActivityResult(data: Intent?) {
        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        val task = GoogleSignIn.getSignedInAccountFromIntent(data)
        try {
            // Google Sign In was successful, authenticate with Firebase
            val account = task.getResult(ApiException::class.java)!!
            Log.d(TAG, "firebaseAuthWithGoogle:" + account.id)
            firebaseAuthWithGoogle(account.idToken!!)
        } catch (e: ApiException) {
            // Google Sign In failed, update UI appropriately
            Log.w(TAG, "Google sign in failed", e)
        }
    }

    /**
     * Signs user with Google ID token in with firebase
     */
    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithCredential:success")
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithCredential:failure", task.exception)
                }
            }
    }

    /**
     * Attempt to sign in with Google
     */
    fun signIn() {
        val signInIntent = googleSignInClient.signInIntent
        // Start an activity to sign in
        activity.startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    companion object {
        private const val TAG = "Auth"
        const val RC_SIGN_IN = 9002
    }
}