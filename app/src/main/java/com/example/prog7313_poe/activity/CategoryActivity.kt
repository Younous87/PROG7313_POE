package com.example.prog7313_poe.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.prog7313_poe.MainActivity
import com.example.prog7313_poe.R
import com.example.prog7313_poe.ui.categories.CategoriesViewModel

class CategoryActivity : AppCompatActivity() {
    lateinit var categoryID : EditText
    lateinit var input_category : EditText
    lateinit var  input_description : EditText
    lateinit var newcategorySaveButton : Button
    lateinit var input_budegt_amount : EditText
    lateinit var categoriesViewModel: CategoriesViewModel


  override fun onCreate (savedInstanceState: Bundle?){
      super.onCreate(savedInstanceState)
      setContentView(R.layout.fragment_new_categories)

    //---------------------------------------------------------------------------------------------------------------------------------------//
    // Initialize Views
    //---------------------------------------------------------------------------------------------------------------------------------------//

    input_category =  findViewById<EditText>(R.id.categoriesNameInput)
    input_budegt_amount = findViewById<EditText>(R.id.categoriesDescriptionInput)
    newcategorySaveButton = findViewById<Button>(R.id.newCategorySaveButton)


    //---------------------------------------------------------------------------------------------------------------------------------------//
    // Category button click Listener
    //---------------------------------------------------------------------------------------------------------------------------------------//


    newcategorySaveButton.setOnClickListener {

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

      //---------------------------------------------------------------------------------------------------------------------------------------//
      // Category button click Listener
      //---------------------------------------------------------------------------------------------------------------------------------------//

  }
}