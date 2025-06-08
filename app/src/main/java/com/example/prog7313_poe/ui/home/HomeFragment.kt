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
import com.example.prog7313_poe.classes.RankingManager
import com.example.prog7313_poe.databinding.FragmentHomeBinding
import com.example.prog7313_poe.ui.home.HomeViewModel
import com.example.prog7313_poe.ui.transactions.TransactionsViewModel

class HomeFragment : Fragment() {
    lateinit var viewAllTransactionsButton : Button
    lateinit var viewAllCategoriesButton : Button
    lateinit var viewAllGoalsButton : Button
    lateinit var viewRankingButton : ImageButton

    // Day streak views
    private lateinit var dayStreakTextView: TextView
    private lateinit var daysLabelTextView: TextView

    // Rank display button (left button)
    private lateinit var rankDisplayButton: ImageButton

    // Ranking manager
    private lateinit var rankingManager: RankingManager

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

        // Initialize day streak views
        dayStreakTextView = view.findViewById(R.id.textView9)
        daysLabelTextView = view.findViewById(R.id.textView10)

        // Initialize rank display button (left button)
        rankDisplayButton = view.findViewById(R.id.imageButton)

        val incomeView = view.findViewById<TextView>(R.id.IcomeView)
        val expenseView =  view.findViewById<TextView>(R.id.ExpenseView)

        // Initialize shared preferences to get user ID
        val sharedPreferences = requireContext().getSharedPreferences("user_prefs", MODE_PRIVATE)
        val userID = sharedPreferences.getString("user_id","")

        // Initialize ranking manager
        rankingManager = RankingManager()

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

        // Optional: Add content description for accessibility
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


}