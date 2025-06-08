package com.example.prog7313_poe.ui.reports

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity.MODE_PRIVATE
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
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
import com.example.prog7313_poe.classes.DailyTotal
import java.text.SimpleDateFormat
import java.util.*

class ReportsFragment : Fragment(R.layout.fragment_reports) {

    private lateinit var viewAllTransactionsButton: Button
    private lateinit var viewAllCategoriesButton: Button

    private lateinit var incomeView: TextView
    private lateinit var expenseView: TextView

    // Category navigation views
    private lateinit var categoryPrevBtn: ImageButton
    private lateinit var categoryNextBtn: ImageButton
    private lateinit var categorySelectLabel: TextView
    private lateinit var highBudgetView: TextView

    private lateinit var trendsPrevBtn: ImageButton
    private lateinit var trendsNextBtn: ImageButton
    private lateinit var trendsMonthLabel: TextView
    private lateinit var viewAllTrendsLink: TextView
    private lateinit var trendsChart: LineChart

    private val trendsCalendar = Calendar.getInstance()

    private val transactionViewModel: TransactionsViewModel by viewModels()
    private val categoriesViewModel: CategoriesViewModel by viewModels()
    private val reportsViewModel: ReportsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize views
        viewAllCategoriesButton = view.findViewById(R.id.AllCategoryBttn)
        viewAllTransactionsButton = view.findViewById(R.id.AllTransactionsBttn)
        incomeView = view.findViewById(R.id.IcomeView)
        expenseView = view.findViewById(R.id.ExpenseView)

        // Category navigation views
        categoryPrevBtn = view.findViewById(R.id.reportsPreviousCategorySelect)
        categoryNextBtn = view.findViewById(R.id.reportsNextCategorySelect)
        categorySelectLabel = view.findViewById(R.id.reportsCategorySelect)
        highBudgetView = view.findViewById(R.id.HighBudget)

        // Trends views
        trendsPrevBtn = view.findViewById(R.id.reportsTrendsPrevMonthBttn)
        trendsNextBtn = view.findViewById(R.id.reportsTrendsNextMonthBttn)
        trendsMonthLabel = view.findViewById(R.id.reportsTrendsMonth)
        viewAllTrendsLink = view.findViewById(R.id.AllTrendsBttn)
        trendsChart = view.findViewById(R.id.reportsLineChart)

        val sharedPreferences = requireContext()
            .getSharedPreferences("user_prefs", MODE_PRIVATE)
        val userID = sharedPreferences.getString("user_id", "") ?: ""

        setupClickListeners(userID)
        setupObservers()

        // Load data
        transactionViewModel.fetchLatestAmounts(userID)
        reportsViewModel.loadCategorySpending(userID)

        configureTrendChart()
        updateTrendMonthLabel()
        loadTrendData(userID)
    }

    private fun setupClickListeners(userID: String) {
        viewAllCategoriesButton.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_reports_to_categoriesReportsFragment2)
        }

        viewAllTransactionsButton.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_reports_to_transactionsReportsFragment2)
        }

        // Category navigation
        categoryPrevBtn.setOnClickListener {
            reportsViewModel.navigateToPreviousCategory()
        }

        categoryNextBtn.setOnClickListener {
            reportsViewModel.navigateToNextCategory()
        }

        // Trends navigation
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

    private fun setupObservers() {
        // Income and expense observers
        transactionViewModel.latestIncome.observe(viewLifecycleOwner) { income ->
            incomeView.text = "${income?.amount ?: 0.0}"
        }

        transactionViewModel.latestExpense.observe(viewLifecycleOwner) { expense ->
            expenseView.text = "${expense?.amount ?: 0.0}"
        }

        // Category spending observers
        reportsViewModel.currentCategorySpending.observe(viewLifecycleOwner) { categorySpending ->
            if (categorySpending != null) {
                categorySelectLabel.text = categorySpending.categoryName
                highBudgetView.text = String.format("%.2f", categorySpending.totalAmount)
            } else {
                categorySelectLabel.text = "No Categories"
                highBudgetView.text = "0.00"
            }
        }

        reportsViewModel.categorySpending.observe(viewLifecycleOwner) { categories ->
            // Update category navigation button states
            val hasCategories = categories.isNotEmpty()
            categoryPrevBtn.isEnabled = hasCategories
            categoryNextBtn.isEnabled = hasCategories

            if (!hasCategories) {
                categorySelectLabel.text = "No Categories"
                highBudgetView.text = "0.00"
            }
        }
    }

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

    private fun updateTrendMonthLabel() {
        val fmt = SimpleDateFormat("MMMM yyyy", Locale.getDefault())
        trendsMonthLabel.text = fmt.format(trendsCalendar.time)
    }

    private fun loadTrendData(userId: String) {
        val year = trendsCalendar.get(Calendar.YEAR)
        val month = trendsCalendar.get(Calendar.MONTH)

        reportsViewModel.loadDailyTotalsForMonth(userId, year, month)

        reportsViewModel.dailyTotals.observe(viewLifecycleOwner) { dailyTotals: List<DailyTotal> ->
            val labelFmt = SimpleDateFormat("dd MMM", Locale.getDefault())
            val labels = dailyTotals.map { dt -> labelFmt.format(dt.date) }
            val values = dailyTotals.map { dt -> dt.total.toFloat() }
            updateTrendChart(labels, values)
        }
    }

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
