package com.example.prog7313_poe.activity

import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class CategoryActivity : AppCompatActivity() {
    lateinit var categoryID : EditText
    lateinit var  input_description : EditText
    lateinit var categoryButton : Button
    /*
  override fun onCreate (savedInstanceState: Bundle?){
      super.onCreate(savedInstanceState)
      setContentView(R.layout.)
  */
    //---------------------------------------------------------------------------------------------------------------------------------------//
    // Initialize Views
    //---------------------------------------------------------------------------------------------------------------------------------------//
    /*
    input_category =  findViewById<EditText>(R.id)
    categoryButton = findViewById<Button>(R.id)

     */
    //---------------------------------------------------------------------------------------------------------------------------------------//
    // Category button click Listener
    //---------------------------------------------------------------------------------------------------------------------------------------//
    /*
    categoryButton.setOnClickListener {

        val description = input_description.text.toString()

        // Check category validation
        if (validateInput(description)) {
            Toast.makeText(this, "Your expense was created successfully, Toast.LENGTH_SHORT).show()
            val category = Category()
            // Start MainActivity after login is successful
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)

        } else {

            Toast.makeText(this, "An error occurred", Toast.LENGTH_SHORT).show()

        }
    }
     */

    //---------------------------------------------------------------------------------------------------------------------------------------//
    // Validate Goal Input
    //---------------------------------------------------------------------------------------------------------------------------------------//
    /*
    private fun validateInput(description: String: Boolean {
        if(description.isEmpty()){
            nameEditText.error = "Date cannot be empty"
            return false
        }
        return true
    }

     */

}