package com.example.prog7313_poe.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ReportFragment.Companion.reportFragment
import com.example.prog7313_poe.MainActivity
import com.example.prog7313_poe.classes.User
import com.example.prog7313_poe.R
import com.example.prog7313_poe.RegisterActivity

class ExpenseActivity: AppCompatActivity() {

    private lateinit var input_expenseID : EditText
    private lateinit var input_date : EditText
    private lateinit var input_start_time : EditText
    private lateinit var input_end_time : EditText
    private lateinit var input_categoryID : EditText
    private lateinit var input_description : EditText
    private lateinit var input_amount: EditText
    private lateinit var photoID: EditText
    private lateinit var userID : EditText
    private lateinit var expenseButton : Button
    /*
    override fun onCreate (savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.)
    */
    //---------------------------------------------------------------------------------------------------------------------------------------//
    // Initialize Views
    //---------------------------------------------------------------------------------------------------------------------------------------//
    /*
    input_expenseID = findViewById<EditText>(R.id)
    input_date =  findViewById<EditText>(R.id)
    input_start_time =  findViewById<EditText>(R.idt)
    input_end_time =  findViewById<EditText>(R.id)
    input_categoryID =  findViewById<EditText>(R.id)
    input_description =  findViewById<EditText>(R.id)
    input_amount = findViewById<EditText>(R.id)
    expenseButton = findViewById<Button>(R.id)

     */
    //---------------------------------------------------------------------------------------------------------------------------------------//
    // Expense button click Listener
    //---------------------------------------------------------------------------------------------------------------------------------------//
    /*
    expenseButton.setOnClickListener {
        val expenseID = input_expenseID.text.toString()
        val start_date = input_start_time.text.toString()
        val start_time = input_start_time.text.toString()
        val end_time = input_end_time.text.toString()
        val categoryID = input_categoryID.text.toString()
        val description = input_description.text.toString()
        val amount = input_amount.text.toString()

        // Check expense validation
        if (validateInput(start_date, start_time, end_date, description, amount:)) {
            Toast.makeText(this, "Your expense was created succesfuly, Toast.LENGTH_SHORT).show()
            val expense = Expense()
            // Start MainActivity after login is successful
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)

        } else {

            Toast.makeText(this, "An error occured", Toast.LENGTH_SHORT).show()

        }
    }

     */
    //---------------------------------------------------------------------------------------------------------------------------------------//
    // Validate Expense Input
    //---------------------------------------------------------------------------------------------------------------------------------------//
    /*
    private fun validateInput(date: String, startTime: String, endDate: String, description: String, amount: String): Boolean {
        if(date.isEmpty()){
            nameEditText.error = "Date cannot be empty"
            return false
        }
        if (startTime.isEmpty()) {
            nameEditText.error = "Start time cannot be empty"
            return false
        }

        if (endTime.isEmpty()) {
            emailEditText.error = "End time cannot be empty"
            return false
        }

        if (description.isEmpty()) {
            passwordEditText.error = "Description cannot be empty"
            return false
        }

        if (amount.isEmpty()) {
            surnameEditText.error = "Amount cannot be empty"
            return false
        }
        return true
    }

     */
    //---------------------------------------------------------------------------------------------------------------------------------------//
    // Add photo button click Listener
    //---------------------------------------------------------------------------------------------------------------------------------------//

    //---------------------------------------------------------------------------------------------------------------------------------------//
    // Get list of all expenses during a period
    //---------------------------------------------------------------------------------------------------------------------------------------//

    //---------------------------------------------------------------------------------------------------------------------------------------//
    // Get total amount per category during a period
    //---------------------------------------------------------------------------------------------------------------------------------------//



}