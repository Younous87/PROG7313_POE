package com.example.prog7313_poe.ui.goals

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import com.example.prog7313_poe.DataBase
import com.example.prog7313_poe.classes.Goal
import com.example.prog7313_poe.classes.User
import com.example.prog7313_poe.data_access_object.GoalDAO
import kotlinx.coroutines.launch

class NewGoalsViewModel (application: Application): AndroidViewModel(application) {
    private var goalDao: GoalDAO


    init {
        val db = Room.databaseBuilder(
            application,
            DataBase::class.java,
            "DataBase"
        ).build()
        goalDao = db.gDao

    }
    fun insertBudgetGoal(goal: Goal) = viewModelScope.launch {
        goalDao.insertBudgetGoal(goal)
    }
}