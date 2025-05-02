package com.example.prog7313_poe.ui.transactions

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import androidx.room.Transaction
import com.example.prog7313_poe.DataBase
import com.example.prog7313_poe.classes.Expense
import com.example.prog7313_poe.classes.Goal
import com.example.prog7313_poe.data_access_object.GoalDAO
import com.example.prog7313_poe.data_access_object.TransactionDAO
import kotlinx.coroutines.launch


class TransactionsViewModel (application: Application): AndroidViewModel(application) {
    private var transactionDAO: TransactionDAO

    private val _text = MutableLiveData<String>().apply {
        value = "This is notifications Fragment"
    }
    val text: LiveData<String> = _text

    init {
        val db = Room.databaseBuilder(
            application,
            DataBase::class.java,
            "DataBase"
        ).build()
        transactionDAO = db.tDao

    }
    fun insertTransaction(
        transaction: Expense,
        onSuccess: (Long) -> Unit,
        onError: (Exception) -> Unit
    ) {
        viewModelScope.launch {
            try{
                val rowId = transactionDAO.insertTransaction(transaction)
                onSuccess(rowId)
            }catch (e: Exception){
                onError(e)
            }

        }
    }

}