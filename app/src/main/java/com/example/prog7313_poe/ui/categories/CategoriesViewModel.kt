package com.example.prog7313_poe.ui.categories

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.prog7313_poe.classes.CategorySpending
import androidx.lifecycle.switchMap
import androidx.room.Room
import com.example.prog7313_poe.DataBase

class CategoriesViewModel(app: Application)
    : AndroidViewModel(app) {

    private val tDao = Room.databaseBuilder(
        app,
        DataBase::class.java,
        "DataBase"
    )
        .build()
        .tDao

    private val _query = MutableLiveData<Triple<Int, String, String>>()

    val categoryTotals: LiveData<List<CategorySpending>> =
        _query.switchMap { (userId, start, end) ->
            tDao.getTotalSpentByCategoryPerPeriod(userId, start, end)
        }

    fun loadTotals(userId: Int, start: String, end: String) {
        _query.value = Triple(userId, start, end)
    }
}

