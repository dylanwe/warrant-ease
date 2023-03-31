package com.example.androidapp.auth

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import com.example.androidapp.R
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuth.AuthStateListener
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

/**
 * Firebase Authentication with email and Google.
 * Inspired by the [Firebase examples](https://github.com/firebase/snippets-android/tree/cb15737fe61389d2b58c65ae171cf83c26119cb3/auth/app/src/main/java/com/google/firebase/quickstart/auth/kotlin)
 * For more documentation please check [email and password sign in](https://firebase.google.com/docs/auth/android/password-auth) & [Google sign in](https://firebase.google.com/docs/auth/android/google-signin)
 *
 * @author Dylan Weijgertze
 */
abstract class SignInActivity : ComponentActivity(), AuthStateListener {
    private lateinit var auth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // configuration of the googleSignInClient
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        // init a googleSignInClient for this app
        googleSignInClient = GoogleSignIn.getClient(this, gso)
        auth = Firebase.auth
    }

    override fun onStart() {
        super.onStart()
        auth.addAuthStateListener(this)
    }

    override fun onStop() {
        super.onStop()
        auth.removeAuthStateListener(this)
    }

    /**
     * Runs when an activity is completed and attempts to log the user in with a Google ID token
     */
    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
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
    }

    /**
     * Signs user with Google ID token in with firebase
     */
    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
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
    protected fun googleSignIn() {
        val signInIntent = googleSignInClient.signInIntent
        // start an activity to sign in
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    /**
     * Attempt to sign up with a given email and password
     */
    protected fun emailSignUp(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "createUserWithEmail:success")
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "createUserWithEmail:failure", task.exception)
                }
            }
    }

    /**
     * Attempt to sign in with a given email and password
     */
    protected fun emailSignIn(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithEmail:success")
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithEmail:failure", task.exception)
                }
            }
    }

    companion object {
        private const val TAG = "SignInActivity"
        private const val RC_SIGN_IN = 9002
    }
}
