package com.example.prog7313_poe.classes

import android.widget.Button
import android.widget.EditText
import androidx.room.Entity
import androidx.room.PrimaryKey

data class User(
    var userID : String? = null,
    var email : String? = null,
    var password : String? = null,
    var name: String? = null,
    var surname : String? = null

)