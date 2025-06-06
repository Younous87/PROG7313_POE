package com.example.prog7313_poe.ui.loginRegister

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.prog7313_poe.classes.User
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject
import kotlinx.coroutines.launch

class LoginViewModel(application: Application): AndroidViewModel(application)  {
    private val firestore = FirebaseFirestore.getInstance()
    private val userCollection = firestore.collection("users")


    // Insert user into Firestore
    fun insertUser(user : User): LiveData<Boolean> {
        val result = MutableLiveData<Boolean>()
        userCollection.document(user.userID ?: "")
            .set(user)
            .addOnSuccessListener { result.value = true }
            .addOnFailureListener { result.value = false }
        return result
    }

    fun validateLogin(email: String, password: String): LiveData<User?>{
        val result = MutableLiveData<User?>()

        userCollection
            .whereEqualTo("email",email)
            .whereEqualTo("password",password)
            .get()
            .addOnSuccessListener { documents ->
                if(!documents.isEmpty){
                    val user = documents.first().toObject(User::class.java)
                    result.value = user
                }else{
                    result.value = null
                }
            }
            .addOnFailureListener {
                result.value = null
            }
        return result
    }
}
