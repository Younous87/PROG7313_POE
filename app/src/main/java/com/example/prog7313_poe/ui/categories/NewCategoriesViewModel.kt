package com.example.prog7313_poe.ui.categories

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import com.example.prog7313_poe.DataBase
import com.example.prog7313_poe.classes.Category
import com.example.prog7313_poe.classes.Goal
import com.example.prog7313_poe.data_access_object.CategoryDAO
import com.example.prog7313_poe.data_access_object.GoalDAO
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NewCategoriesViewModel (application: Application): AndroidViewModel(application) {
    private var categoryDAO: CategoryDAO

    // LiveData to notify when category doesn't exist
    private val _categoryNotFound = MutableLiveData<Boolean>()
    val categoryNotFound: LiveData<Boolean> get() = _categoryNotFound

    // LiveData to get Category ID
    private val _categoryID = MutableLiveData<Int?>()
    val categoryId : LiveData<Int?> get() = _categoryID

    init {
        val db = Room.databaseBuilder(
            application,
            DataBase::class.java,
            "DataBase"
        )
            .fallbackToDestructiveMigration(false)
            .build()
        categoryDAO = db.cDao

    }
    fun insertCategory(category: Category) = viewModelScope.launch {
        categoryDAO.insertCategory(category)
    }

    // Validate Category
    fun validateCategoryInput(inputCategory : String, userId: String){
        viewModelScope.launch(Dispatchers.IO){
            val count = categoryDAO.countCategoryIdByName(inputCategory.trim(), userId)
            _categoryNotFound.postValue(count == 0)
            }


    }
    // Fetch category ID by category name
//    fun fetchCategoryIdByName(name: String){
//        viewModelScope.launch(Dispatchers.IO){
//            val id = categoryDAO.getCategoryIdByName(name)
//            _categoryID.postValue(id)
//        }

}