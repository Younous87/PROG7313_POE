package com.example.prog7313_poe.ui.reports

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity.MODE_PRIVATE
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import com.example.prog7313_poe.R
import com.example.prog7313_poe.ui.categories.CategoriesViewModel
import com.example.prog7313_poe.ui.transactions.TransactionsViewModel
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import androidx.core.content.ContextCompat
import java.text.SimpleDateFormat
import java.util.*

class ReportsFragment : Fragment(R.layout.fragment_reports) {

    // Navigation buttons
    private lateinit var viewAllTransactionsButton: Button
    private lateinit var viewAllCategoriesButton: Button

    // Summary views
    private lateinit var incomeView: TextView
    private lateinit var expenseView: TextView

    // Trends UI
    private lateinit var trendsPrevBtn: ImageButton
    private lateinit var trendsNextBtn: ImageButton
    private lateinit var trendsMonthLabel: TextView
    private lateinit var viewAllTrendsLink: TextView
    private lateinit var trendsChart: LineChart

    // Calendar for month navigation
    private val trendsCalendar = Calendar.getInstance()

    // ViewModels
    private val transactionViewModel: TransactionsViewModel by viewModels()
    private val categoriesViewModel: CategoriesViewModel by viewModels()
    private val reportsViewModel: ReportsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize navigation and summary views
        viewAllCategoriesButton = view.findViewById(R.id.AllCategoryBttn)
        viewAllTransactionsButton = view.findViewById(R.id.AllTransactionsBttn)
        incomeView = view.findViewById(R.id.IcomeView)
        expenseView = view.findViewById(R.id.ExpenseView)

        // Initialize Trends views
        trendsPrevBtn = view.findViewById(R.id.reportsTrendsPrevMonthBttn)
        trendsNextBtn = view.findViewById(R.id.reportsTrendsNextMonthBttn)
        trendsMonthLabel = view.findViewById(R.id.reportsTrendsMonth)
        viewAllTrendsLink = view.findViewById(R.id.viewAllTrends)
        trendsChart = view.findViewById(R.id.reportsLineChart)

        // Get user ID
        val sharedPreferences = requireContext()
            .getSharedPreferences("user_prefs", MODE_PRIVATE)
        val userID = sharedPreferences.getString("user_id", "") ?: ""

        // Navigation
        viewAllCategoriesButton.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_reports_to_categoriesReportsFragment2)
        }
        viewAllTransactionsButton.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_reports_to_transactionsReportsFragment2)
        }

        // Observe latest amounts
        transactionViewModel.latestIncome.observe(viewLifecycleOwner) { income ->
            incomeView.text = "${income?.amount ?: 0.0}"
        }
        transactionViewModel.latestExpense.observe(viewLifecycleOwner) { expense ->
            expenseView.text = "${expense?.amount ?: 0.0}"
        }
        transactionViewModel.fetchLatestAmounts(userID)

        // Trends setup
        configureTrendChart()
        updateTrendMonthLabel()
        loadTrendData(userID)

        trendsPrevBtn.setOnClickListener {
            trendsCalendar.add(Calendar.MONTH, -1)
            updateTrendMonthLabel()
            loadTrendData(userID)
        }
        trendsNextBtn.setOnClickListener {
            trendsCalendar.add(Calendar.MONTH, 1)
            updateTrendMonthLabel()
            loadTrendData(userID)
        }

        viewAllTrendsLink.setOnClickListener {
            // TODO: Navigate to detailed trends screen
        }
    }

    // Configure chart appearance
    private fun configureTrendChart() {
        with(trendsChart) {
            axisRight.isEnabled = false
            description.isEnabled = false
            legend.isEnabled = false
            xAxis.position = XAxis.XAxisPosition.BOTTOM
            xAxis.setDrawGridLines(false)
            axisLeft.setDrawGridLines(false)
        }
    }

    // Update the month label text
    private fun updateTrendMonthLabel() {
        val fmt = SimpleDateFormat("MMMM yyyy", Locale.getDefault())
        trendsMonthLabel.text = fmt.format(trendsCalendar.time)
    }

    // Load data and observe
    private fun loadTrendData(userId: String) {
        val year = trendsCalendar.get(Calendar.YEAR)
        val month = trendsCalendar.get(Calendar.MONTH)
        reportsViewModel
            .getDailyTotalsForMonth(userId, year, month)
            .observe(viewLifecycleOwner) { dailyTotals ->
                val labelFmt = SimpleDateFormat("dd MMM", Locale.getDefault())
                val labels = dailyTotals.map { labelFmt.format(it.date) }
                val values = dailyTotals.map { it.total.toFloat() }
                updateTrendChart(labels, values)
            }
    }

    // Update chart with new data
    private fun updateTrendChart(labels: List<String>, values: List<Float>) {
        val entries = values.mapIndexed { i, v -> Entry(i.toFloat(), v) }
        val set = LineDataSet(entries, "Expenses").apply {
            color = ContextCompat.getColor(requireContext(), R.color.ez_yellow)
            setDrawCircles(false)
            lineWidth = 2f
            mode = LineDataSet.Mode.CUBIC_BEZIER
        }
        trendsChart.data = LineData(set)
        trendsChart.xAxis.valueFormatter = IndexAxisValueFormatter(labels)
        trendsChart.invalidate()
    }
}
