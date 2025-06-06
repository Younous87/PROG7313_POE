//package com.example.prog7313_poe.ui.reports
//
//import android.app.Application
//import androidx.lifecycle.AndroidViewModel
//import androidx.lifecycle.LiveData
//import androidx.lifecycle.MutableLiveData
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.viewModelScope
//import androidx.room.Room
//import com.example.prog7313_poe.DataBase
//import com.example.prog7313_poe.classes.Category
//import com.example.prog7313_poe.classes.Goal
//import kotlinx.coroutines.launch
//
//
//class CategoriesReportsViewModel (application: Application): AndroidViewModel(application) {
//
//
//    private val repository: CategoriesReportRepository
//
//
//    val getAllData: LiveData<List<Category>>
//
//
//
//        categoryDAO = db.cDao
//
//
//        repository = CategoriesReportRepository(categoryDAO)
//
//
//        getAllData = repository.getAllData
//    }
//
//}