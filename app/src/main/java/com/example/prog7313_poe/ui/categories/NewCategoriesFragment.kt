package com.example.prog7313_poe.ui.categories

import android.content.Context.MODE_PRIVATE
import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.prog7313_poe.R
import com.example.prog7313_poe.classes.Category

class NewCategoriesFragment : Fragment() {
    private lateinit var categorySaveButton : Button
    private lateinit var categoryNameInput : EditText
    private lateinit var categoryBudgetInput : EditText

    companion object {
        fun newInstance() = NewCategoriesFragment()
    }

    private val viewModel: NewCategoriesViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // TODO: Use the ViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_new_categories, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //---------------------------------------------------------------------------------------------------------------------------------------//
        // Initialize Views
        //---------------------------------------------------------------------------------------------------------------------------------------//
        categorySaveButton = view.findViewById(R.id.newCategorySaveButton)
        categoryNameInput  = view.findViewById(R.id.categoriesNameInput)

        val sharedPreferences = requireContext().getSharedPreferences("user_prefs", MODE_PRIVATE)
        val userID = sharedPreferences.getInt("user_id",-1)

        //---------------------------------------------------------------------------------------------------------------------------------------//
        // Category button click Listener
        //---------------------------------------------------------------------------------------------------------------------------------------//
        categorySaveButton.setOnClickListener {
            val name = categoryNameInput.text.toString().trim()

            if(validateInput(name)){
                val category = Category(
                    categoryName = name,
                    description = "",
                    userID = userID
                )
                viewModel.insertCategory(category)
                Toast.makeText(context, "Category was created", Toast.LENGTH_SHORT).show()
            }
        }

    }

    //---------------------------------------------------------------------------------------------------------------------------------------//
    // Validate Category Inputs
    //---------------------------------------------------------------------------------------------------------------------------------------//

    private fun validateInput(category: String): Boolean {
        if (category.isEmpty()) {
            categoryNameInput.error = "Category name cannot be empty"
            return false
        }
        return true
    }

}