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
        val cal = Calendar.getInstance().apply {
            set(year, month, 1)
        }
        val sdf = SimpleDateFormat("yyyy/MM/dd", Locale.getDefault())
        val start = sdf.format(cal.time)

        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH))
        val end = sdf.format(cal.time)

        db.collection("expenses")
            .whereEqualTo("userID", userId)
            .whereGreaterThanOrEqualTo("date", start)
            .whereLessThanOrEqualTo("date", end)
            .get()
            .addOnSuccessListener { snap ->
                val byDate = snap.documents
                    .mapNotNull { doc ->
                        val ds = doc.getString("date") ?: return@mapNotNull null
                        val amt = doc.getDouble("amount") ?: 0.0
                        ds to amt
                    }
                    .groupBy({ it.first }, { it.second })

                val daily = byDate.mapNotNull { (dateStr, sums) ->
                    sdf.parse(dateStr)?.let { Date ->
                        DailyTotal(Date, sums.sum())
                    }
                }.sortedBy { it.date }

                _dailyTotals.value = daily
            }
            .addOnFailureListener {
                _dailyTotals.value = emptyList()
            }
    }
}
