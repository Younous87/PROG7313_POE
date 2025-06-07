package com.example.prog7313_poe.classes
import android.icu.text.DateFormat.HourCycle
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.sql.Time
import java.time.LocalDate
import java.time.LocalTime
import java.util.Date
import kotlin.math.exp

data class Expense(
    var expenseID :String? = null,
    var time : String? = null,
    var date : String? = null,
    var categoryID : String? = null,
    var description : String? = null,
    var amount : Double? = null,
    var photoID : String? = null,
    var transactionType: String? = null,
    var userID : String? = null
){

    //---------------------------------------------------------------------------------------------------------------------------------------//
    // Create an Expense and insert data into db
    //---------------------------------------------------------------------------------------------------------------------------------------//
//    fun createExpense(id: String,date: String, times: String, category_id: String, descript: String, value: String, photo: String, user: String, type: String) :Boolean {
//        val expense = Expense(id, times, category_id, descript, value, photo, user, type)
//        return true
//
//    }
}
