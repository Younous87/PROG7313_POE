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
        val docRef = goalsCollection.document()
        val goalWithID = goal.copy(goal_ID = docRef.id)

        docRef.set(goalWithID)
            .addOnSuccessListener {result.postValue(true) }
            .addOnFailureListener { result.postValue(false) }
        return result
    }

    fun validateGoal(userId: String, month: String, maximum: String): LiveData<Goal?> {
        val result = MutableLiveData<Goal?>()

        goalsCollection
            .whereEqualTo("userID", userId)
            .whereEqualTo("month", month)
            .whereEqualTo("maximum",maximum)
            .limit(1)
            .get()
            .addOnSuccessListener { documents ->
                if (!documents.isEmpty){
                    val goal = documents.documents[0].toObject(Goal::class.java)
                    result.value = goal
                }else{
                    result.value = null
                }
            }
            .addOnFailureListener {
                result.value = null
            }
        return result
    }

}