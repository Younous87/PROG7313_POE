package com.example.prog7313_poe.ui.reports

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.core.net.toUri
import com.example.prog7313_poe.classes.ExpenseWithPhoto
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

class TransactionsReportsViewModel(app: Application) : AndroidViewModel(app) {
    private val db = FirebaseFirestore.getInstance()

    private val _transactionReportData = MutableLiveData<List<ExpenseWithPhoto>>()
    val transactionReportData: LiveData<List<ExpenseWithPhoto>> = _transactionReportData

    fun getExpensesWithPhotoAndCategory(
        userID: String,
        startDate: String,
        endDate: String,
        categoryID: String
    ) {
        db.collection("expenses")
            .whereEqualTo("userID", userID)
            .whereGreaterThanOrEqualTo("date", startDate)
            .whereLessThanOrEqualTo("date", endDate)
            .orderBy("date", Query.Direction.ASCENDING)
            .get()
            .addOnSuccessListener { snap ->
                val docs = if (categoryID.isNotBlank()) {
                    snap.documents.filter { it.getString("categoryID") == categoryID }
                } else {
                    snap.documents
                }

                val photoIDs    = docs.mapNotNull { it.getString("photoID") }.distinct()
                val categoryIDs = docs.mapNotNull { it.getString("categoryID") }.distinct()

                val photoTask    = photoIDs   .takeIf { it.isNotEmpty() }?.let { ids ->
                    db.collection("photos").whereIn("photoID", ids).get()
                }
                val categoryTask = categoryIDs.takeIf { it.isNotEmpty() }?.let { ids ->
                    db.collection("categories").whereIn("categoryID", ids).get()
                }

                val photoMap    = mutableMapOf<String, String?>()
                val categoryMap = mutableMapOf<String, String>()

                if (photoTask == null && categoryTask == null) {
                    _transactionReportData.value = docs.map {
                        ExpenseWithPhoto(
                            description = it.getString("description") ?: "",
                            categoryID  = "",  // no name lookup
                            date        = it.getString("date") ?: "",
                            amount      = it.getDouble("amount") ?: 0.0,
                            fileUri     = null
                        )
                    }
                    return@addOnSuccessListener
                }

                listOfNotNull(photoTask, categoryTask)
                    .let { com.google.android.gms.tasks.Tasks.whenAllSuccess<Any>(it) }
                    .addOnSuccessListener { results ->
                        // build lookup maps
                        results.filterIsInstance<com.google.firebase.firestore.QuerySnapshot>()
                            .forEach { ss ->
                                ss.documents.forEach { d ->
                                    d.getString("photoID")   ?.let { id -> photoMap[id] = d.getString("fileUri") }
                                    d.getString("categoryID")?.let { id -> categoryMap[id] = d.getString("categoryName") ?: "Unknown" }
                                }
                            }

                        _transactionReportData.value = docs.map { d ->
                            val cid = d.getString("categoryID") ?: ""
                            val pid = d.getString("photoID")
                            ExpenseWithPhoto(
                                description = d.getString("description") ?: "",
                                categoryID  = categoryMap[cid] ?: "Unknown",
                                date        = d.getString("date") ?: "",
                                amount      = d.getDouble("amount") ?: 0.0,
                                fileUri     = pid?.let { photoMap[it]?.toUri() }
                            )
                        }
                    }
                    .addOnFailureListener {
                        _transactionReportData.value = emptyList()
                    }
            }
            .addOnFailureListener {
                _transactionReportData.value = emptyList()
            }
    }
}
