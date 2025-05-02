package com.example.prog7313_poe.ui.categories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.prog7313_poe.classes.CategorySpending
import com.example.prog7313_poe.data_access_object.TransactionDAO
import androidx.lifecycle.switchMap

class CategoriesViewModel(
    private val tDao: TransactionDAO
) : ViewModel() {

    private val _queryTrigger = MutableLiveData<Triple<Int, String, String>>()

    val categoryTotals: LiveData<List<CategorySpending>> =
        _queryTrigger.switchMap { (userID, startDate, endDate) ->
            tDao.getTotalSpentByCategoryPerPeriod(userID, startDate, endDate)
        }

    fun loadTotals(userID: Int, startDate: String, endDate: String) {
        _queryTrigger.value = Triple(userID, startDate, endDate)
    }
}
