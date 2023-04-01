package com.example.androidapp

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.androidapp.auth.EmailAuth
import com.example.androidapp.auth.GoogleAuth
import com.example.androidapp.navigation.AppNavigation
import com.example.androidapp.ui.theme.AndroidAppTheme
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuth.AuthStateListener
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : ComponentActivity(), AuthStateListener {
    private lateinit var auth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var googleAuth: GoogleAuth
    private lateinit var emailAuth: EmailAuth

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

        // init auth providers
        googleAuth = GoogleAuth(auth, googleSignInClient)
        emailAuth = EmailAuth(auth)
    }

    override fun onStart() {
        super.onStart()
        // listen for auth state
        auth.addAuthStateListener(this)
    }

    override fun onStop() {
        super.onStop()
        // stop listening for auth state
        auth.removeAuthStateListener(this)
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        // handle activity result
        googleAuth.onActivityResult(requestCode, resultCode, data)
    }

    /**
     * Update the UI based on Firebase auth state
     */
    override fun onAuthStateChanged(firebaseAuth: FirebaseAuth) {
        setContent {
            AndroidAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navHostController = rememberNavController()
                    AppNavigation(
                        navHostController = navHostController,
                        googleSignIn = {
                            googleAuth.googleSignIn(startActivity = { signInIntent, RC_SIGN_IN ->
                                startActivityForResult(signInIntent, RC_SIGN_IN)
                            })
                        },
                        emailSignIn = emailAuth::emailSignIn,
                        emailSignUp = emailAuth::emailSignUp
                    )
                }
            }
        }
    }
}
