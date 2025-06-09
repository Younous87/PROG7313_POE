package com.example.prog7313_poe.classes

import android.os.Handler
import android.os.Looper
import android.util.Log
import com.example.prog7313_poe.R
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.concurrent.TimeUnit

class RankingManager {

    companion object {
        // XP Values for different actions
        const val LOGIN_XP = 10
        const val TRANSACTION_XP = 25
        const val GOAL_XP = 50
        const val STREAK_BONUS_XP = 15

        // Collection name
        const val USER_RANKINGS_COLLECTION = "user_rankings"

        // Rank Thresholds
        val RANK_THRESHOLDS = listOf(
            RankThreshold("Bronze", 0, 499),
            RankThreshold("Silver", 500, 1499),
            RankThreshold("Gold", 1500, 2999),
            RankThreshold("Diamond", 3000, Int.MAX_VALUE)
        )
    }

    private val firestore = FirebaseFirestore.getInstance()
    private val rankingCollection = firestore.collection(USER_RANKINGS_COLLECTION)

    // Award points for daily login
    fun awardLoginPoints(userId: String, callback: (Boolean) -> Unit) {
        val today = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())

        rankingCollection.document(userId).get()
            .addOnSuccessListener { document ->

                val userRanking = if (document.exists()) {
                    document.toObject(UserRanking::class.java) ?: UserRanking(userId = userId)
                } else {
                    UserRanking(userId = userId)
                }

                // Check if user already logged in today
                if (userRanking.lastLoginDate != today) {
                    val newStreak = if (isConsecutiveDay(userRanking.lastLoginDate, today)) {
                        userRanking.currentStreak + 1
                    } else {
                        1 // Reset streak if not consecutive
                    }

                    val streakXP = LOGIN_XP + if (newStreak > 1) STREAK_BONUS_XP else 0

                    val updatedRanking = userRanking.copy(
                        dayStreakXP = userRanking.dayStreakXP + streakXP,
                        currentStreak = newStreak,
                        lastLoginDate = today
                    )

                    updateUserRanking(updatedRanking) { updateSuccess ->
                        callback(updateSuccess)
                    }
                } else {
                    callback(false) // Already logged in today
                }
            }
            .addOnFailureListener { exception ->
                callback(false)
            }
    }

    // Award points for adding transaction
    fun awardTransactionPoints(userId: String, callback: (Boolean) -> Unit) {
        rankingCollection.document(userId).get()
            .addOnSuccessListener { document ->
                val userRanking = if (document.exists()) {
                    document.toObject(UserRanking::class.java) ?: UserRanking(userId = userId)
                } else {
                    UserRanking(userId = userId)
                }

                val updatedRanking = userRanking.copy(
                    transactionXP = userRanking.transactionXP + TRANSACTION_XP,
                    totalTransactions = userRanking.totalTransactions + 1
                )

                updateUserRanking(updatedRanking, callback)
            }
            .addOnFailureListener {
                callback(false)
            }
    }

    // Award points for adding goal
    fun awardGoalPoints(userId: String, callback: (Boolean) -> Unit) {
        rankingCollection.document(userId).get()
            .addOnSuccessListener { document ->
                val userRanking = if (document.exists()) {
                    document.toObject(UserRanking::class.java) ?: UserRanking(userId = userId)
                } else {
                    UserRanking(userId = userId)
                }

                val updatedRanking = userRanking.copy(
                    goalXP = userRanking.goalXP + GOAL_XP,
                    totalGoals = userRanking.totalGoals + 1
                )

                updateUserRanking(updatedRanking, callback)
            }
            .addOnFailureListener {
                callback(false)
            }
    }

    // Update user ranking with recalculated values
    private fun updateUserRanking(userRanking: UserRanking, callback: (Boolean) -> Unit) {
        Log.d("RankingManager", "updateUserRanking called with: $userRanking")

        val updatedRanking = userRanking.copy(
            transactionRank = calculateRank(userRanking.transactionXP),
            goalRank = calculateRank(userRanking.goalXP),
            dayStreakRank = calculateRank(userRanking.dayStreakXP),
            overallXP = calculateOverallXP(userRanking),
            overallRank = calculateOverallRank(userRanking)
        )

        Log.d("RankingManager", "Final updated ranking to save: $updatedRanking")

        rankingCollection.document(userRanking.userId).set(updatedRanking, SetOptions.merge())
            .addOnSuccessListener {
                Log.d("RankingManager", "Firestore set successful")
                callback(true)
            }
            .addOnFailureListener { exception ->
                Log.e("RankingManager", "Firestore set failed: ${exception.message}", exception)
                callback(false)
            }
    }

    // Calculate rank based on XP
    private fun calculateRank(xp: Int): String {
        return RANK_THRESHOLDS.find { xp >= it.minXP && xp <= it.maxXP }?.rank ?: "Bronze"
    }

    // Calculate overall XP (weighted average)
    private fun calculateOverallXP(userRanking: UserRanking): Int {
        return ((userRanking.transactionXP * 0.4) +
                (userRanking.goalXP * 0.3) +
                (userRanking.dayStreakXP * 0.3)).toInt()
    }

    // Calculate overall rank
    private fun calculateOverallRank(userRanking: UserRanking): String {
        val overallXP = calculateOverallXP(userRanking)
        return calculateRank(overallXP)
    }

    // Get user ranking data
    fun getUserRanking(userId: String, callback: (UserRanking?) -> Unit) {
        rankingCollection.document(userId).get()
            .addOnSuccessListener { document ->
                val userRanking = if (document.exists()) {
                    document.toObject(UserRanking::class.java)
                } else {
                    null
                }
                callback(userRanking)
            }
            .addOnFailureListener {
                callback(null)
            }
    }

    // Get all user rankings for leaderboard
    fun getAllRankings(callback: (List<UserRanking>) -> Unit) {
        rankingCollection.orderBy("overallXP", com.google.firebase.firestore.Query.Direction.DESCENDING)
            .get()
            .addOnSuccessListener { documents ->
                val rankings = documents.mapNotNull { doc ->
                    doc.toObject(UserRanking::class.java)
                }
                callback(rankings)
            }
            .addOnFailureListener {
                callback(emptyList())
            }
    }

    // Get XP needed for next rank
    fun getXPForNextRank(currentXP: Int): Pair<Int, String> {
        val currentRankIndex = RANK_THRESHOLDS.indexOfFirst {
            currentXP >= it.minXP && currentXP <= it.maxXP
        }

        return if (currentRankIndex < RANK_THRESHOLDS.size - 1) {
            val nextRank = RANK_THRESHOLDS[currentRankIndex + 1]
            Pair(nextRank.minXP, nextRank.rank)
        } else {
            Pair(currentXP, RANK_THRESHOLDS.last().rank) // Already at max rank
        }
    }

    // Helper function to check if dates are consecutive
    private fun isConsecutiveDay(lastDate: String, currentDate: String): Boolean {
        if (lastDate.isEmpty()) return false

        try {
            val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val last = sdf.parse(lastDate)
            val current = sdf.parse(currentDate)

            val diffInMillis = current!!.time - last!!.time
            val diffInDays = TimeUnit.MILLISECONDS.toDays(diffInMillis)

            return diffInDays == 1L
        } catch (e: Exception) {
            Log.e("RankingManager", "Error parsing dates: $lastDate, $currentDate", e)
            return false
        }
    }

    // Get rank drawable resource
    fun getRankDrawable(rank: String): Int {
        return when (rank.lowercase()) {
            "bronze" -> R.drawable.bronze_rank
            "silver" -> R.drawable.silver_rank
            "gold" -> R.drawable.gold_rank
            "diamond" -> R.drawable.diamond_rank
            else -> R.drawable.bronze_rank
        }
    }
}