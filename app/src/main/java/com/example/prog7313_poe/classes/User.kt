package com.example.prog7313_poe.classes

class User(
    val userID : String,
    val email : String,
    val password : String
){
    fun validateLogin(inputEmail: String, inputPassword: String): Boolean{
        return inputEmail == email && inputPassword == password
    }

}