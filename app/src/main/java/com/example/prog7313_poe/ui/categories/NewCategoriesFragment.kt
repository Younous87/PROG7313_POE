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
import android.widget.ImageButton
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.prog7313_poe.R
import com.example.prog7313_poe.classes.Category

class NewCategoriesFragment : Fragment() {
    private lateinit var categorySaveButton : Button
    private lateinit var categoryNameInput : EditText
    private lateinit var categoryBudgetInput : EditText
    private lateinit var backButton: ImageButton

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
        backButton = view.findViewById(R.id.imageButton5)
        setupClickListeners()

        val sharedPreferences = requireContext().getSharedPreferences("user_prefs", MODE_PRIVATE)
        val userID = sharedPreferences.getString("user_id","") ?: ""

        //---------------------------------------------------------------------------------------------------------------------------------------//
        // Category button click Listener
        //---------------------------------------------------------------------------------------------------------------------------------------//
        categorySaveButton.setOnClickListener {
            val name = categoryNameInput.text.toString().trim()

            // Validate if fields are empty
            if(validateInput(name)){
                // Validate if category name exists
                viewModel.validateCategoryInput(name,userID)

                // Observing validation result
                viewModel.categoryNotFound.observe(viewLifecycleOwner){notFound ->
                    if(notFound){
                        // Create new category
                        val category = Category(
                            categoryName = name,
                            userID = userID
                        )
                        // Insert category into Firestore
                        viewModel.insertCategory(category).observe(viewLifecycleOwner){success ->
                            if(success){
                                Toast.makeText(context, "Category was created", Toast.LENGTH_SHORT).show()
                            }else{
                                Toast.makeText(context, "Failed to create category", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }else{
                        categoryNameInput.error ="Category already exists"

                    }
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