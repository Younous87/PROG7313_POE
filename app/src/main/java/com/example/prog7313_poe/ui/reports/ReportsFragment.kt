package com.example.prog7313_poe.ui.reports

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController
import com.example.prog7313_poe.R

class ReportsFragment : Fragment() {

    lateinit var viewAllTransactionsButton : Button
    lateinit var viewAllCategoriesButton : Button

    companion object {
        fun newInstance() = ReportsFragment()
    }

    private val viewModel: ReportsViewModel by viewModels()

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

        //---------------------------------------------------------------------------------------------------------------------------------------//
        // Category button click Listener
        //---------------------------------------------------------------------------------------------------------------------------------------//
        viewAllCategoriesButton.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_reports_to_categoriesReportsFragment2)

        }
        viewAllTransactionsButton.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_reports_to_transactionsReportsFragment2)

        }
    }
}