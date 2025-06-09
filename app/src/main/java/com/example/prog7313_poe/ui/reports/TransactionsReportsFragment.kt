package com.example.prog7313_poe.ui.reports

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity.MODE_PRIVATE
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.prog7313_poe.R
import com.example.prog7313_poe.classes.ExpenseWithPhoto
import com.example.prog7313_poe.classes.Goal
import com.example.prog7313_poe.ui.categories.CategoriesViewModel
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.LimitLine
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import java.text.SimpleDateFormat
import java.util.*

class TransactionsReportsFragment : Fragment(R.layout.fragment_transactions_reports) {

    private lateinit var backButton: ImageButton
    private lateinit var startDatePicker: TextView
    private lateinit var endDatePicker: TextView
    private lateinit var spinnerCategory: Spinner
    private lateinit var searchButton: Button
    private lateinit var recyclerView: RecyclerView
    private lateinit var lineChart: LineChart

    private val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    private val startDate = Calendar.getInstance()
    private val endDate   = Calendar.getInstance()

    private val transVM      : TransactionsReportsViewModel by viewModels()
    private val categoriesVM : CategoriesViewModel           by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        backButton      = view.findViewById(R.id.imageButton5)
        startDatePicker = view.findViewById(R.id.startDatePicker)
        endDatePicker   = view.findViewById(R.id.endDatePicker)
        spinnerCategory = view.findViewById(R.id.spinnerCategory)
        searchButton    = view.findViewById(R.id.searchTransactionButton)
        recyclerView    = view.findViewById(R.id.recyclerTransactionReport)
        lineChart       = view.findViewById(R.id.lineChart)

        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter       = TransactionReportAdapter()

        configureChart(lineChart)

        startDatePicker.setOnClickListener { pickDate(startDate, startDatePicker) }
        endDatePicker  .setOnClickListener {
            pickDate(endDate, endDatePicker, minDate = startDate.timeInMillis)
        }

        val userId = requireContext()
            .getSharedPreferences("user_prefs", MODE_PRIVATE)
            .getString("user_id","")!!
        categoriesVM.loadCategoriesForUser(userId)
        categoriesVM.allCategories.observe(viewLifecycleOwner) { cats ->
            val ids   = mutableListOf<String>("")
            val names = mutableListOf("All Categories")
            cats.forEach { c ->
                ids.add(c.categoryID)
                names.add(c.categoryName)
            }
            spinnerCategory.adapter = ArrayAdapter(
                requireContext(),
                android.R.layout.simple_spinner_item,
                names
            ).also { it.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item) }
            spinnerCategory.tag = ids
        }

        transVM.transactionReportData.observe(viewLifecycleOwner) { list ->
            (recyclerView.adapter as TransactionReportAdapter).setData(list)
            redrawChart(list, null)
        }

        searchButton.setOnClickListener {
            if (!validateDates()) return@setOnClickListener

            val startStr = dateFormat.format(startDate.time)
            val endStr   = dateFormat.format(endDate.time)
            val ids      = spinnerCategory.tag as List<String>
            val catId    = ids[spinnerCategory.selectedItemPosition]

            transVM.getExpensesWithPhotoAndCategory(userId, startStr, endStr, catId)
        }

        backButton.setOnClickListener { requireActivity().onBackPressed() }
    }

    private fun pickDate(cal: Calendar, target: TextView, minDate: Long? = null) {
        DatePickerDialog(
            requireContext(),
            { _, y, m, d ->
                cal.set(y, m, d)
                target.text = dateFormat.format(cal.time)
            },
            cal.get(Calendar.YEAR),
            cal.get(Calendar.MONTH),
            cal.get(Calendar.DAY_OF_MONTH)
        ).apply {
            minDate?.let { datePicker.minDate = it }
            show()
        }
    }

    private fun validateDates(): Boolean {
        if (startDatePicker.text.isBlank()) {
            Toast.makeText(requireContext(), "Select start date", Toast.LENGTH_SHORT).show()
            return false
        }
        if (endDatePicker.text.isBlank()) {
            Toast.makeText(requireContext(), "Select end date", Toast.LENGTH_SHORT).show()
            return false
        }
        if (endDate.before(startDate)) {
            Toast.makeText(requireContext(), "End date must be after start date", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

    private fun configureChart(chart: LineChart) {
        with(chart) {
            axisRight.isEnabled   = false
            description.isEnabled = false
            legend.isEnabled      = false
            xAxis.position        = XAxis.XAxisPosition.BOTTOM
            xAxis.setDrawGridLines(false)
            axisLeft.setDrawGridLines(false)
        }
    }

    private fun redrawChart(data: List<ExpenseWithPhoto>, goal: Goal?) {
        if (data.isEmpty() && goal == null) {
            lineChart.clear()
            lineChart.invalidate()
            return
        }

        val sums = data
            .filter { !it.date.isNullOrBlank() }
            .groupBy { it.date!! }
            .mapValues { (_, items) ->
                items.sumByDouble { it.amount ?: 0.0 }
            }

        val sortedDates = sums.keys
            .mapNotNull { ds -> dateFormat.parse(ds)?.let { parsed -> ds to parsed } }
            .sortedBy { it.second }
            .map { it.first }

        val entries = sortedDates.mapIndexed { i, ds ->
            Entry(i.toFloat(), (sums[ds] ?: 0.0).toFloat())
        }

        lineChart.axisLeft.removeAllLimitLines()
        goal?.let {
            val minL = LimitLine(it.minimum?.toFloat() ?: 0f, "Min")
            val maxL = LimitLine(it.maximum?.toFloat() ?: 0f, "Max")
            lineChart.axisLeft.addLimitLine(minL)
            lineChart.axisLeft.addLimitLine(maxL)
        }

        val set = LineDataSet(entries, "Daily Totals").apply {
            color = ContextCompat.getColor(requireContext(), R.color.ez_yellow)
            lineWidth = 2f
            setDrawCircles(false)
        }
        lineChart.data = LineData(set)
        lineChart.xAxis.valueFormatter = IndexAxisValueFormatter(sortedDates)
        lineChart.invalidate()
    }
}
