package com.example.prog7313_poe.ui.reports

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.prog7313_poe.classes.Category
import com.example.prog7313_poe.classes.Goal
import com.google.android.material.animation.AnimatableView.Listener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import kotlinx.coroutines.launch


class CategoriesReportsViewModel (application: Application): AndroidViewModel(application) {
    private val db = FirebaseFirestore.getInstance()
    private val _getAllData = MutableLiveData<List<Category>>()
    val getAllData: LiveData<List<Category>> get() = _getAllData

    private var listener: ListenerRegistration? = null

    init{
        fetchCategories()
    }

    private fun fetchCategories(){
        listener = db.collection("categories")
            .addSnapshotListener{ snapshot, error ->
                if(error!=null){
                    _getAllData.value = emptyList()
                    return@addSnapshotListener
                }
                val categoryList = snapshot?.documents?.mapNotNull { doc ->
                    doc.toObject(Category::class.java)?.apply { categoryID = doc.id }
                } ?: emptyList()

                _getAllData.value = categoryList
            }
    }



    override fun onCleared() {
        super.onCleared()
        listener?.remove()
    }

}
