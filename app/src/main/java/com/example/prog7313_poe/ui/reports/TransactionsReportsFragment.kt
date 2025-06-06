package com.example.prog7313_poe.ui.reports

import android.annotation.SuppressLint
import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity.MODE_PRIVATE
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.prog7313_poe.R
import com.example.prog7313_poe.classes.Goal

class TransactionsReportsFragment : Fragment() {

    //private lateinit var mTransactionsReportsViewModel: TransactionsReportsViewModel
    private lateinit var startDate : EditText
    private lateinit var endDate : EditText
    private  lateinit var searchButton : Button

    companion object {
        fun newInstance() = TransactionsReportsFragment()
    }

   // private val viewModel: TransactionsReportsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // TODO: Use the ViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val view = inflater.inflate(R.layout.fragment_transactions_reports, container, false)

        return view
    }

    @SuppressLint("SuspiciousIndentation")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

//        // Set up the RecyclerView with its adapter
//        val adapter = TransactionReportAdapter()
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
//        //---------------------------------------------------------------------------------------------------------------------------------------//
//        // Initialize Views
//        //---------------------------------------------------------------------------------------------------------------------------------------//
//        startDate = view.findViewById(R.id.startDateTransaction)
//        endDate = view.findViewById(R.id.endDateTransaction)
//        searchButton = view.findViewById(R.id.searchTransactionButton)
//
//        // Initialize shared preferences to get user ID
//        val sharedPreferences = requireContext().getSharedPreferences("user_prefs", MODE_PRIVATE)
//        val userID = sharedPreferences.getString("user_id","")?: ""
//
//        // Observe the transaction report data
//        mTransactionsReportsViewModel.transactionReportData.observe(viewLifecycleOwner) { transactions ->
//            // Update the adapter with the new data
//            adapter.setData(transactions)
//        }
//
//        //---------------------------------------------------------------------------------------------------------------------------------------//
//        // Search Transaction button click Listener
//        //---------------------------------------------------------------------------------------------------------------------------------------//
//        searchButton.setOnClickListener{
//            val start = startDate.text.toString()
//            val end = endDate.text.toString()
//            val stringUserID = userID.toString()
//
//
//                // Call the ViewModel method to fetch data
//                mTransactionsReportsViewModel.getExpensesPerPeriodWithPhoto(stringUserID, start, end)
//
//                mTransactionsReportsViewModel.transactionReportData.observe(viewLifecycleOwner) { transactions ->
//                    // Update the adapter with the new data
//                    adapter.setData(transactions)}
//
//        }


    }

    //---------------------------------------------------------------------------------------------------------------------------------------//
    // Validate Goal Input
    //---------------------------------------------------------------------------------------------------------------------------------------//
//    private fun validateInput( start: String, end: String): Boolean {
//
//        if (start.isEmpty()) {
//            startDate.error = "Start date cannot be empty"
//            return false
//        }
//        if(end.isEmpty()){
//            endDate.error = "End date cannot be empty"
//        }
//
//        return true
//    }
}