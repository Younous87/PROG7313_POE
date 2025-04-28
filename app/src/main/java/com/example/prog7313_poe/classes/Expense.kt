package com.example.prog7313_poe.classes

import androidx.appcompat.app.AppCompatActivity
import kotlin.math.exp

data class Expense(
    val expenseID : String? = null,
    val start_date : String? = null,
    val end_date : String? = null,
    val categoryID : String? = null,
    val description : String? = null,
    val amount : String? = null,
    val photoID : String? = null,
    val userID : String? = null
){

    //---------------------------------------------------------------------------------------------------------------------------------------//
    // Create an Expense and insert data into db
    //---------------------------------------------------------------------------------------------------------------------------------------//
    fun createExpense(id: String, startDate: String, endDate: String, category_id: String, descript: String, value: String, photo: String, user: String) :Boolean {
        val expense = Expense(id, startDate, endDate, category_id, descript, value, photo, user)
        if(expense.toString().isNotEmpty()) {
            return true
        } else {
            return false
        }

    }
}
