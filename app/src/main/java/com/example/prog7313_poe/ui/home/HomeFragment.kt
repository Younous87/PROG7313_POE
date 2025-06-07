package com.example.prog7313_poe.ui.home
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity.MODE_PRIVATE
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.prog7313_poe.R
import com.example.prog7313_poe.databinding.FragmentHomeBinding
import com.example.prog7313_poe.ui.home.HomeViewModel
import com.example.prog7313_poe.ui.transactions.TransactionsViewModel

class HomeFragment : Fragment() {
    lateinit var viewAllTransactionsButton : Button
    lateinit var viewAllCategoriesButton : Button
    lateinit var viewAllGoalsButton : Button
    lateinit var viewRankingButton : ImageButton

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private val transactionViewModel: TransactionsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

//        val textView: TextView = binding.textHome
//        homeViewModel.text.observe(viewLifecycleOwner) {
//            textView.text = it
//        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //---------------------------------------------------------------------------------------------------------------------------------------//
        // Initialize Views
        //---------------------------------------------------------------------------------------------------------------------------------------//
        viewAllCategoriesButton = view.findViewById(R.id.AllCategoryBttn)
        viewAllTransactionsButton = view.findViewById(R.id.AllTransactionsBttn)
        viewAllGoalsButton = view.findViewById(R.id.AllGoalsBttn)
        viewRankingButton = view.findViewById(R.id.rankingButton)
        val incomeView = view.findViewById<TextView>(R.id.IcomeView)
        val expenseView =  view.findViewById<TextView>(R.id.ExpenseView)
        // Initialize shared preferences to get user ID
        val sharedPreferences = requireContext().getSharedPreferences("user_prefs", MODE_PRIVATE)
        val userID = sharedPreferences.getString("user_id","")

        //---------------------------------------------------------------------------------------------------------------------------------------//
        // Category, Transaction and Goals button click Listener
        //---------------------------------------------------------------------------------------------------------------------------------------//
        viewAllCategoriesButton.setOnClickListener {
           findNavController().navigate(R.id.action_navigation_reports_to_categoriesReportsFragment2)

        }
        viewAllTransactionsButton.setOnClickListener {
           findNavController().navigate(R.id.action_navigation_reports_to_transactionsReportsFragment2)

        }
        viewAllGoalsButton.setOnClickListener{
            findNavController().navigate(R.id.navigation_goals)
        }

        viewRankingButton.setOnClickListener {
            findNavController().navigate(R.id.rankingFragment2)

        }

        //---------------------------------------------------------------------------------------------------------------------------------------//
        // Display latest transactions in home fragment
        //-----------------
        transactionViewModel.fetchLatestAmounts(userID = userID.toString())
        
        transactionViewModel.latestIncome.observe(viewLifecycleOwner) { income ->
            incomeView.text = "${income?.amount?:0.0}"
        }
        transactionViewModel.latestExpense.observe(viewLifecycleOwner) { expense ->
            expenseView.text = "${expense?.amount?:0.0}"
        }

    }
}