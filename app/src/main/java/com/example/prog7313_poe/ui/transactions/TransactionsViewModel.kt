package com.example.prog7313_poe.ui.transactions

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Query
import com.example.prog7313_poe.classes.Expense
import com.example.prog7313_poe.classes.Goal
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject
import kotlinx.coroutines.launch


class TransactionsViewModel (application: Application): AndroidViewModel(application) {

    private val firestore = FirebaseFirestore.getInstance()
    private val transactionCollection = firestore.collection("expenses")

    private val _text = MutableLiveData<String>().apply {
        value = "This is notifications Fragment"
    }
    // Income amount for report fragment
    private val _latestIncome = MutableLiveData<Expense?>()
    val latestIncome : LiveData<Expense?> get() = _latestIncome

    // Expense amount for report fragment
    private val _latestExpense = MutableLiveData<Expense?>()
    val latestExpense: LiveData<Expense?> get() = _latestExpense


    val text: LiveData<String> = _text

    // Insert a transaction into Firestore
    fun insertTransaction(
        transaction: Expense,
        onSuccess: (Long) -> Unit,
        onError: (Exception) -> Unit
    ) {
        transactionCollection
            .add(transaction)
            .addOnSuccessListener { }
            .addOnFailureListener { onError(it) }
    }

    // Function to fetch latest income and expense amounts
    fun fetchLatestAmounts(userID: String){
        // Fetch latest income
        transactionCollection
            .whereEqualTo("userId",userID)
            .whereEqualTo("transactionType","income")
            //.orderBy("date", Query.Direction.DESCENDING)
            .limit(1)
            .get()
            .addOnSuccessListener { snapshot ->
                val latest = snapshot.documents.firstOrNull()?.toObject(Expense::class.java)
                _latestIncome.postValue(latest)
            }
        // Fetch latest expense
        transactionCollection
            .whereEqualTo("userId",userID)
            .whereEqualTo("transactionType","expense")
            //.orderBy("date", Query.Direction.DESCENDING)
            .limit(1)
            .get()
            .addOnSuccessListener { snapshot ->
                val latest = snapshot.documents.firstOrNull()?.toObject(Expense::class.java)
                _latestExpense.postValue(latest)
            }
    }

    //

}