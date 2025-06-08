package com.example.prog7313_poe.classes
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey


data class Category(
    var categoryID : String = "",
    var categoryName: String = "",
    var userID : String = "",
){
    constructor(): this("","","")
}


