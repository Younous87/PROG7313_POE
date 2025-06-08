package com.example.prog7313_poe.ui.reports


import android.annotation.SuppressLint
import android.app.DatePickerDialog
import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity.MODE_PRIVATE
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.prog7313_poe.R
import com.example.prog7313_poe.classes.Goal


import android.widget.TextView
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale


class TransactionsReportsFragment : Fragment() {

    private lateinit var mTransactionsReportsViewModel: TransactionsReportsViewModel
    private lateinit var startDatePicker: TextView
    private lateinit var endDatePicker: TextView
    private lateinit var searchButton: Button
    private lateinit var backButton: ImageButton

    // Date handling variables
    private val startDate = Calendar.getInstance()
    private val endDate = Calendar.getInstance()
    private val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

    companion object {
        fun newInstance() = TransactionsReportsFragment()
    }

    private val viewModel: TransactionsReportsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // TODO: Use the ViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_transactions_reports, container, false)
        return view
    }

    @SuppressLint("SuspiciousIndentation")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        //---------------------------------------------------------------------------------------------------------------------------------------//
        // Initialize Views
        //---------------------------------------------------------------------------------------------------------------------------------------//
        startDatePicker = view.findViewById(R.id.startDatePicker)
        endDatePicker = view.findViewById(R.id.endDatePicker)
        searchButton = view.findViewById(R.id.searchTransactionButton)
        backButton = view.findViewById(R.id.imageButton5)


        // Set up date picker click listeners
        setupDatePickers()

        // Initialize shared preferences to get user ID
        val sharedPreferences = requireContext().getSharedPreferences("user_prefs", MODE_PRIVATE)
        val userID = sharedPreferences.getString("user_id", "") ?: ""

        //---------------------------------------------------------------------------------------------------------------------------------------//
        // Uncomment and modify this section when you're ready to use the ViewModel
        //---------------------------------------------------------------------------------------------------------------------------------------//

        // Set up the RecyclerView with its adapter
        val adapter = TransactionReportAdapter()
        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerTransactionReport)

        // Attach the adapter to the RecyclerView
        recyclerView.adapter = adapter

        // Set a layout manager to determine how items are arranged (in this case, vertical list)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        // Initialize the ViewModel to manage UI-related data
        mTransactionsReportsViewModel = ViewModelProvider(this)[TransactionsReportsViewModel::class.java]

        // Observe the transaction report data
        mTransactionsReportsViewModel.transactionReportData.observe(viewLifecycleOwner) { transactions ->
            // Update the adapter with the new data
            adapter.setData(transactions)
        }

        setupClickListeners()

        //---------------------------------------------------------------------------------------------------------------------------------------//
        // Search Transaction button click Listener
        //---------------------------------------------------------------------------------------------------------------------------------------//
        searchButton.setOnClickListener {
            if (validateInput()) {
                val start = getStartDateString()
                val end = getEndDateString()

                // Show selected date range for confirmation
                Toast.makeText(
                    requireContext(),
                    "Searching from $start to $end",
                    Toast.LENGTH_SHORT
                ).show()

                //---------------------------------------------------------------------------------------------------------------------------------------//
                // Uncomment this section when you're ready to use the ViewModel
                //---------------------------------------------------------------------------------------------------------------------------------------//

                // Call the ViewModel method to fetch data
                mTransactionsReportsViewModel.getExpensesPerPeriodWithPhoto(userID, start, end)

                mTransactionsReportsViewModel.transactionReportData.observe(viewLifecycleOwner) { transactions ->
                    // Update the adapter with the new data
                    adapter.setData(transactions)
                }

            }
        }
    }

    private fun setupClickListeners() {
        backButton.setOnClickListener {
            // Handle back navigation - adjust based on your navigation setup
            requireActivity().onBackPressed()
        }
    }

    //---------------------------------------------------------------------------------------------------------------------------------------//
    // Set up Date Pickers
    //---------------------------------------------------------------------------------------------------------------------------------------//
    private fun setupDatePickers() {
        startDatePicker.setOnClickListener { showStartDatePicker() }
        endDatePicker.setOnClickListener { showEndDatePicker() }
    }

    private fun showStartDatePicker() {
        val datePickerDialog = DatePickerDialog(
            requireContext(),
            { _, year, month, dayOfMonth ->
                startDate.set(year, month, dayOfMonth)
                startDatePicker.text = dateFormat.format(startDate.time)

                // Ensure end date is not before start date
                if (endDate.before(startDate)) {
                    endDate.time = startDate.time
                    endDatePicker.text = dateFormat.format(endDate.time)
                }
            },
            startDate.get(Calendar.YEAR),
            startDate.get(Calendar.MONTH),
            startDate.get(Calendar.DAY_OF_MONTH)
        )

        datePickerDialog.show()
    }

    private fun showEndDatePicker() {
        val datePickerDialog = DatePickerDialog(
            requireContext(),
            { _, year, month, dayOfMonth ->
                val selectedDate = Calendar.getInstance().apply {
                    set(year, month, dayOfMonth)
                }

                // Ensure end date is not before start date
                if (selectedDate.before(startDate)) {
                    Toast.makeText(
                        requireContext(),
                        "End date cannot be before start date",
                        Toast.LENGTH_SHORT
                    ).show()
                    return@DatePickerDialog
                }

                endDate.time = selectedDate.time
                endDatePicker.text = dateFormat.format(endDate.time)
            },
            endDate.get(Calendar.YEAR),
            endDate.get(Calendar.MONTH),
            endDate.get(Calendar.DAY_OF_MONTH)
        )

        // Set minimum date to start date
        if (!startDatePicker.text.isNullOrEmpty() && startDatePicker.text != "Select Start Date") {
            datePickerDialog.datePicker.minDate = startDate.timeInMillis
        }

        datePickerDialog.show()
    }

    //---------------------------------------------------------------------------------------------------------------------------------------//
    // Validate Date Input
    //---------------------------------------------------------------------------------------------------------------------------------------//
    private fun validateInput(): Boolean {
        val startText = startDatePicker.text?.toString() ?: ""
        val endText = endDatePicker.text?.toString() ?: ""

        if (startText.isEmpty() || startText == "Select Start Date") {
            Toast.makeText(requireContext(), "Please select a start date", Toast.LENGTH_SHORT).show()
            return false
        }

        if (endText.isEmpty() || endText == "Select End Date") {
            Toast.makeText(requireContext(), "Please select an end date", Toast.LENGTH_SHORT).show()
            return false
        }

        // Additional validation: Check if end date is after start date
        if (endDate.before(startDate)) {
            Toast.makeText(requireContext(), "End date must be after start date", Toast.LENGTH_SHORT).show()
            return false
        }

        return true
    }

    //---------------------------------------------------------------------------------------------------------------------------------------//
    // Helper methods to get selected dates
    //---------------------------------------------------------------------------------------------------------------------------------------//
    // Calendar objects
    fun getSelectedStartDate(): Calendar = startDate
    fun getSelectedEndDate(): Calendar = endDate

    // Date objects
    fun getStartDateAsDate(): Date = startDate.time
    fun getEndDateAsDate(): Date = endDate.time

    // Formatted strings
    fun getStartDateString(): String = dateFormat.format(startDate.time)
    fun getEndDateString(): String = dateFormat.format(endDate.time)
    fun getSelectedDateRange(): String = "${getStartDateString()} to ${getEndDateString()}"

    // Additional utility methods
    fun getDateRange(): Pair<Date, Date> = Pair(startDate.time, endDate.time)
    fun getDateRangeAsCalendar(): Pair<Calendar, Calendar> = Pair(startDate, endDate)
}