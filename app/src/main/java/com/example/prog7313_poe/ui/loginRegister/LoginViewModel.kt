package com.example.prog7313_poe.ui.loginRegister

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import com.example.prog7313_poe.DataBase
import com.example.prog7313_poe.classes.User
import com.example.prog7313_poe.data_access_object.UserDAO
import kotlinx.coroutines.launch

class LoginViewModel(application: Application): AndroidViewModel(application)  {
    private var userDao: UserDAO

    init {
        val db = Room.databaseBuilder(
            application,
            DataBase::class.java,
            "DataBase"
        )
            .fallbackToDestructiveMigration(false)
            .build()
        userDao = db.uDao

    }
    fun insertUser(user : User) = viewModelScope.launch {
        userDao.insertUser(user)
    }

    fun validateLogin(email: String, password: String): LiveData<User?>{
        val result = MutableLiveData<User?>()
        viewModelScope.launch {
            result.postValue(userDao.validateLogin(email,password))
        }
        return result
    }
}
