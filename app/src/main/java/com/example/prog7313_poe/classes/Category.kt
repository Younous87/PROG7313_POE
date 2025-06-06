package com.example.prog7313_poe.classes
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey


data class Category(
    var categoryID : String? = null,
    var categoryName: String? = null,
    var userID : String,

)


