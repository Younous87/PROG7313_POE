package com.example.prog7313_poe.classes

import android.net.Uri

class ExpenseWithPhoto (
    var expenseID : String? = null,
    var time : String?= null,
    var date : String?= null,
    var categoryID : String?= null,
    var description : String?= null,
    var amount : Double? = null,
    var photoID : String?= null,
    var transactionType: String?= null,
    var userID : String?= null,
    var fileUri : Uri?= null
)