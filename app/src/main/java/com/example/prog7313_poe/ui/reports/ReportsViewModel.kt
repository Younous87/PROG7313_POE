package com.example.prog7313_poe.ui.reports

import androidx.lifecycle.ViewModel
import com.example.prog7313_poe.data_access_object.CategoryDAO
import com.example.prog7313_poe.data_access_object.GoalDAO
import com.example.prog7313_poe.data_access_object.PhotoDAO
import com.example.prog7313_poe.data_access_object.TransactionDAO
import com.example.prog7313_poe.data_access_object.UserDAO

class ReportsViewModel(
    private val cDao: CategoryDAO,
    private val gDao: GoalDAO,
    private val pDao: PhotoDAO,
    private val tDao: TransactionDAO,
    private val uDao: UserDAO
) : ViewModel() {
    // TODO: Implement the ViewModel
}