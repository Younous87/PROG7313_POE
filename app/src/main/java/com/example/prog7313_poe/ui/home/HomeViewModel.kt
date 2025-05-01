package com.example.prog7313_poe.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.prog7313_poe.data_access_object.GoalDAO

class HomeViewModel(
    private val gDao: GoalDAO
) : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is goals Fragment"
    }
    val text: LiveData<String> = _text
}