package com.example.prog7313_poe.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.prog7313_poe.classes.Category
import com.example.prog7313_poe.classes.CategorySpending
import com.example.prog7313_poe.classes.DailyTotal
import com.example.prog7313_poe.classes.Expense
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class HomeViewModel : ViewModel(){
    private val _text = MutableLiveData<String>().apply {
        value = "This is goals Fragment"
    }
    val text: LiveData<String> = _text

    private val db = FirebaseFirestore.getInstance()

    private val _monthlyIncome = MutableLiveData<Double>(0.0)
    val monthlyIncome: LiveData<Double> = _monthlyIncome

    private val _monthlyExpense = MutableLiveData<Double>(0.0)
    val monthlyExpense: LiveData<Double> = _monthlyExpense


    private val _categorySpending = MutableLiveData<List<CategorySpending>>()
    val categorySpending: LiveData<List<CategorySpending>> = _categorySpending

    private val _currentCategoryIndex = MutableLiveData<Int>(0)
    val currentCategoryIndex: LiveData<Int> = _currentCategoryIndex

    private val _currentCategorySpending = MutableLiveData<CategorySpending?>()
    val currentCategorySpending: LiveData<CategorySpending?> = _currentCategorySpending


    fun loadCategorySpending(userId: String) {
        // First, get all categories for the user
        db.collection("categories")
            .whereEqualTo("userID", userId)
            .get()
            .addOnSuccessListener { categorySnap ->
                val categories = categorySnap.documents.mapNotNull { doc ->
                    doc.toObject(Category::class.java)?.apply {
                        categoryID = doc.id
                    }
                }

                // Then get expenses for each category
                loadExpensesForCategories(userId, categories)
            }
            .addOnFailureListener {
                _categorySpending.value = emptyList()
            }
    }

    private fun loadExpensesForCategories(userId: String, categories: List<Category>) {
        val categorySpendingList = mutableListOf<CategorySpending>()
        var completedRequests = 0

        if (categories.isEmpty()) {
            _categorySpending.value = emptyList()
            return
        }

        categories.forEach { category ->
            db.collection("expenses")
                .whereEqualTo("userID", userId)
                .whereEqualTo("categoryID", category.categoryID)
                .get()
                .addOnSuccessListener { expenseSnap ->
                    val expenses = expenseSnap.documents.mapNotNull { doc ->
                        doc.toObject(Expense::class.java)
                    }

                    val totalAmount = expenses.sumOf { expense ->
                        expense.amount ?: 0.0
                    }

                    categorySpendingList.add(
                        CategorySpending(
                            categoryName = category.categoryName,
                            totalAmount = totalAmount
                        )
                    )

                    completedRequests++

                    // When all requests are complete, update the LiveData
                    if (completedRequests == categories.size) {
                        val sortedList = categorySpendingList.sortedByDescending { it.totalAmount }
                        _categorySpending.value = sortedList

                        // Set the first category as current if available
                        if (sortedList.isNotEmpty()) {
                            _currentCategoryIndex.value = 0
                            _currentCategorySpending.value = sortedList[0]
                        }
                    }
                }
                .addOnFailureListener {
                    completedRequests++

                    // Even on failure, check if all requests are complete
                    if (completedRequests == categories.size) {
                        val sortedList = categorySpendingList.sortedByDescending { it.totalAmount }
                        _categorySpending.value = sortedList

                        if (sortedList.isNotEmpty()) {
                            _currentCategoryIndex.value = 0
                            _currentCategorySpending.value = sortedList[0]
                        }
                    }
                }
        }
    }

    fun navigateToNextCategory() {
        val current = _currentCategoryIndex.value ?: 0
        val categories = _categorySpending.value ?: return

        if (categories.isNotEmpty()) {
            val nextIndex = if (current < categories.size - 1) current + 1 else 0
            _currentCategoryIndex.value = nextIndex
            _currentCategorySpending.value = categories[nextIndex]
        }
    }

    fun navigateToPreviousCategory() {
        val current = _currentCategoryIndex.value ?: 0
        val categories = _categorySpending.value ?: return

        if (categories.isNotEmpty()) {
            val prevIndex = if (current > 0) current - 1 else categories.size - 1
            _currentCategoryIndex.value = prevIndex
            _currentCategorySpending.value = categories[prevIndex]
        }
    }


    fun loadMonthlyTotals(userId: String, year: Int, month: Int) {
        val cal = Calendar.getInstance().apply {
            set(year, month, 1)
        }
        val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val startDate = sdf.format(cal.time)

        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH))
        val endDate = sdf.format(cal.time)

        // Load expenses for the month
        loadMonthlyExpenses(userId, startDate, endDate)

        // Load income for the month
        loadMonthlyIncome(userId, startDate, endDate)
    }

    private fun loadMonthlyExpenses(userId: String, startDate: String, endDate: String) {

        val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val startDateObj = sdf.parse(startDate)
        val endDateObj = sdf.parse(endDate)

        db.collection("expenses")
            .whereEqualTo("userID", userId)
            .whereEqualTo("transactionType", "Expense")
            .get()
            .addOnSuccessListener { snapshot ->
                val totalExpense = snapshot.documents.sumOf { doc ->
                    val dateStr = doc.getString("date") ?: return@sumOf 0.0
                    val docDate = sdf.parse(dateStr)

                    // Check if date falls within the month range
                    if (docDate != null && startDateObj != null && endDateObj != null &&
                        !docDate.before(startDateObj) && !docDate.after(endDateObj)) {
                        doc.getDouble("amount") ?: 0.0
                    } else {
                        0.0
                    }
                }
                _monthlyExpense.value = totalExpense
            }
            .addOnFailureListener {
                _monthlyExpense.value = 0.0
            }
    }

    private fun loadMonthlyIncome(userId: String, startDate: String, endDate: String) {
        val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val startDateObj = sdf.parse(startDate)
        val endDateObj = sdf.parse(endDate)

        db.collection("expenses") // Assuming you store both income and expenses in the same collection
            .whereEqualTo("userID", userId)
            .whereEqualTo("transactionType", "Income")
            .get()
            .addOnSuccessListener { snapshot ->
                val totalIncome = snapshot.documents.sumOf { doc ->
                    val dateStr = doc.getString("date") ?: return@sumOf 0.0
                    val docDate = sdf.parse(dateStr)

                    // Check if date falls within the month range
                    if (docDate != null && startDateObj != null && endDateObj != null &&
                        !docDate.before(startDateObj) && !docDate.after(endDateObj)) {
                        doc.getDouble("amount") ?: 0.0
                    } else {
                        0.0
                    }
                }
                _monthlyIncome.value = totalIncome
            }
            .addOnFailureListener {
                _monthlyIncome.value = 0.0
            }
    }

}
