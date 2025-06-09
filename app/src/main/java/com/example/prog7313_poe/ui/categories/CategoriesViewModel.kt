package com.example.prog7313_poe.ui.categories

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.prog7313_poe.classes.Category
import com.example.prog7313_poe.classes.CategorySpending
import com.google.firebase.firestore.FieldPath
import com.google.firebase.firestore.FirebaseFirestore
import java.util.Date

class CategoriesViewModel(app: Application) : AndroidViewModel(app) {

    private val firestore = FirebaseFirestore.getInstance()
    private val categoryCollection = firestore.collection("categories")
    private val transactionCollection = firestore.collection("expenses")

    // Get category name with total amount spent
    private val _categorySpendingList = MutableLiveData<List<CategorySpending>>()
    val categorySpendingList: LiveData<List<CategorySpending>> get() = _categorySpendingList

    private val _allCategories = MutableLiveData<List<Category>>()
    val allCategories: LiveData<List<Category>> = _allCategories



    fun loadCategoriesForUser(userId: String) {
        categoryCollection
            .whereEqualTo("userID", userId)
            .get()
            .addOnSuccessListener {
                snap ->
                val list = snap.documents.mapNotNull{
                    doc ->
                    val id = doc.id
                    val name = doc.getString("categoryName") ?: return@mapNotNull null
                    Category(id, name)
                }
                _allCategories.postValue(list)
            }
            .addOnFailureListener {
                _allCategories.postValue(emptyList())
            }
    }

    fun loadTotals(userId: String, start: Date, end: Date){
        transactionCollection
            .whereEqualTo("userID",userId)
            .whereEqualTo("transactionType","expense")
            .whereGreaterThanOrEqualTo("date", start)
            .whereLessThanOrEqualTo("date",end)
            .get()
            .addOnSuccessListener { expensesDocs ->
                val categoryTotals = mutableMapOf<String, Double>()

                for(doc in expensesDocs){
                    val categoryID = doc.getString("categoryID") ?: continue
                    val amount = doc.getDouble("amount") ?: 0.0
                    categoryTotals[categoryID] = categoryTotals.getOrDefault(categoryID, 0.0) + amount
                }
                if(categoryTotals.isEmpty()){
                    _categorySpendingList.postValue(emptyList())
                    return@addOnSuccessListener
                }

                categoryCollection
                    .whereIn(FieldPath.documentId(),categoryTotals.keys.toList())
                    .get()
                    .addOnSuccessListener { categoryDocs ->
                        val result = mutableListOf<CategorySpending>()
                        for(doc in categoryDocs){
                            val categoryName = doc.getString("categoryName") ?: "Unknown"
                            val totalSpent = categoryTotals[doc.id]?: 0.0
                            result.add(CategorySpending(categoryName,totalSpent))
                        }
                        _categorySpendingList.postValue(result)
                    }
            }
    }
}

