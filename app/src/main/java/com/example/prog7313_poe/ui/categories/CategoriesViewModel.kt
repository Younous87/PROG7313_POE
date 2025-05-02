package com.example.prog7313_poe.ui.categories

import android.app.Application
import android.app.SharedElementCallback
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import com.example.prog7313_poe.DataBase
import com.example.prog7313_poe.data_access_object.CategoryDAO
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CategoriesViewModel(application: Application): AndroidViewModel(application) {
    private val cDAO: CategoryDAO
    init {
        val db = Room.databaseBuilder(
            application,
            DataBase::class.java,
            "DataBase"
        ).build()
        cDAO = db.cDao
    }

}
