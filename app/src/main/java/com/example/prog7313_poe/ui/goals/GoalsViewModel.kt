package com.example.prog7313_poe.ui.goals

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.prog7313_poe.data_access_object.GoalDAO

class GoalsViewModel(
    private val gDao: GoalDAO
) : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is dashboard Fragment"
    }
    val text: LiveData<String> = _text
    // TODO: Implement the ViewModel Further
}