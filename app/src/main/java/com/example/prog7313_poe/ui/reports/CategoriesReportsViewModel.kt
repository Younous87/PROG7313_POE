package com.example.prog7313_poe.ui.reports

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import com.example.prog7313_poe.DataBase
import com.example.prog7313_poe.classes.Category
import com.example.prog7313_poe.classes.CategorySpending
import com.example.prog7313_poe.classes.Goal
import com.example.prog7313_poe.data_access_object.CategoryDAO
import com.example.prog7313_poe.data_access_object.TransactionDAO
import kotlinx.coroutines.launch


class CategoriesReportsViewModel (application: Application): AndroidViewModel(application) {
    private var categoryDAO: CategoryDAO
    private var transactionDAO: TransactionDAO

    // Repository to abstract data operations and provide a clean API to the ViewModel
    private val repository: CategoriesReportRepository

    // LiveData properties to store query results
    private var _categorySpendingData: LiveData<List<CategorySpending>> = MutableLiveData()
    val categorySpendingData: LiveData<List<CategorySpending>> get() = _categorySpendingData

    // LiveData holding the list of all categories; observers (like UI) react to changes
//    val getAllData: LiveData<List<Category>>

    init {
        val db = Room.databaseBuilder(
            application,
            DataBase::class.java,
            "DataBase"
        )
            .fallbackToDestructiveMigration(false)
            .build()
        transactionDAO = db.tDao
        categoryDAO = db.cDao

        // Pass the DAO to the repository to handle data operations
        repository = CategoriesReportRepository(categoryDAO)

        // Retrieve all categories from the repository as LiveData
//        getAllData = repository.getAllData
    }

    fun getTotalSpentByCategoryPerPeriod(userID: String, startDate: String, endDate: String) {
        _categorySpendingData = transactionDAO.getTotalSpentByCategoryPerPeriod(userID, startDate, endDate)
    }

}