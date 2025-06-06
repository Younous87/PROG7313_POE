//package com.example.prog7313_poe.ui.goals
//
//import android.app.Application
//import androidx.lifecycle.AndroidViewModel
//import androidx.lifecycle.LiveData
//import androidx.lifecycle.MutableLiveData
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.viewModelScope
//import androidx.room.Room
//import com.example.prog7313_poe.DataBase
//import com.example.prog7313_poe.classes.Goal
//import com.example.prog7313_poe.classes.User
//import kotlinx.coroutines.launch
//
//class NewGoalsViewModel (application: Application): AndroidViewModel(application) {
//
//
//    init {
//        val db = Room.databaseBuilder(
//            application,
//            DataBase::class.java,
//            "DataBase"
//        ).build()
//        goalDao = db.gDao
//
//    }
//    fun insertBudgetGoal(goal: Goal) = viewModelScope.launch {
//        goalDao.insertBudgetGoal(goal)
//    }
//
//    fun validateGoal(userId: String, month: String, maximum: String): LiveData<Goal?> {
//        val result = MutableLiveData<Goal?>()
//        viewModelScope.launch {
//            result.postValue(goalDao.validateGoal(userId,month,maximum))
//        }
//        return result
//    }
//
//}