package com.example.prog7313_poe.activity

import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class GoalActivity: AppCompatActivity() {
    private lateinit var goal_ID : EditText
    private lateinit var userID : EditText
    private lateinit var input_month : EditText
    private lateinit var input_minimum : EditText
    private lateinit var input_maximum : EditText
    private  lateinit var goalButton : Button

    /*
   override fun onCreate (savedInstanceState: Bundle?){
       super.onCreate(savedInstanceState)
       setContentView(R.layout.)
   */
    //---------------------------------------------------------------------------------------------------------------------------------------//
    // Initialize Views
    //---------------------------------------------------------------------------------------------------------------------------------------//
    /*
    input_month =  findViewById<EditText>(R.id)
    input_minimum =  findViewById<EditText>(R.idt)
    input_maximum =  findViewById<EditText>(R.id)
    goalButton = findViewById<Button>(R.id)

     */

    //---------------------------------------------------------------------------------------------------------------------------------------//
    // Goal button click Listener
    //---------------------------------------------------------------------------------------------------------------------------------------//
    /*
    goalButton.setOnClickListener {

        val month = input_month.text.toString()
        val minimum = input_minimum.text.toString()
        val maximum = input_maximum.text.toString()

        // Check expense validation
        if (validateInput(month, maximum, minimum)) {
            Toast.makeText(this, "Your expense was created sucesfuly, Toast.LENGTH_SHORT).show()
            val goal = Goal()
            // Start MainActivity after login is successful
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)

        } else {

            Toast.makeText(this, "An error occured", Toast.LENGTH_SHORT).show()

        }
    }
     */
    //---------------------------------------------------------------------------------------------------------------------------------------//
    // Validate Goal Input
    //---------------------------------------------------------------------------------------------------------------------------------------//
    /*
    private fun validateInput(month: String, maximum: String, minimum: String): Boolean {
        if(month.isEmpty()){
            nameEditText.error = "Date cannot be empty"
            return false
        }
        if (maximum.isEmpty()) {
            nameEditText.error = "Start time cannot be empty"
            return false
        }

        if (minimum.isEmpty()) {
            emailEditText.error = "End time cannot be empty"
            return false
        }
        return true
    }
     */
}