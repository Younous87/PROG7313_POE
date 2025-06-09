package com.example.prog7313_poe.ui.reports

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.prog7313_poe.classes.DailyTotal
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.*

    class ReportsViewModel(application: Application) : AndroidViewModel(application) {
    private val db = FirebaseFirestore.getInstance()

    private val _dailyTotals = MutableLiveData<List<DailyTotal>>()
    val dailyTotals: LiveData<List<DailyTotal>> = _dailyTotals

        fun loadDailyTotalsForMonth(userId: String, year: Int, month: Int) {
            val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            val calStart = Calendar.getInstance().apply {
                set(Calendar.YEAR, year)
                set(Calendar.MONTH, month)
                set(Calendar.DAY_OF_MONTH, 1)
            }
            val calEnd = Calendar.getInstance().apply {
                set(Calendar.YEAR, year)
                set(Calendar.MONTH, month)
                set(Calendar.DAY_OF_MONTH, calStart.getActualMaximum(Calendar.DAY_OF_MONTH))
            }
            val startStr = sdf.format(calStart.time)
            val endStr   = sdf.format(calEnd.time)

            db.collection("expenses")
                .whereEqualTo("userID", userId)
                .whereGreaterThanOrEqualTo("date", startStr)
                .whereLessThanOrEqualTo("date", endStr)
                .get()
                .addOnSuccessListener { snap ->
                    val grouped = snap.documents
                        .mapNotNull { doc ->
                            val ds  = doc.getString("date") ?: return@mapNotNull null
                            val amt = doc.getDouble("amount")  ?: 0.0
                            ds to amt
                        }
                        .groupBy({ it.first }, { it.second })

                    val list = grouped.mapNotNull { (ds, amounts) ->
                        sdf.parse(ds)?.let { dateObj ->
                            DailyTotal(dateObj, amounts.sum())
                        }
                    }.sortedBy { it.date }

                    _dailyTotals.value = list
                }
                .addOnFailureListener {
                    _dailyTotals.value = emptyList()
                }
        }
}
