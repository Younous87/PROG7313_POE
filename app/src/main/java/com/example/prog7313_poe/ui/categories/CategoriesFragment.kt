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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.prog7313_poe.classes.CategorySpending
import com.example.prog7313_poe.ui.reports.CategoriesReportAdapter
import com.example.prog7313_poe.ui.reports.TransactionReportAdapter
import com.example.prog7313_poe.ui.reports.TransactionsReportsViewModel


class CategoriesFragment : Fragment() {
//    lateinit var newCategoryButton : Button
//
//    private lateinit var mCategoriesViewModel: CategoriesViewModel
//    private lateinit var startDate : EditText
//    private lateinit var endDate : EditText
//    private  lateinit var searchButton : Button
//
//    companion object {
//        fun newInstance() = CategoriesFragment()
//    }

//    private val viewModel: CategoriesViewModel by viewModels()
//    private val args: CategoriesFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_categories, container, false)

//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        // fetches the user id from the shared preferences
//        val userID = requireContext()
//            .getSharedPreferences("user_prefs", MODE_PRIVATE)
//            .getInt("user_id", -1)
//
//        // Set up the RecyclerView with its adapter
//        val adapter = CategoriesReportAdapter()
//        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerTransactionReport)
//
//        // Attach the adapter to the RecyclerView
//        recyclerView.adapter = adapter
//
//        // Set a layout manager to determine how items are arranged (in this case, vertical list)
//        recyclerView.layoutManager = LinearLayoutManager(requireContext())
//
//        // Initialize the ViewModel to manage UI-related data
//        mTransactionsReportsViewModel = ViewModelProvider(this).get(TransactionsReportsViewModel::class.java)
//
//        // fetches dates from the nav args in fragment_categories.xml
//
//
//        // triggers the DAO query
//        if(startDate.isNotEmpty() && endDate.isNotEmpty()) {
//            viewModel.loadTotals(userID, startDate, endDate)
//        }
//
//        // looks for layout to hold results
//        val container = view as ViewGroup
//
//        //observes the data from the DAO query live
//        viewModel.categoryTotals.observe(viewLifecycleOwner) { totals: List<CategorySpending> ->
//            container.removeAllViews()
//            totals.forEach { c ->
//                container.addView(TextView(requireContext()).apply {
//                    text     = "${c.categoryName}: R ${"%.2f".format(c.totalAmount)}"
//                    textSize = 20f
//                })
//            }
//        }



//        view.findViewById<Button>(R.id.categoriesAddButton).setOnClickListener {
//            val action = CategoriesFragmentDirections.actionCategoriesToNewCategory()
//            findNavController().navigate(action)
//        }
//    }



}