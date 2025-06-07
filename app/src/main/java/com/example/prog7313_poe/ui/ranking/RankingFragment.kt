package com.example.prog7313_poe.ui.ranking

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity.MODE_PRIVATE
import com.example.prog7313_poe.R
import com.example.prog7313_poe.classes.RankingManager
import com.example.prog7313_poe.classes.UserRanking
import com.example.prog7313_poe.databinding.FragmentGoalsBinding
import com.example.prog7313_poe.databinding.FragmentRankingBinding

class RankingFragment : Fragment() {

    companion object {
        fun newInstance() = RankingFragment()
    }

    private val viewModel: RankingViewModel by viewModels()
    private val rankingManager = RankingManager()
    private lateinit var userId: String

    // Views (since you're not using view binding)
    private lateinit var backButton: ImageButton
    private lateinit var rankingOverall: TextView
    private lateinit var rankingOverallLogo: ImageView
    private lateinit var rankingOverallprogressBar: ProgressBar
    private lateinit var rankingOverallXPCounter: TextView
    private lateinit var rankingOverallXPLimit: TextView

    private lateinit var rankingTransaction: TextView
    private lateinit var rankingOverallLogo2: ImageView
    private lateinit var rankingTransactionprogressBar: ProgressBar
    private lateinit var rankingTransactionXPCounter: TextView
    private lateinit var rankingTransactionXPLimit: TextView

    private lateinit var rankingGoal: TextView
    private lateinit var rankingGoalLogo: ImageView
    private lateinit var rankingGoalProgressBar: ProgressBar
    private lateinit var rankingGoalXPCounter: TextView
    private lateinit var rankingGoalXPLimit: TextView

    private lateinit var rankingDayStreak: TextView
    private lateinit var rankingDayStreakLogo: ImageView
    private lateinit var rankingDayStreakProgressBar: ProgressBar
    private lateinit var rankingDayStreakXPCounter: TextView
    private lateinit var rankingDayStreakXPLimit: TextView

    private var _binding: FragmentRankingBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val sharedPreferences = requireContext().getSharedPreferences("user_prefs", MODE_PRIVATE)
        userId = sharedPreferences.getString("user_id", "") ?: ""

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRankingBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews(view)
        setupClickListeners()
        loadUserRanking()
    }

    private fun initViews(view: View) {
        // Overall ranking views
        backButton = view.findViewById(R.id.backButton)
        rankingOverall = view.findViewById(R.id.rankingOverall)
        rankingOverallLogo = view.findViewById(R.id.rankingOverallLogo)
        rankingOverallprogressBar = view.findViewById(R.id.rankingOverallprogressBar)
        rankingOverallXPCounter = view.findViewById(R.id.rankingOverallXPCounter)
        rankingOverallXPLimit = view.findViewById(R.id.rankingOverallXPLimit)

        // Transaction ranking views
        rankingTransaction = view.findViewById(R.id.rankingTransaction)
        rankingOverallLogo2 = view.findViewById(R.id.rankingOverallLogo2)
        rankingTransactionprogressBar = view.findViewById(R.id.rankingTransactionprogressBar)
        rankingTransactionXPCounter = view.findViewById(R.id.rankingTransactionXPCounter)
        rankingTransactionXPLimit = view.findViewById(R.id.rankingTransactionXPLimit)

        // Goal ranking views
        rankingGoal = view.findViewById(R.id.rankingGoal)
        rankingGoalLogo = view.findViewById(R.id.rankingGoalLogo)
        rankingGoalProgressBar = view.findViewById(R.id.rankingGoalProgressBar)
        rankingGoalXPCounter = view.findViewById(R.id.rankingGoalXPCounter)
        rankingGoalXPLimit = view.findViewById(R.id.rankingGoalXPLimit)

        // Day streak ranking views
        rankingDayStreak = view.findViewById(R.id.rankingDayStreak)
        rankingDayStreakLogo = view.findViewById(R.id.rankingDayStreakLogo)
        rankingDayStreakProgressBar = view.findViewById(R.id.rankingDayStreakProgressBar)
        rankingDayStreakXPCounter = view.findViewById(R.id.rankingDayStreakXPCounter)
        rankingDayStreakXPLimit = view.findViewById(R.id.rankingDayStreakXPLimit)
    }

    private fun setupClickListeners() {
        backButton.setOnClickListener {
            // Handle back navigation - adjust based on your navigation setup
            requireActivity().onBackPressed()
        }
    }


    private fun loadUserRanking() {
        rankingManager.getUserRanking(userId) { userRanking ->
            activity?.runOnUiThread {
                if (userRanking != null) {
                    updateUI(userRanking)
                } else {
                    // Create new user ranking
                    val newRanking = UserRanking(userId = userId)
                    updateUI(newRanking)
                }
            }
        }
    }

    private fun updateUI(userRanking: UserRanking) {
        // Overall
        rankingOverall.text = userRanking.overallRank
        rankingOverallLogo.setImageResource(rankingManager.getRankDrawable(userRanking.overallRank))
        updateProgressBar(rankingOverallprogressBar, rankingOverallXPCounter,
            rankingOverallXPLimit, userRanking.overallXP)

        // Transaction
        rankingTransaction.text = userRanking.transactionRank
        rankingOverallLogo2.setImageResource(rankingManager.getRankDrawable(userRanking.transactionRank))
        updateProgressBar(rankingTransactionprogressBar, rankingTransactionXPCounter,
            rankingTransactionXPLimit, userRanking.transactionXP)

        // Goal
        rankingGoal.text = userRanking.goalRank
        rankingGoalLogo.setImageResource(rankingManager.getRankDrawable(userRanking.goalRank))
        updateProgressBar(rankingGoalProgressBar, rankingGoalXPCounter,
            rankingGoalXPLimit, userRanking.goalXP)

        // Day Streak
        rankingDayStreak.text = userRanking.dayStreakRank
        rankingDayStreakLogo.setImageResource(rankingManager.getRankDrawable(userRanking.dayStreakRank))
        updateProgressBar(rankingDayStreakProgressBar, rankingDayStreakXPCounter,
            rankingDayStreakXPLimit, userRanking.dayStreakXP)
    }

    private fun updateProgressBar(
        progressBar: ProgressBar,
        currentXPText: TextView,
        limitXPText: TextView,
        currentXP: Int
    ) {
        val (nextRankXP, _) = rankingManager.getXPForNextRank(currentXP)
        val currentRankThreshold = RankingManager.RANK_THRESHOLDS.find {
            currentXP >= it.minXP && currentXP <= it.maxXP
        }

        if (currentRankThreshold != null) {
            val progress = if (currentRankThreshold.maxXP == Int.MAX_VALUE) {
                100 // Max rank reached
            } else {
                ((currentXP - currentRankThreshold.minXP).toFloat() /
                        (currentRankThreshold.maxXP - currentRankThreshold.minXP).toFloat() * 100).toInt()
            }

            progressBar.progress = progress
            currentXPText.text = currentXP.toString()
            limitXPText.text = if (currentRankThreshold.maxXP == Int.MAX_VALUE) {
                "MAX"
            } else {
                nextRankXP.toString()
            }
        }
    }
}