package com.example.prog7313_poe.ui.reports

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.prog7313_poe.R
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class CategoriesReportsFragment : Fragment() {

    private lateinit var dateStartPicker: TextView
    private lateinit var dateEndPicker: TextView
    private var startDate: Calendar = Calendar.getInstance()
    private var endDate: Calendar = Calendar.getInstance()
    private val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()) // API format
    private val displayFormat = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault()) // Display format

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
        val searchButton = view.findViewById<Button>(R.id.newGoalSaveButton)

        // Reset calendar instances
        startDate.timeInMillis = 0
        endDate.timeInMillis = 0

        // Set click listeners for date pickers
        dateStartPicker.setOnClickListener { showStartDatePicker() }
        dateEndPicker.setOnClickListener { showEndDatePicker() }

        searchButton.setOnClickListener {
            if (isDateRangeValid()) {
                val start = dateFormat.format(startDate.time)
                val end = dateFormat.format(endDate.time)

                val action = CategoriesReportsFragmentDirections
                    .actionCategoriesReportsFragmentToNavigationCategories(start, end)

                findNavController().navigate(action)
            } else {
                Toast.makeText(requireContext(), "Please select both start and end dates", Toast.LENGTH_SHORT).show()
            }
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
}
