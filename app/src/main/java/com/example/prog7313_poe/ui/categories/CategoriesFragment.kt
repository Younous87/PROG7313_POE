package com.example.prog7313_poe.ui.categories

import android.content.Context.MODE_PRIVATE
import android.content.Intent
import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.prog7313_poe.MainActivity
import com.example.prog7313_poe.R
import com.example.prog7313_poe.classes.Category
import com.example.prog7313_poe.ui.goals.GoalsViewModel
import androidx.navigation.fragment.navArgs
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


class CategoriesFragment : Fragment() {
    companion object {
        fun newInstance() = CategoriesFragment()
    }

    private val viewModel: CategoriesViewModel by viewModels()
    private val args: CategoriesFragmentArgs by navArgs()
    lateinit var newCategoryButton : Button

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_categories, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        // fetches the user id from the shared preferences
        val sharedPreferences = requireContext().getSharedPreferences("user_prefs", MODE_PRIVATE)
        val userID = sharedPreferences.getString("user_id", "")?: ""

        // Navigate to add category screen
        view.findViewById<Button>(R.id.categoriesAddButton).setOnClickListener {
            val action = CategoriesFragmentDirections.actionCategoriesToNewCategory()
            findNavController().navigate(action)
        }
    }

}