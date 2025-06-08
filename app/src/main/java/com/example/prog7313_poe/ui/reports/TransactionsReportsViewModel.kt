package com.example.prog7313_poe.ui.reports

import android.app.Application
import android.net.Uri
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import android.util.Log
import com.google.firebase.firestore.Query
import com.example.prog7313_poe.classes.ExpenseWithPhoto
import com.google.api.Context
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import androidx.core.net.toUri

class TransactionsReportsViewModel(application: Application): AndroidViewModel(application) {
    private val db = FirebaseFirestore.getInstance()

    private val _transactionReportData = MutableLiveData<List<ExpenseWithPhoto>>()
    val transactionReportData: LiveData<List<ExpenseWithPhoto>> get() = _transactionReportData

    fun getExpensesPerPeriodWithCategory(
        userID: String,
        startDate: String,
        endDate: String,
        categoryID: String
    ) {
        val query = db.collection("expenses")
            .whereEqualTo("userID", userID)
            .whereGreaterThanOrEqualTo("date", startDate)
            .whereLessThanOrEqualTo("date", endDate)

        // only filter by category if one is selected
        if (categoryID.isNotBlank()) {
            query.whereEqualTo("categoryID", categoryID)
        }

        query.orderBy("date", Query.Direction.ASCENDING)
            .get()
            .addOnSuccessListener { expenseResult ->
            }
            .addOnFailureListener {
                _transactionReportData.value = emptyList()
            }
    }

    fun getExpensesPerPeriodWithPhoto(userID: String, startDate: String, endDate: String) {
        val context = getApplication<Application>().applicationContext
        db.collection("expenses")
            .whereEqualTo("userID",userID)
            .whereGreaterThanOrEqualTo("date",startDate)
            .whereLessThanOrEqualTo("date",endDate)
            .orderBy("date",com.google.firebase.firestore.Query.Direction.ASCENDING )
            .get()
            .addOnSuccessListener { expenseResult ->
                val expenses = expenseResult.documents
                if(expenses.isEmpty()){
                    _transactionReportData.value = emptyList()
                    return@addOnSuccessListener
                }

                val photoIDs = expenses.mapNotNull { it.getString("photoID") }.distinct()
                val categoryIDs = expenses.mapNotNull { it.getString("categoryID") }.distinct()

                val photoTask = if(photoIDs.isNotEmpty()){
                    db.collection("photos").whereIn("photoID",photoIDs).get()
                }else null

                val categoryTask = if(categoryIDs.isNotEmpty()){
                    db.collection("categories").whereIn("categoryID",categoryIDs).get()
                }else null

                val photoLookups = mutableMapOf<String, String?>()
                val categoryLookups = mutableMapOf<String, String>()

                val tasks = mutableListOf<com.google.android.gms.tasks.Task<*>>()
                photoTask?.let {tasks.add(it)}
                categoryTask?.let { tasks.add(it) }

                if((tasks.isEmpty())){
                    val expenseList = expenses.map {
                        ExpenseWithPhoto(
                            description = it.getString("description")?: "",
                            categoryID = it.getString("categoryID")?: "",
                            date = it.getString("date")?: "",
                            amount = it.getDouble("amount")?: 0.0,
                            fileUri = null
                        )
                    }
                    _transactionReportData.value = expenseList
                }else{
                    com.google.android.gms.tasks.Tasks.whenAllSuccess<Any>(tasks)
                        .addOnSuccessListener { results ->
                            results.forEach{ result ->
                                when(result){
                                    is com.google.firebase.firestore.QuerySnapshot -> {
                                        for(doc in result.documents){
                                            when{
                                                // Photo result
                                                doc.contains("photoID") ->{
                                                    val id = doc.getString("photoID")?: continue
                                                    val uri = doc.getString("fileUri")
                                                    photoLookups[id] = uri
                                                }
                                                // Category result
                                                doc.contains("categoryID") ->{
                                                    val id = doc.getString("categoryID")?: continue
                                                    categoryLookups[id] = doc.getString("categoryName")?: "Unknown"
                                                }
                                            }
                                        }
                                    }
                                }

                            }
                            val expenseList = expenses.map {
                                val categoryID = it.getString("categoryID")?: ""
                                val photoID = it.getString("photoID")
                                ExpenseWithPhoto(
                                    description = it.getString("description")?: "",
                                    categoryID = categoryLookups[categoryID]?: "Unknown",
                                    date = it.getString("date")?: "",
                                    amount = it.getDouble("amount")?: 0.0,
                                    fileUri = photoID?.let { id ->
                                        photoLookups[id]?.toUri()
                                    }
                                )
                            }
                            _transactionReportData.value = expenseList
                        }
                        .addOnFailureListener{
                            _transactionReportData.value = emptyList()
                        }
                }

            }
            .addOnFailureListener {e->
                _transactionReportData.value = emptyList()
            }

    }
}