package com.example.prog7313_poe.classes

import android.widget.Button
import android.widget.EditText
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity()
data class User(
    @PrimaryKey(autoGenerate = true)
    val userID : String,
    val email : String? = null,
    val password : String? = null,
    val name: String? = null,
    val surname : String? = null

){

    // Validate user and password
    // connect to db and check if user and pw exist in the db
    fun validateLogin(inputEmail: String, inputPassword: String): Boolean{
        return inputEmail == email && inputPassword == password
    }
    // Create new user
    fun createUser(name: String, surname: String, email: String, password: String): Boolean{

        val user = User("",email,password,name,surname)
        return true
    }

}