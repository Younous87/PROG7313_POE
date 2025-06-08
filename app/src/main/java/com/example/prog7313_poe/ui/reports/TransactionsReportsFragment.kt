package com.example.prog7313_poe.ui.reports

import android.widget.ArrayAdapter
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
import com.example.prog7313_poe.classes.Category
import java.text.SimpleDateFormat
import java.util.*

class TransactionsReportsFragment : Fragment(R.layout.fragment_transactions_reports) {

    private lateinit var backButton: ImageButton
    private lateinit var startDatePicker: TextView
    private lateinit var endDatePicker: TextView
    private lateinit var spinnerCategory: Spinner
    private lateinit var searchButton: Button
    private lateinit var recyclerView: RecyclerView

    private val startDate = Calendar.getInstance()
    private val endDate   = Calendar.getInstance()
    private val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

    private lateinit var adapter: TransactionReportAdapter

    private val viewModel: TransactionsReportsViewModel by viewModels()
    private val categoriesViewModel: CategoriesViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        backButton        = view.findViewById(R.id.imageButton5)
        startDatePicker   = view.findViewById(R.id.startDatePicker)
        endDatePicker     = view.findViewById(R.id.endDatePicker)
        spinnerCategory   = view.findViewById(R.id.spinnerCategory)
        searchButton      = view.findViewById(R.id.searchTransactionButton)
        recyclerView      = view.findViewById(R.id.recyclerTransactionReport)

        adapter = TransactionReportAdapter()
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter

        startDatePicker.setOnClickListener { showDatePicker(startDate, startDatePicker) }
        endDatePicker.setOnClickListener {
            showDatePicker(endDate, endDatePicker, minDate = startDate.timeInMillis)
        }

        categoriesViewModel.allCategories.observe(viewLifecycleOwner) { cats: List<Category> ->

            val ids   = mutableListOf("")
            val names = mutableListOf("All Categories")
            for (cat in cats) {
                ids.add(cat.categoryID)
                names.add(cat.categoryName)
            }

            spinnerCategory.adapter = ArrayAdapter(
                requireContext(),
                android.R.layout.simple_spinner_item,
                names
            ).also { it.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item) }

            spinnerCategory.tag = ids
        }

        viewModel.transactionReportData.observe(viewLifecycleOwner) { list: List<ExpenseWithPhoto> ->
            adapter.setData(list)
        }

        backButton.setOnClickListener { requireActivity().onBackPressed() }

        searchButton.setOnClickListener {
            if (!validateDates()) return@setOnClickListener

            val userId = requireContext()
                .getSharedPreferences("user_prefs", MODE_PRIVATE)
                .getString("user_id", "")!!

            val start = dateFormat.format(startDate.time)
            val end   = dateFormat.format(endDate.time)

            val ids = spinnerCategory.tag as List<String>
            val catId = ids[ spinnerCategory.selectedItemPosition ]

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
