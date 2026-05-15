package com.syeda.raithabharosa.utils

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

object FirebaseTest {

    fun testFirebase() {

        val auth = FirebaseAuth.getInstance()

        val firestore = FirebaseFirestore.getInstance()

        Log.d("FIREBASE_TEST", "Firebase Connected Successfully")
    }
}