package com.example.prog7313_poe.ui.reports

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity.MODE_PRIVATE
import androidx.navigation.fragment.findNavController
import com.example.prog7313_poe.R
import com.example.prog7313_poe.ui.categories.CategoriesViewModel
import com.example.prog7313_poe.ui.transactions.TransactionsViewModel

class ReportsFragment : Fragment() {

    lateinit var viewAllTransactionsButton : Button
    lateinit var viewAllCategoriesButton : Button

    companion object {
        fun newInstance() = ReportsFragment()
    }

    private val transactionViewModel: TransactionsViewModel by viewModels()
    private val categoriesViewModel : CategoriesViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // TODO: Use the ViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_reports, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //---------------------------------------------------------------------------------------------------------------------------------------//
        // Initialize Views
        //---------------------------------------------------------------------------------------------------------------------------------------//
        viewAllCategoriesButton = view.findViewById(R.id.AllCategoryBttn)
        viewAllTransactionsButton = view.findViewById(R.id.AllTransactionsBttn)
        val incomeView = view.findViewById<TextView>(R.id.IcomeView)
        val expenseView =  view.findViewById<TextView>(R.id.ExpenseView)
        // Initialize shared preferences to get user ID
        val sharedPreferences = requireContext().getSharedPreferences("user_prefs", MODE_PRIVATE)
        val userID = sharedPreferences.getString("user_id","")

        //---------------------------------------------------------------------------------------------------------------------------------------//
        // Category button click Listener
        //---------------------------------------------------------------------------------------------------------------------------------------//
        viewAllCategoriesButton.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_reports_to_categoriesReportsFragment2)

        }
        viewAllTransactionsButton.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_reports_to_transactionsReportsFragment2)

        }
        //---------------------------------------------------------------------------------------------------------------------------------------//
        // Display latest transactions in home fragment
        //-----------------
        transactionViewModel.latestIncome.observe(viewLifecycleOwner) { income ->
            incomeView.text = "${income?.amount?:0.0}"

        }
        transactionViewModel.latestExpense.observe(viewLifecycleOwner) { expense ->
             expenseView.text = "${expense?.amount?:0.0}"

        }
        transactionViewModel.fetchLatestAmounts(userID = userID.toString())

    }
}