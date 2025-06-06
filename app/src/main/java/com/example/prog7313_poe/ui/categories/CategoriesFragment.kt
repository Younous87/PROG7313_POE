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
    private lateinit var  categoryNameTextView: TextView
    private lateinit var categoryTotalTextView: TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_categories, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Get views
        categoryNameTextView = view.findViewById(R.id.transactionReportsCategoryView)
        categoryTotalTextView = view.findViewById(R.id.transactionReportsTotalView)

        // fetches the user id from the shared preferences
        val sharedPreferences = requireContext().getSharedPreferences("user_prefs", MODE_PRIVATE)
        val userID = sharedPreferences.getString("user_id", "")?: ""

        // Parse date strings to date objects
        val startDate = if(args.startDate.isNotBlank()) parseDate(args.startDate) else Date()
        val endDate = if(args.endDate.isNotBlank()) parseDate(args.endDate) else Date()

        // Load the category total from viewmodel
        viewModel.loadTotals(userID, startDate, endDate)

        // fetches dates from the nav args in fragment_categories.xml
        viewModel.categorySpendingList.observe(viewLifecycleOwner){list->
            if(list.isNotEmpty()){
                val first = list.first()
                categoryNameTextView.text = first.categoryName
                categoryTotalTextView.text = "R %.2f".format(first.totalAmount)
            }else{
                categoryNameTextView.text = "No category data"
                categoryTotalTextView.text = "0.0"
            }
        }
        // Navigate to add category screen
        view.findViewById<Button>(R.id.categoriesAddButton).setOnClickListener {
            val action = CategoriesFragmentDirections.actionCategoriesToNewCategory()
            findNavController().navigate(action)
        }
    }
    private fun parseDate(dateString: String): Date{
        return try{
            val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            formatter.parse(dateString) ?: Date()
        } catch (e: Exception){
            Date()
        }
    }
}