package com.example.prog7313_poe.ui.goals

import android.content.Intent
import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity.MODE_PRIVATE
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.prog7313_poe.MainActivity
import com.example.prog7313_poe.R
import com.example.prog7313_poe.classes.Goal
import com.example.prog7313_poe.classes.User
import com.example.prog7313_poe.ui.loginRegister.LoginViewModel

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.widget.TextView
import com.example.prog7313_poe.classes.RankingManager
import java.text.SimpleDateFormat
import java.util.*

class NewGoalsFragment : Fragment() {
    private lateinit var input_month : TextView  // Changed from EditText to TextView
    private lateinit var input_minimum : EditText
    private lateinit var input_maximum : EditText
    private lateinit var goalButton : Button
    private val selectedDate = Calendar.getInstance()  // Added to store selected date
    private var selectedMonthDate: Date? = null  // Added to store the actual Date object

    companion object {
        fun newInstance() = NewGoalsFragment()
    }

    //private val viewModel: NewGoalsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // TODO: Use the ViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_new_goals, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //---------------------------------------------------------------------------------------------------------------------------------------//
        // Initialize Views
        //---------------------------------------------------------------------------------------------------------------------------------------//
        input_month = view.findViewById(R.id.monthNewGoalInput )  // Now a TextView
        input_minimum = view.findViewById(R.id.minimumNewGoalInput)
        input_maximum = view.findViewById(R.id.maximumNewGoalInput)
        goalButton = view.findViewById(R.id.newGoalSaveButton)

        // Set up month picker click listener
        input_month.setOnClickListener { showMonthYearPicker() }

        // Initialize shared preferences to get user ID
        val sharedPreferences = requireContext().getSharedPreferences("user_prefs", MODE_PRIVATE)
        val userID = sharedPreferences.getString("user_id","")

        //---------------------------------------------------------------------------------------------------------------------------------------//
        // Save Transaction button click Listener, No logic for ID
        //---------------------------------------------------------------------------------------------------------------------------------------//
        goalButton.setOnClickListener{
            val monthDate = selectedMonthDate  // Get the Date object instead of string
            val min = input_minimum.text.toString()
            val max = input_maximum.text.toString()

            // validate input
//            if(validateInput(monthDate, max, min)){
//
//                val goal = Goal(userID =  userID, month = monthDate, minimum = min, maximum =  max)  // Pass Date object
//                viewModel.insertBudgetGoal(goal)
//                // Validate goal
//                viewModel.validateGoal(userID.toString(), monthDate, max).observe(viewLifecycleOwner){ goal ->
//                    if(goal != null){
//                        Toast.makeText(context, "Goal created", Toast.LENGTH_SHORT).show()
//
//                    }else{
//                        Toast.makeText( context, "Please try again", Toast.LENGTH_SHORT).show()
//                    }
//                }
//
//            }

            // In your transaction activity, after successfully adding a transaction:
            val rankingManager = RankingManager()


            rankingManager.awardGoalPoints(userID ?: "") { success ->
                if (success) {
                    Toast.makeText(this@NewGoalsFragment.requireContext(), "+${RankingManager.TRANSACTION_XP} XP earned!", Toast.LENGTH_SHORT).show()
                }else{
                    Toast.makeText(this@NewGoalsFragment.requireContext(), "Failed to award XP", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    //---------------------------------------------------------------------------------------------------------------------------------------//
    // Month Year Picker Functions
    //---------------------------------------------------------------------------------------------------------------------------------------//
    private fun showMonthYearPicker() {
        val calendar = Calendar.getInstance()
        val currentYear = calendar.get(Calendar.YEAR)
        val currentMonth = calendar.get(Calendar.MONTH)

        // Option 1: Using DatePickerDialog (simpler but less customizable)
        val datePickerDialog = DatePickerDialog(
            requireContext(),
            { _, year, month, _ ->
                selectedDate.set(Calendar.YEAR, year)
                selectedDate.set(Calendar.MONTH, month)
                selectedDate.set(Calendar.DAY_OF_MONTH, 1) // Set to first day of month

                // Store the actual Date object
                selectedMonthDate = selectedDate.time

                // Display formatted string to user
                val monthFormat = SimpleDateFormat("MMMM yyyy", Locale.getDefault())
                val formattedMonth = monthFormat.format(selectedMonthDate!!)
                input_month.text = formattedMonth
            },
            currentYear,
            currentMonth,
            1
        )

        // Try to hide the day picker (may not work on all devices)
        try {
            val dayPicker = datePickerDialog.datePicker.javaClass.getDeclaredField("mDaySpinner")
            dayPicker.isAccessible = true
            (dayPicker.get(datePickerDialog.datePicker) as View).visibility = View.GONE
        } catch (e: Exception) {
            // Fallback: show regular date picker
        }

        datePickerDialog.show()
    }


    //---------------------------------------------------------------------------------------------------------------------------------------//
    // Validate Goal Input
    //---------------------------------------------------------------------------------------------------------------------------------------//
    private fun validateInput(monthDate: Date?, minimum: String, maximum: String): Boolean {
        if(monthDate == null){
            // Since we can't set error on TextView, you could show a Toast or highlight the field differently
            return false
        }
        if (minimum.isEmpty()) {
            input_minimum.error = "Minimum Goal cannot be empty"
            return false
        }
        if(maximum.isEmpty()){
            input_maximum.error = "Maximum Goal cannot be empty"
            return false  // Added missing return statement
        }

        return true
    }

    // Helper functions to get selected date in different formats
    fun getSelectedDate(): Date? {
        return selectedMonthDate
    }

    fun getSelectedDateFormatted(): String? {
        return selectedMonthDate?.let {
            SimpleDateFormat("MMMM yyyy", Locale.getDefault()).format(it)
        }
    }

    fun getSelectedMonth(): Int? {
        return selectedMonthDate?.let {
            val calendar = Calendar.getInstance()
            calendar.time = it
            calendar.get(Calendar.MONTH) + 1 // +1 because Calendar.MONTH is 0-based
        }
    }

    fun getSelectedYear(): Int? {
        return selectedMonthDate?.let {
            val calendar = Calendar.getInstance()
            calendar.time = it
            calendar.get(Calendar.YEAR)
        }
    }

    // Helper function to get date in database format (if needed)
    fun getSelectedDateForDatabase(): String? {
        return selectedMonthDate?.let {
            SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(it)
        }
    }

    // Helper function to get timestamp (if needed)
    fun getSelectedDateTimestamp(): Long? {
        return selectedMonthDate?.time
    }
}