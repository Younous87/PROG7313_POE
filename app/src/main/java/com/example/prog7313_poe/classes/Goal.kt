package com.example.prog7313_poe.classes

import androidx.appcompat.app.AppCompatActivity

data class Goal(

    val goal_ID : String? = null,
    val userID : String? = null,
    val month : String? = null,
    val minimum : String? = null,
    val maximum : String? = null,
){

    fun createGoal(id: String,mon: String, id2: String, max: String, min: String) : Boolean {
        return true
    }

}