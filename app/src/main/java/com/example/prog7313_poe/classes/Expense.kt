package com.example.prog7313_poe.classes

import androidx.appcompat.app.AppCompatActivity

class Expense() : AppCompatActivity(){
    lateinit var expenseID : String
    lateinit var start_date : String
    lateinit var end_date : String
    lateinit var categoryID : String
    lateinit var description : String
    lateinit var amount : String
    lateinit var photoID : String
    lateinit var userID : String


    constructor(id: String, startDate: String, endDate: String, category_id: String, descript: String, value: String, photo: String, user: String) : this() {
        expenseID = id
        start_date = startDate
        end_date = endDate
        categoryID = category_id
        description = descript
        amount = value
        photoID = photo
        userID =  user
    }
}
