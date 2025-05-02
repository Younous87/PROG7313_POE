package com.example.prog7313_poe.ui.reports

import androidx.lifecycle.LiveData
import com.example.prog7313_poe.classes.ExpenseWithPhoto
import com.example.prog7313_poe.data_access_object.TransactionDAO

class TransactionsReportRepository(private val transactionDAO: TransactionDAO) {

//    val getAllExpensesPerPeriod: LiveData<List<ExpenseWithPhoto>> = transactionDAO.getExpensesPerPeriodWithPhoto()

    fun getExpensesWithPhotosInPeriod(
        userID: String,
        startDate: String,
        endDate: String
    ): LiveData<List<ExpenseWithPhoto>> {
        return transactionDAO.getExpensesPerPeriodWithPhoto(userID, startDate, endDate)
    }
}

