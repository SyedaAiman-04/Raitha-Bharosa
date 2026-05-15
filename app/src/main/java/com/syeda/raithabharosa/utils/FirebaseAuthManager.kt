package com.syeda.raithabharosa.utils

import android.content.Context
import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import com.google.android.gms.auth.api.signin.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.FirebaseFirestore
import com.syeda.raithabharosa.R

object FirebaseAuthManager {

    private val auth = FirebaseAuth.getInstance()
    private val firestore = FirebaseFirestore.getInstance()

    fun getGoogleSignInClient(context: Context): GoogleSignInClient {

        val gso = GoogleSignInOptions.Builder(
            GoogleSignInOptions.DEFAULT_SIGN_IN
        )
            .requestIdToken(context.getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        return GoogleSignIn.getClient(context, gso)
    }

    fun signIn(
        launcher: ActivityResultLauncher<Intent>,
        client: GoogleSignInClient
    ) {
        launcher.launch(client.signInIntent)
    }

    fun firebaseAuthWithGoogle(
        idToken: String,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    ) {

        val credential =
            GoogleAuthProvider.getCredential(idToken, null)

        auth.signInWithCredential(credential)
            .addOnCompleteListener { task ->

                if (task.isSuccessful) {

                    val user = auth.currentUser

                    if (user != null) {

                        val userMap = hashMapOf(
                            "name" to user.displayName,
                            "email" to user.email,
                            "photoUrl" to user.photoUrl.toString()
                        )

                        firestore.collection("users")
                            .document(user.uid)
                            .set(userMap)

                        onSuccess()
                    }

                } else {
                    onFailure(
                        task.exception?.message ?: "Login Failed"
                    )
                }
            }
    }
}