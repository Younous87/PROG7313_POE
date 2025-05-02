package com.example.prog7313_poe.ui.reports

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity.MODE_PRIVATE
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.prog7313_poe.R
import com.example.prog7313_poe.ui.goals.GoalsViewModel
import android.widget.Button
import android.widget.EditText
import androidx.navigation.fragment.findNavController
import com.example.prog7313_poe.ui.reports.CategoriesReportsFragmentDirections


class CategoriesReportsFragment : Fragment() {

    private lateinit var mCategoriesReportsViewModel: CategoriesReportsViewModel

    //goalViewModel = ViewModelProvider(this)[GoalsViewModel::class.java]
    //val sharedPreferences = requireContext().getSharedPreferences("user_prefs", MODE_PRIVATE)
    //val userID = sharedPreferences.getString("user_id",null)

    companion object {
        fun newInstance() = CategoriesReportsFragment()
    }

    private val viewModel: CategoriesReportsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //goalViewModel = ViewModelProvider(this)[GoalsViewModel::class.java]

        // TODO: Use the ViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val view = inflater.inflate(R.layout.fragment_categories_reports, container, false)

        // Set up the RecyclerView with its adapter
        val adapter = CategoriesReportAdapter()
        val recyclerView = view.findViewById<RecyclerView>(R.id.categoryRecycler)

        // Attach the adapter to the RecyclerView
        recyclerView.adapter = adapter

        // Set a layout manager to determine how items are arranged (in this case, vertical list)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        // Initialize the ViewModel to manage UI-related data
        mCategoriesReportsViewModel = ViewModelProvider(this).get(CategoriesReportsViewModel::class.java)

        // Observe the LiveData from the ViewModel; update the adapter's data when changes occur
        mCategoriesReportsViewModel.getAllData.observe(viewLifecycleOwner, Observer { category ->
            adapter.setData(category) // Update adapter with new data
        })

        val startInput = view.findViewById<EditText>(R.id.dateStartInput)
        val endInput = view.findViewById<EditText>(R.id.dateEndInput)
        val searchButton = view.findViewById<Button>(R.id.newGoalSaveButton)

        searchButton.setOnClickListener {
            val start = startInput.text.toString()
            val end = endInput.text.toString()

            val action = CategoriesReportsFragmentDirections.actionCategoriesReportsFragmentToNavigationCategories(start, end)
            findNavController().navigate(action)
        }



        return view
    }


}