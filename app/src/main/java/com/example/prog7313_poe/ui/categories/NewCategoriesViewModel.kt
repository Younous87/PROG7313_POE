package com.example.prog7313_poe.ui.categories

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import com.example.prog7313_poe.DataBase
import com.example.prog7313_poe.classes.Category
import com.example.prog7313_poe.classes.Goal
import com.example.prog7313_poe.data_access_object.CategoryDAO
import com.example.prog7313_poe.data_access_object.GoalDAO
import kotlinx.coroutines.launch

class NewCategoriesViewModel (application: Application): AndroidViewModel(application) {
    private var categoryDAO: CategoryDAO


    init {
        val db = Room.databaseBuilder(
            application,
            DataBase::class.java,
            "DataBase"
        ).build()
        categoryDAO = db.cDao

    }
    fun insertCategory(category: Category) = viewModelScope.launch {
        categoryDAO.insertCategory(category)
    }
}