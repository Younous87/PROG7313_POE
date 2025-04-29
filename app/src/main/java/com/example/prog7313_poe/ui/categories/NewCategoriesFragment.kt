package com.example.prog7313_poe.ui.categories

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
        categoryBudgetInput = view.findViewById(R.id.categoriesBudgetInput)

        //---------------------------------------------------------------------------------------------------------------------------------------//
        // Category button click Listener
        //---------------------------------------------------------------------------------------------------------------------------------------//
        categorySaveButton.setOnClickListener {
            val name = categoryNameInput.text.toString()
            val budget = categoryBudgetInput.text.toString()

            if(validateInput(name,budget)){
                val category = Category()
                if(category.createCategory(name, budget)){
                    Toast.makeText(context,"Category was created", Toast.LENGTH_SHORT).show()
                }else{
                    Toast.makeText(context,"Error!!! Category was not created", Toast.LENGTH_SHORT).show()
                }
            }
        }

    }

    //---------------------------------------------------------------------------------------------------------------------------------------//
    // Validate Category Inputs
    //---------------------------------------------------------------------------------------------------------------------------------------//

    private fun validateInput(category: String, budget: String): Boolean {
        if (category.isEmpty()) {
            categoryNameInput.error = "Category name cannot be empty"
            return false
        }
        if (budget.isEmpty()) {
            categoryBudgetInput.error = "Budget cannot be empty"
            return false
        }
        return true
    }

}