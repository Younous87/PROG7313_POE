package com.example.prog7313_poe.ui.reports

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import com.example.prog7313_poe.DataBase
import com.example.prog7313_poe.data_access_object.CategoryDAO
import com.example.prog7313_poe.data_access_object.GoalDAO
import com.example.prog7313_poe.data_access_object.PhotoDAO
import com.example.prog7313_poe.data_access_object.TransactionDAO
import com.example.prog7313_poe.data_access_object.UserDAO
import kotlinx.coroutines.launch

class TransactionsReportsViewModel(application: Application): AndroidViewModel(application) {

    private var transactionDAO: TransactionDAO

    init {
        val db = Room.databaseBuilder(
            application,
            DataBase::class.java,
            "DataBase"
        ).build()
        transactionDAO = db.tDao

    }

    fun getTotalSpentByCategoryPerPeriod(userID: String, startDate: String, endDate:String) = viewModelScope.launch {
        transactionDAO.getTotalSpentByCategoryPerPeriod(userID, startDate, endDate  )
    }

    fun getExpensesPerPeriodWithPhoto(userID: String, startDate: String, endDate: String) = viewModelScope.launch {
        transactionDAO.getExpensesPerPeriodWithPhoto(userID,startDate, endDate)
    }
}