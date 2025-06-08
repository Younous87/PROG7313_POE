package com.example.prog7313_poe.ui.reports

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity.MODE_PRIVATE
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.prog7313_poe.R
import com.example.prog7313_poe.classes.ExpenseWithPhoto
import com.example.prog7313_poe.ui.categories.CategoriesViewModel
import java.text.SimpleDateFormat
import java.util.*

class TransactionsReportsFragment : Fragment(R.layout.fragment_transactions_reports) {

    // views
    private lateinit var backButton: ImageButton
    private lateinit var startDatePicker: TextView
    private lateinit var endDatePicker: TextView
    private lateinit var spinnerCategory: Spinner
    private lateinit var searchButton: Button
    private lateinit var recyclerView: RecyclerView

    // date handling
    private val startDate = Calendar.getInstance()
    private val endDate   = Calendar.getInstance()
    private val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

    // adapter
    private lateinit var adapter: TransactionReportAdapter

    // viewmodels
    private val viewModel: TransactionsReportsViewModel by viewModels()
    private val categoriesViewModel: CategoriesViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 1) bind all views
        backButton        = view.findViewById(R.id.imageButton5)
        startDatePicker   = view.findViewById(R.id.startDatePicker)
        endDatePicker     = view.findViewById(R.id.endDatePicker)
        spinnerCategory   = view.findViewById(R.id.spinnerCategory)
        searchButton      = view.findViewById(R.id.searchTransactionButton)
        recyclerView      = view.findViewById(R.id.recyclerTransactionReport)

        // 2) setup RecyclerView
        adapter = TransactionReportAdapter()
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter

        // 3) setup date‐pickers
        startDatePicker.setOnClickListener { showDatePicker(startDate, startDatePicker) }
        endDatePicker.setOnClickListener {
            showDatePicker(endDate, endDatePicker, minDate = startDate.timeInMillis)
        }

        // 4) populate category spinner
        categoriesViewModel.readAllData.observe(viewLifecycleOwner) { cats ->
            val ids   = listOf("") + cats.map { it.categoryID }
            val names = listOf("All Categories") + cats.map { it.categoryName }
            spinnerCategory.adapter = ArrayAdapter(
                requireContext(),
                android.R.layout.simple_spinner_item,
                names
            ).also { it.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item) }
            spinnerCategory.tag = ids
        }

        // 5) observe Firestore results
        viewModel.transactionReportData.observe(viewLifecycleOwner) { list: List<ExpenseWithPhoto> ->
            adapter.setData(list)
        }

        // 6) back navigation
        backButton.setOnClickListener { requireActivity().onBackPressed() }

        // 7) single search handler
        searchButton.setOnClickListener {
            if (!validateDates()) return@setOnClickListener

            val userId = requireContext()
                .getSharedPreferences("user_prefs", MODE_PRIVATE)
                .getString("user_id", "")!!

            val start = dateFormat.format(startDate.time)
            val end   = dateFormat.format(endDate.time)

            // lookup selected category ID
            val ids = spinnerCategory.tag as List<String>
            val catId = ids[ spinnerCategory.selectedItemPosition ]

            // call your Firestore query
            viewModel.getExpensesPerPeriodWithCategory(userId, start, end, catId)
        }
    }

    private fun showDatePicker(
        cal: Calendar,
        display: TextView,
        minDate: Long? = null
    ) {
        val dlg = DatePickerDialog(
            requireContext(),
            { _, y, m, d ->
                cal.set(y, m, d)
                display.text = dateFormat.format(cal.time)
            },
            cal.get(Calendar.YEAR),
            cal.get(Calendar.MONTH),
            cal.get(Calendar.DAY_OF_MONTH)
        )
        minDate?.let { dlg.datePicker.minDate = it }
        dlg.show()
    }

    private fun validateDates(): Boolean {
        if (startDatePicker.text.isBlank()) {
            Toast.makeText(requireContext(), "Select a start date", Toast.LENGTH_SHORT).show()
            return false
        }
        if (endDatePicker.text.isBlank()) {
            Toast.makeText(requireContext(), "Select an end date", Toast.LENGTH_SHORT).show()
            return false
        }
        if (endDate.before(startDate)) {
            Toast.makeText(
                requireContext(),
                "End date cannot be before start date",
                Toast.LENGTH_SHORT
            ).show()
            return false
        }
        return true
    }
}
