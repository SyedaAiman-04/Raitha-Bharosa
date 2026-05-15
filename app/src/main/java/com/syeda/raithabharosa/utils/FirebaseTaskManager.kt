package com.syeda.raithabharosa.utils

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.syeda.raithabharosa.ui.models.CalendarTask

object FirebaseTaskManager {

    private val firestore = FirebaseFirestore.getInstance()

    private val auth = FirebaseAuth.getInstance()

    fun addTask(
        task: CalendarTask,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    ) {

        val user = auth.currentUser

        if (user == null) {
            onFailure("User not logged in")
            return
        }

        firestore.collection("users")
            .document(user.uid)
            .collection("tasks")
            .add(task)
            .addOnSuccessListener {
                onSuccess()
            }
            .addOnFailureListener {
                onFailure(it.message ?: "Failed")
            }
    }

    fun getTasks(
        onResult: (List<CalendarTask>) -> Unit
    ) {

        val user = auth.currentUser ?: return

        firestore.collection("users")
            .document(user.uid)
            .collection("tasks")
            .get()
            .addOnSuccessListener { result ->

                val tasks =
                    result.documents.mapNotNull {
                        it.toObject(CalendarTask::class.java)
                    }

                onResult(tasks)
            }
    }
}