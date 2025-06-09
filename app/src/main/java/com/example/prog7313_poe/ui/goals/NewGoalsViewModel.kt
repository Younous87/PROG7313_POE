package com.example.prog7313_poe.ui.goals

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.prog7313_poe.classes.Goal
import com.example.prog7313_poe.classes.User
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch
import java.util.Date

class NewGoalsViewModel (application: Application): AndroidViewModel(application) {
    private val db = FirebaseFirestore.getInstance()
    private val goalsCollection = db.collection("goals")


    fun insertBudgetGoal(goal: Goal): LiveData<Boolean> {
        val result = MutableLiveData<Boolean>()

        goalsCollection
            .whereEqualTo("userID", goal.userID)
            .whereEqualTo("month", goal.month)
            .get()
            .addOnSuccessListener { documents ->
                if (!documents.isEmpty) {
                    // Goal for this month already exists, update it
                    val existingDocId = documents.documents[0].id
                    goalsCollection.document(existingDocId).set(goal.copy(goal_ID = existingDocId))
                        .addOnSuccessListener { result.postValue(true) }
                        .addOnFailureListener { result.postValue(false) }
                } else {
                    // No goal exists for this month, insert a new one
                    val newDocRef = goalsCollection.document()
                    val goalWithID = goal.copy(goal_ID = newDocRef.id)
                    newDocRef.set(goalWithID)
                        .addOnSuccessListener { result.postValue(true) }
                        .addOnFailureListener { result.postValue(false) }

                }

            }
            .addOnFailureListener {
                result.postValue(false)
            }
        return  result
    }


}