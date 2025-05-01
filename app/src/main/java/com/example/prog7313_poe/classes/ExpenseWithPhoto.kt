package com.example.prog7313_poe.classes

import androidx.room.PrimaryKey

data class ExpenseWithPhoto(
    val expenseID : Int,
    val time : String?,
    val date : String?,
    val categoryID : String?,
    val description : String?,
    val amount : Double? ,
    val photoID : String?,
    val transactionType: String?,
    val userID : String?,
    val fileUri : String?

)