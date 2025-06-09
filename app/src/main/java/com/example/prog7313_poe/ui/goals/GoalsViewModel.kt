package com.example.prog7313_poe.ui.goals

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.prog7313_poe.classes.Goal
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class GoalsViewModel : ViewModel() {

    private val db = FirebaseFirestore.getInstance()
    private val _goal = MutableLiveData<Goal?>()
    val goal: LiveData<Goal?> get() = _goal

    fun fetchGoal(userId: String){
        val currentMonth = SimpleDateFormat("MMMM yyyy", Locale.getDefault()).format(Date())

        db.collection("goals")
            .whereEqualTo("userID",userId)
            .whereEqualTo("month",currentMonth)
            .get()
            .addOnSuccessListener { docs ->
                if(!docs.isEmpty){
                    _goal.value = docs.documents[0].toObject(Goal::class.java)
                }else{
                    _goal.value = null
                }
            }
            .addOnFailureListener { _goal.value = null }
    }
}