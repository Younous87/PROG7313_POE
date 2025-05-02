package com.example.prog7313_poe.ui.categories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.prog7313_poe.classes.CategorySpending
import com.example.prog7313_poe.data_access_object.CategoryDAO
import com.example.prog7313_poe.data_access_object.TransactionDAO
import kotlinx.coroutines.launch

class CategoriesViewModel(
    private val tDao: TransactionDAO
) : ViewModel() {

    private val _queryTrigger = MutableLiveData<Triple<Int, String, String>>()

    val categoryTotals = MediatorLiveData<List<CategorySpending>>()
            init{
                categoryTotals.addSource(_queryTrigger) { (userID, startDate, endDate) ->
                    viewModelScope.launch {
                        val result = tDao.getCategoryTotalsBetweenDates(userID, startDate, endDate)
                        categoryTotals.postValue(result)
                    }
                }
      // Transformations.switchMap(_queryTrigger) { (userID, startDate, endDate) ->
       // tDao.getCategoryTotalsBetweenDates(userID, startDate, endDate)
            }


    fun loadTotals(userID: Int, startDate: String, endDate: String) {
        _queryTrigger.value = Triple(userID, startDate, endDate)
    }
}