package com.example.prog7313_poe.ui.reports

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity.MODE_PRIVATE
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.prog7313_poe.R
import com.example.prog7313_poe.classes.CategorySpending
import com.google.firebase.firestore.FirebaseFirestore
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class CategoriesReportsFragment : Fragment() {

    private lateinit var dateStartPicker: TextView
    private lateinit var dateEndPicker: TextView
    private lateinit var backButton: ImageButton

    private var startDate: Calendar = Calendar.getInstance()
    private var endDate: Calendar = Calendar.getInstance()

    val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    //private val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()) // API format
    private val displayFormat = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault()) // Display format

    //Search
    private lateinit var recyclerView: androidx.recyclerview.widget.RecyclerView
    private lateinit var adapter: CategoriesReportAdapter
    private val firestoreDateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    private val displayDateFormat = SimpleDateFormat("yyyy-MM-dd",Locale.getDefault())
    private val db = FirebaseFirestore.getInstance()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_categories_reports, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize views with new IDs
        dateStartPicker = view.findViewById(R.id.dateStartPicker)
        dateEndPicker = view.findViewById(R.id.dateEndPicker)
        backButton = view.findViewById(R.id.imageButton5)


        val searchButton = view.findViewById<Button>(R.id.newGoalSaveButton)
        val sharedPreferences = requireContext().getSharedPreferences("user_prefs", MODE_PRIVATE)
        val userId = sharedPreferences.getString("user_id", "") ?: ""

        // Search and Recycler
        recyclerView = view.findViewById(R.id.categoryRecycler)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        adapter = CategoriesReportAdapter(emptyList())
        recyclerView.adapter = adapter

        // Reset calendar instances
        startDate.timeInMillis = 0
        endDate.timeInMillis = 0

        // Set click listeners for date pickers
        dateStartPicker.setOnClickListener { showStartDatePicker() }
        dateEndPicker.setOnClickListener { showEndDatePicker() }

        setupClickListeners()
        // Setup Listeners
        searchButton.setOnClickListener {
            if (isDateRangeValid()) {
                fetchAndDisplayReports()
            }
        }

    }

    private fun setupClickListeners() {
        backButton.setOnClickListener {
            // Handle back navigation - adjust based on your navigation setup
            requireActivity().onBackPressed()
        }
    }

    private fun showStartDatePicker() {
        val currentDate = Calendar.getInstance()

        val datePickerDialog = DatePickerDialog(
            requireContext(),
            { _, year, month, dayOfMonth ->
                startDate.set(Calendar.YEAR, year)
                startDate.set(Calendar.MONTH, month)
                startDate.set(Calendar.DAY_OF_MONTH, dayOfMonth)

                // Update the TextView with display format
                dateStartPicker.text = displayFormat.format(startDate.time)

                // Validate that start date is before end date
                if (endDate.timeInMillis > 0 && startDate.after(endDate)) {
                    // Reset end date if start date is after current end date
                    endDate.time = startDate.time
                    endDate.add(Calendar.DAY_OF_MONTH, 1) // Add one day
                    dateEndPicker.text = displayFormat.format(endDate.time)

                    Toast.makeText(requireContext(), "End date adjusted to be after start date",
                        Toast.LENGTH_SHORT).show()
                }
            },
            if (startDate.timeInMillis > 0) startDate.get(Calendar.YEAR) else currentDate.get(Calendar.YEAR),
            if (startDate.timeInMillis > 0) startDate.get(Calendar.MONTH) else currentDate.get(Calendar.MONTH),
            if (startDate.timeInMillis > 0) startDate.get(Calendar.DAY_OF_MONTH) else currentDate.get(Calendar.DAY_OF_MONTH)
        )


        datePickerDialog.show()
    }

    private fun showEndDatePicker() {
        val currentDate = Calendar.getInstance()

        val datePickerDialog = DatePickerDialog(
            requireContext(),
            { _, year, month, dayOfMonth ->
                endDate.set(Calendar.YEAR, year)
                endDate.set(Calendar.MONTH, month)
                endDate.set(Calendar.DAY_OF_MONTH, dayOfMonth)

                // Validate that end date is after start date
                if (startDate.timeInMillis > 0 && endDate.before(startDate)) {
                    Toast.makeText(requireContext(), "End date must be after start date",
                        Toast.LENGTH_SHORT).show()
                    return@DatePickerDialog
                }

                // Update the TextView with display format
                dateEndPicker.text = displayFormat.format(endDate.time)
            },
            if (endDate.timeInMillis > 0) endDate.get(Calendar.YEAR) else currentDate.get(Calendar.YEAR),
            if (endDate.timeInMillis > 0) endDate.get(Calendar.MONTH) else currentDate.get(Calendar.MONTH),
            if (endDate.timeInMillis > 0) endDate.get(Calendar.DAY_OF_MONTH) else currentDate.get(Calendar.DAY_OF_MONTH)
        )

        // Set minimum date to start date if selected
        if (startDate.timeInMillis > 0) {
            datePickerDialog.datePicker.minDate = startDate.timeInMillis
        }
        // Optional: Set minimum date to today if no start date selected
        // else {
        //     datePickerDialog.datePicker.minDate = System.currentTimeMillis()
        // }

        datePickerDialog.show()
    }

    // Helper methods
    private fun isDateRangeValid(): Boolean {
        return startDate.timeInMillis > 0 &&
                endDate.timeInMillis > 0 &&
                !endDate.before(startDate)
    }

    private fun getStartDate(): Date? {
        return if (startDate.timeInMillis > 0) startDate.time else null
    }

    private fun getEndDate(): Date? {
        return if (endDate.timeInMillis > 0) endDate.time else null
    }

    private fun getDateRangeString(): String {
        return if (isDateRangeValid()) {
            "${displayFormat.format(startDate.time)} - ${displayFormat.format(endDate.time)}"
        } else {
            ""
        }
    }
    private fun fetchAndDisplayReports(){
        // Get user id
        val sharedPreferences = requireContext().getSharedPreferences("user_prefs", MODE_PRIVATE)
        val userId = sharedPreferences.getString("user_id", "") ?: ""

        // Start and End Date
        val startDateStr = dateFormat.format(startDate.time)
        val endDateStr = dateFormat.format(endDate.time)

        db.collection("expenses")
            .whereEqualTo("userID", userId)
            .whereEqualTo("transactionType","Expense")
            .get()
            .addOnSuccessListener { expensesSnapshot ->
                val filteredExpenses = expensesSnapshot.documents
                    .mapNotNull { doc ->
                        val dateStr = doc.getString("date") ?: return@mapNotNull null
                        val date = try {
                            dateFormat.parse(dateStr)
                        } catch (e: Exception) {
                            Toast.makeText(context, "Error 1", Toast.LENGTH_SHORT).show()
                            null
                        }
                        val amount = doc.getDouble("amount") ?: return@mapNotNull null
                        val categoryId = doc.getString("categoryID") ?: return@mapNotNull null

                        if (date != null && !date.before(startDate.time) && !date.after(endDate.time)) {
                            Triple(categoryId, amount, doc.id)
                        } else null
                    }
                val categoryTotals = filteredExpenses.groupBy { it.first }
                    .mapValues { entry -> entry.value.sumOf { it.second } }

                db.collection("categories")
                    .whereEqualTo("userID", userId)
                    .get()
                    .addOnSuccessListener { categoriesSnapshot ->
                        val categoryNamesMap = categoriesSnapshot.documents.associateBy(
                            { it.getString("categoryID") ?: "" },
                            { it.getString("categoryName") ?: "Unknown" }
                        )
                        val categorySpendingList = categoryTotals.map { (categoryId, total) ->
                            val categoryName = categoryNamesMap[categoryId] ?: "Unknown"
                            CategorySpending(categoryName, total)
                        }
                        adapter.updateList(categorySpendingList)
                    }

            }
            .addOnFailureListener {
                Toast.makeText(context,"Error loading data", Toast.LENGTH_SHORT).show()
            }

    }
}
