package com.example.prog7313_poe.ui.categories

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.prog7313_poe.classes.Category
import com.example.prog7313_poe.classes.Goal
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class NewCategoriesViewModel (application: Application): AndroidViewModel(application) {
    private val firestore = FirebaseFirestore.getInstance()
    private val categoryCollection = firestore.collection("categories")


    // LiveData to notify when category doesn't exist
    private val _categoryNotFound = MutableLiveData<Boolean>()
    val categoryNotFound: LiveData<Boolean> get() = _categoryNotFound

    // LiveData to get Category ID
    private val _categoryID = MutableLiveData<String?>()
    val categoryId : LiveData<String?> get() = _categoryID


    fun insertCategory(category: Category): LiveData<Boolean> {
        val result = MutableLiveData<Boolean>()
        val docRef = categoryCollection.document()
        val categoryWithID = category.copy(categoryID = docRef.id)

        docRef.set(categoryWithID)
            .addOnSuccessListener { result.postValue(true) }
            .addOnFailureListener { result.postValue(false) }
        return result
    }

    // Validate Category
    fun validateCategoryInput(inputCategory : String, userId: String){
        val trimmedInput = inputCategory.trim().lowercase()
        categoryCollection
            .whereEqualTo("userId",userId)
            .get()
            .addOnSuccessListener { snapshot ->
                val exists = snapshot.documents.any {doc ->
                    val name = doc.getString("categoryName")?.lowercase()
                    name == trimmedInput
                }
                _categoryNotFound.postValue(!exists)
            }
            .addOnFailureListener { _categoryNotFound.postValue(true) }

    }

    // Fetch category ID by category name
    fun fetchCategoryIdByName(name: String, userId: String){
        categoryCollection
            .whereEqualTo("categoryName",name)
            .whereEqualTo("userID",userId)
            .get()
            .addOnSuccessListener { snapshot ->
                val doc = snapshot.documents.firstOrNull()
                _categoryID.postValue(doc?.id)
            }
            .addOnFailureListener { _categoryID.postValue(null) }
        }

    fun fetchAllCategories(userId: String, onComplete: (List<Category>) -> Unit){
        categoryCollection
            .whereEqualTo("userID",userId)
            .get()
            .addOnSuccessListener { snapshot ->
                val categories = snapshot.documents.mapNotNull { it.toObject((Category::class.java)) }
                onComplete(categories)
            }
            .addOnFailureListener {
                onComplete(emptyList())
            }
    }
}