package com.example.prog7313_poe.classes

import androidx.appcompat.app.AppCompatActivity
import kotlin.math.exp

data class Expense(
    val expenseID : String? = null,
    val time : String? = null,
    val date : String? = null,
    val categoryID : String? = null,
    val description : String? = null,
    val amount : String? = null,
    val photoID : String? = null,
    val transactionType: String? = null,
    val userID : String? = null
){

    //---------------------------------------------------------------------------------------------------------------------------------------//
    // Create an Expense and insert data into db
    //---------------------------------------------------------------------------------------------------------------------------------------//
    fun createExpense(id: String,date: String, times: String, category_id: String, descript: String, value: String, photo: String, user: String, type: String) :Boolean {
        val expense = Expense(id, times, category_id, descript, value, photo, user, type)
        return true

    }
}
