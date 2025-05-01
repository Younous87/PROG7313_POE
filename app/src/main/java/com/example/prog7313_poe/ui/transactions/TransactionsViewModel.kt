package com.example.prog7313_poe.ui.transactions

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.prog7313_poe.data_access_object.TransactionDAO

class TransactionsViewModel(
    private val tDao: TransactionDAO
) : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is notifications Fragment"
    }
    val text: LiveData<String> = _text
}