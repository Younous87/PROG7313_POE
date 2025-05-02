package com.example.prog7313_poe.ui.reports

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import com.example.prog7313_poe.DataBase
import com.example.prog7313_poe.classes.CategorySpending
import com.example.prog7313_poe.classes.ExpenseWithPhoto
import com.example.prog7313_poe.data_access_object.CategoryDAO
import com.example.prog7313_poe.data_access_object.GoalDAO
import com.example.prog7313_poe.data_access_object.PhotoDAO
import com.example.prog7313_poe.data_access_object.TransactionDAO
import com.example.prog7313_poe.data_access_object.UserDAO
import kotlinx.coroutines.launch

class TransactionsReportsViewModel(application: Application): AndroidViewModel(application) {

    private var transactionDAO: TransactionDAO

    // LiveData properties to store query results
    private var _categorySpendingData: LiveData<List<CategorySpending>> = MutableLiveData()
    val categorySpendingData: LiveData<List<CategorySpending>> get() = _categorySpendingData

    private var _transactionReportData: LiveData<List<ExpenseWithPhoto>> = MutableLiveData()
    val transactionReportData: LiveData<List<ExpenseWithPhoto>> get() = _transactionReportData

    init {
        val db = Room.databaseBuilder(
            application,
            DataBase::class.java,
            "DataBase"
        ).build()
        transactionDAO = db.tDao

    }


    fun getTotalSpentByCategoryPerPeriod(userID: String, startDate: String, endDate: String) {
        _categorySpendingData = transactionDAO.getTotalSpentByCategoryPerPeriod(userID, startDate, endDate)
    }

    fun getExpensesPerPeriodWithPhoto(userID: String, startDate: String, endDate: String) {
        _transactionReportData = transactionDAO.getExpensesPerPeriodWithPhoto(userID, startDate, endDate)
    }
}