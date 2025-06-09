package com.example.prog7313_poe.ui.home
import android.health.connect.datatypes.units.Length
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity.MODE_PRIVATE
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.prog7313_poe.R
import com.example.prog7313_poe.classes.Goal
import com.example.prog7313_poe.classes.RankingManager
import com.example.prog7313_poe.databinding.FragmentHomeBinding
import com.example.prog7313_poe.ui.home.HomeViewModel
import com.example.prog7313_poe.ui.reports.ReportsViewModel
import com.example.prog7313_poe.ui.transactions.TransactionsViewModel
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class HomeFragment : Fragment() {

    lateinit var viewAllTransactionsButton : Button
    lateinit var viewAllCategoriesButton : Button
    lateinit var viewAllGoalsButton : Button
    lateinit var viewRankingButton : ImageButton

    // Goal Progress bar views
    private lateinit var minGoalProgressBar : ProgressBar
    private lateinit var  maxGoalProgressBar: ProgressBar

    // Category navigation views
    private lateinit var categoryPrevBtn: ImageButton
    private lateinit var categoryNextBtn: ImageButton
    private lateinit var categorySelectLabel: TextView
    private lateinit var highBudgetView: TextView

    // Transactions views
    private lateinit var transactionsPrevBtn: ImageButton
    private lateinit var transactionsNextBtn: ImageButton
    private lateinit var transactionsMonthLabel: TextView

    // Day streak views
    private lateinit var dayStreakTextView: TextView
    private lateinit var daysLabelTextView: TextView

    // Rank display button (left button)
    private lateinit var rankDisplayButton: ImageButton

    // Ranking manager
    private lateinit var rankingManager: RankingManager

    // Income and expense views
    private lateinit var incomeView: TextView
    private lateinit var expenseView: TextView

    private val transactionsCalendar = Calendar.getInstance()

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private val transactionViewModel: TransactionsViewModel by viewModels()
    private val homeViewModel: HomeViewModel by viewModels()

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

        // Category navigation views
        categoryPrevBtn = view.findViewById(R.id.previousCategoryMonth)
        categoryNextBtn = view.findViewById(R.id.nextCategoryMonth)
        categorySelectLabel = view.findViewById(R.id.categoryMonthDash)
        highBudgetView = view.findViewById(R.id.totalExpenseCategories)

        // Transactions views
        transactionsPrevBtn = view.findViewById(R.id.TransactionsPreviousMonthBttn)
        transactionsNextBtn = view.findViewById(R.id.TransactionsNextMonthBttn)
        transactionsMonthLabel = view.findViewById(R.id.TransactionsMonthDash)


        // Initialize day streak views
        dayStreakTextView = view.findViewById(R.id.textView9)
        daysLabelTextView = view.findViewById(R.id.textView10)

        // Initialize rank display button (left button)
        rankDisplayButton = view.findViewById(R.id.imageButton)


        // Initialize shared preferences to get user ID
        val sharedPreferences = requireContext().getSharedPreferences("user_prefs", MODE_PRIVATE)
        val userID = sharedPreferences.getString("user_id","")

        // Initialize ranking manager
        rankingManager = RankingManager()

        // Initialize Progress bar views
        minGoalProgressBar = view.findViewById(R.id.MinGoalProgressbar)
        maxGoalProgressBar = view.findViewById(R.id.MaxGoalProgressbar)

        // Income and expense views
        incomeView = view.findViewById(R.id.IncomeViewTransactions)
        expenseView = view.findViewById(R.id.ExpenseViewTransactions)



        //---------------------------------------------------------------------------------------------------------------------------------------//
        // Category, Transaction and Goals button click Listener
        //---------------------------------------------------------------------------------------------------------------------------------------//
        viewAllCategoriesButton.setOnClickListener {
            findNavController().navigate(R.id.categoriesReportsFragment)
        }

        viewAllTransactionsButton.setOnClickListener {
            findNavController().navigate(R.id.transactionsReportsFragment)
        }

        viewAllGoalsButton.setOnClickListener{
            findNavController().navigate(R.id.navigation_goals)
        }

        viewRankingButton.setOnClickListener {
            findNavController().navigate(R.id.rankingFragment2)
        }

        // Rank display button click listener (shows user profile or rank details)
        rankDisplayButton.setOnClickListener {
            // Navigate to user profile or show rank details
            // You can replace this with your desired navigation
            findNavController().navigate(R.id.rankingFragment2)
            // Or show a dialog with rank information
            // showRankInfoDialog(userID)
        }

        //---------------------------------------------------------------------------------------------------------------------------------------//
        // Initialize day streak and rank functionality
        //---------------------------------------------------------------------------------------------------------------------------------------//
        if (!userID.isNullOrEmpty()) {
            loadUserStreak(userID)
            loadUserRank(userID)
            updateGoalProgress(userID)

        }

        // Category navigation
        categoryPrevBtn.setOnClickListener {
            homeViewModel.navigateToPreviousCategory()
        }

        categoryNextBtn.setOnClickListener {
            homeViewModel.navigateToNextCategory()
        }

        //---------------------------------------------------------------------------------------------------------------------------------------//
        // Display latest transactions in home fragment
        //---------------------------------------------------------------------------------------------------------------------------------------//
        transactionViewModel.fetchLatestAmounts(userID = userID.toString())

        transactionViewModel.latestIncome.observe(viewLifecycleOwner) { income ->
            incomeView.text = "${income?.amount?:0.0}"
        }

        transactionViewModel.latestExpense.observe(viewLifecycleOwner) { expense ->
            expenseView.text = "${expense?.amount?:0.0}"
        }


        // Transactions navigation
        transactionsPrevBtn.setOnClickListener {
            transactionsCalendar.add(Calendar.MONTH, -1)
            updateTransactionMonthLabel()
            if (userID != null) {
                loadMonthlyTransactions(userID)
            }
        }

        // Transactions navigation
        transactionsNextBtn.setOnClickListener {
            transactionsCalendar.add(Calendar.MONTH, 1)
            updateTransactionMonthLabel()
            if (userID != null) {
                loadMonthlyTransactions(userID)
            }
        }

        // Initialize observers
        setupObservers()
        // Load data
        if (userID != null) {
            homeViewModel.loadCategorySpending(userID)
            transactionViewModel.fetchLatestAmounts(userID)
            updateTransactionMonthLabel()
            loadMonthlyTransactions(userID)
        }


    }

    private fun setupObservers() {

        // Category spending observers
        homeViewModel.currentCategorySpending.observe(viewLifecycleOwner) { categorySpending ->
            if (categorySpending != null) {
                categorySelectLabel.text = categorySpending.categoryName
                highBudgetView.text = String.format("%.2f", categorySpending.totalAmount)
            } else {
                categorySelectLabel.text = "No Categories"
                highBudgetView.text = "0.00"
            }
        }

        homeViewModel.categorySpending.observe(viewLifecycleOwner) { categories ->
            // Update category navigation button states
            val hasCategories = categories.isNotEmpty()
            categoryPrevBtn.isEnabled = hasCategories
            categoryNextBtn.isEnabled = hasCategories

            if (!hasCategories) {
                categorySelectLabel.text = "No Categories"
                highBudgetView.text = "0.00"
            }
        }

        homeViewModel.monthlyIncome.observe(viewLifecycleOwner) { income ->
            incomeView.text = String.format("%.2f", income)
        }

        homeViewModel.monthlyExpense.observe(viewLifecycleOwner) { expense ->
            expenseView.text = String.format("%.2f", expense)
        }
    }

    private fun updateTransactionMonthLabel() {
        val fmt = SimpleDateFormat("MMMM yyyy", Locale.getDefault())
        transactionsMonthLabel.text = fmt.format(transactionsCalendar.time)
    }

    private fun loadMonthlyTransactions(userId: String) {
        val year = transactionsCalendar.get(Calendar.YEAR)
        val month = transactionsCalendar.get(Calendar.MONTH)
        homeViewModel.loadMonthlyTotals(userId, year, month)
    }

    //---------------------------------------------------------------------------------------------------------------------------------------//
    // Day Streak and Rank Functions
    //---------------------------------------------------------------------------------------------------------------------------------------//

    private fun loadUserStreak(userID: String) {
        rankingManager.getUserRanking(userID) { userRanking ->
            activity?.runOnUiThread {
                if (userRanking != null) {
                    updateStreakDisplay(userRanking.currentStreak)
                } else {
                    // New user, show 0 streak
                    updateStreakDisplay(0)
                }
            }
        }
    }

    private fun loadUserRank(userID: String) {
        rankingManager.getUserRanking(userID) { userRanking ->
            activity?.runOnUiThread {
                if (userRanking != null) {
                    updateRankDisplay(userRanking.overallRank)
                } else {
                    // New user, show Bronze rank
                    updateRankDisplay("Bronze")
                }
            }
        }
    }

    private fun updateRankDisplay(rank: String) {
        val rankDrawable = rankingManager.getRankDrawable(rank)
        rankDisplayButton.setImageResource(rankDrawable)

        rankDisplayButton.contentDescription = "$rank Rank"

    }

    private fun updateStreakDisplay(streakCount: Int) {
        dayStreakTextView.text = streakCount.toString()
        daysLabelTextView.text = if (streakCount == 1) " Day" else " Days"

    }




    override fun onResume() {
        super.onResume()
        // Refresh streak and rank when user returns to fragment
        val sharedPreferences = requireContext().getSharedPreferences("user_prefs", MODE_PRIVATE)
        val userID = sharedPreferences.getString("user_id","")

        if (!userID.isNullOrEmpty()) {
            loadUserStreak(userID)
            loadUserRank(userID)
        }
    }
    //---------------------------------------------------------------------------------------------------------------------------------------//
    // Goal Progress Bar
    //---------------------------------------------------------------------------------------------------------------------------------------//
    private fun updateGoalProgress(userID: String){
        val db = com.google.firebase.firestore.FirebaseFirestore.getInstance()

        // Get current month in format "MM yyyy"
        val currentMonth = java.text.SimpleDateFormat("MMMM yyyy",java.util.Locale.getDefault()).format(Date())
        // Get user's goal for current month
        db.collection("goals")
            .whereEqualTo("userID",userID)
            .whereEqualTo("month",currentMonth)
            .get()
            .addOnSuccessListener { goalDocs ->
                if(!goalDocs.isEmpty){
                    val goal = goalDocs.documents[0].toObject(Goal::class.java)

                    // Get all expenses fir this user in current month
                    db.collection("expenses")
                        .whereEqualTo("userID",userID)
                        .whereEqualTo("transactionType","Expense")
                        .get()
                        .addOnSuccessListener { expenseDocs ->
                            val totalExpenseForMonth = expenseDocs.documents
                                .filter {
                                    val dateStr = it.getString("date") ?: return@filter false
                                    // Convert firestore date string to Date object
                                    val formatter = java.text.SimpleDateFormat("dd/MM/yyyy",java.util.Locale.getDefault())

                                    try {
                                        val date = formatter.parse(dateStr)
                                        val monthStr = java.text.SimpleDateFormat("MMMM yyyy",java.util.Locale.getDefault())
                                        val expenseMonth = monthStr.format(date!!)
                                        expenseMonth == currentMonth
                                    } catch (e: Exception){
                                        false
                                    }
                                }
                                .sumOf { it.getDouble("amount")?: 0.0 }

                            val min = goal?.minimum?: 0.0
                            val max = goal?.maximum?:1.0

                            val minProgress = ((totalExpenseForMonth/min) * 100).coerceAtMost(100.0).toInt()
                            val maxProcess = ((totalExpenseForMonth/max) * 100).coerceAtMost(100.0).toInt()
                            minGoalProgressBar.progress = minProgress
                            maxGoalProgressBar.progress = maxProcess
                        }
                }else{
                    minGoalProgressBar.progress = 0
                    maxGoalProgressBar.progress = 0
                }
            }

    }


}