package com.example.prog7313_poe.classes

import androidx.appcompat.app.AppCompatActivity
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.util.Date


data class Goal(
    var goal_ID : String? = null,
    var userID : String? = null,
    var month : Date? = null,
    var minimum : Double? = null,
    var maximum : Double? = null,
){

//    fun createGoal(id: String,mon: String, id2: String, max: String, min: String) : Boolean {
//        return true
//    }

}