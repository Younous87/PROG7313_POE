package com.example.prog7313_poe.classes

import androidx.appcompat.app.AppCompatActivity

class User(
    val userID : String,
    val email : String,
   val password : String
){
    fun validateLogin(inputEmail: String, inputPassword: String): Boolean{
        return inputEmail == email && inputPassword == password
    }

}