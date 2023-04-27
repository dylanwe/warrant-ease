package com.warrantease.androidapp.presentation

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuth.AuthStateListener
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.ramcosta.composedestinations.DestinationsNavHost
import com.warrantease.androidapp.auth.GoogleAuth
import com.warrantease.androidapp.generic.di.AppModule
import com.warrantease.androidapp.presentation.ui.NavGraphs
import com.warrantease.androidapp.presentation.ui.destinations.HomeScreenDestination
import com.warrantease.androidapp.presentation.ui.destinations.OnboardingScreenDestination
import com.warrantease.androidapp.presentation.ui.destinations.SignInScreenDestination
import com.warrantease.androidapp.presentation.ui.onboarding.OnboardingScreen
import com.warrantease.androidapp.presentation.ui.theme.AndroidAppTheme
import org.koin.android.ext.koin.androidContext
import org.koin.core.component.KoinComponent
import org.koin.core.context.startKoin
import org.koin.ksp.generated.*

/**
 * Activity with Firebase Authentication.
 * Inspired by the [Firebase examples](https://github.com/firebase/snippets-android/tree/cb15737fe61389d2b58c65ae171cf83c26119cb3/auth/app/src/main/java/com/google/firebase/quickstart/auth/kotlin)
 *
 * @author Dylan Weijgertze
 */
class MainActivity : ComponentActivity(), AuthStateListener, KoinComponent {
    private lateinit var auth: FirebaseAuth
    private lateinit var googleAuth: GoogleAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        startKoin {
            androidContext(this@MainActivity)
            modules(
                AppModule().module
            )
        }

        auth = Firebase.auth
        // Init auth providers
        googleAuth = GoogleAuth(auth, this)
    }

    override fun onStart() {
        super.onStart()
        auth.addAuthStateListener(this)
    }

    override fun onStop() {
        super.onStop()
        auth.removeAuthStateListener(this)
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == GoogleAuth.RC_SIGN_IN) {
            googleAuth.onActivityResult(data)
        }
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
                    // Pick start navigation
                    val start = if (firebaseAuth.currentUser != null) {
                        if (firebaseAuth.currentUser!!.displayName.isNullOrBlank()) {
                            OnboardingScreenDestination
                        } else {
                            HomeScreenDestination
                        }
                    } else {
                        SignInScreenDestination
                    }

                    DestinationsNavHost(navGraph = NavGraphs.root, startRoute = start)
                }
            }
        }
    }
}
